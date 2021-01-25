package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import model.CartaDiCredito;
import model.Spesa;
import model.Utente;

public class MainApplication implements Runnable {

    public Utente currentUser;

    public Socket net;

    public ArrayList<Utente> utenti = new ArrayList<>();

    public BufferedReader inNet;
    public PrintWriter outNet;

    public MainApplication(Socket net) {
        this.net = net;
        log("Il client si è connesso");
        loadData();
    }

    @Override
    public void run() {
        try {
            inNet = new BufferedReader(new InputStreamReader(net.getInputStream()));
            outNet = new PrintWriter(net.getOutputStream());
            loadData();
            boolean terminate = false;
            boolean isLogged = false;
            while (!terminate) {
                try {
                    sendNet("Che cosa vuoi fare?\n" +
                            "1 Effettua il login\n" +
                            "2 Registrati\n" +
                            "exit Esci dal programma");
                    String response = readNet();
                    log(response);
                    switch (response) {
                        case "1": {
                            isLogged = login();
                            break;
                        }
                        case "2": {
                            isLogged = register();
                            break;
                        }
                        case "exit": {
                            log("Il client ha richiesto la chiusura, shutdown imminente");
                            sendNet("Arrivederci!");
                            terminate = true;
                            outNet.close();
                            inNet.close();
                            net.close();
                            break;
                        }
                    }
                    while (isLogged) {
                        sendNet("Che cosa vuoi fare " + currentUser.getEmail() + "?\n" +
                                "1 Aggiungi una carta di credito\n" +
                                "2 Aggiungi una spesa\n" +
                                "3 Controlla le spese effettuate\n" +
                                "4 Controlla il cashback attuale\n" +
                                "5 Controlla le informazioni del tuo account\n" +
                                "6 Effettua il logout");
                        response = inNet.readLine();
                        switch (response) {
                            case "1": {
                                addCarta();
                                break;
                            }
                            case "2": {
                                addSpesa();
                                break;
                            }
                            case "3": {
                                checkSpese();
                                break;
                            }
                            case "4": {
                                checkCashback();
                                break;
                            }
                            case "5": {
                                checkAccount();
                                break;
                            }
                            case "6": {
                                updateData();
                                currentUser = null;
                                isLogged = false;
                                break;
                            }
                            default: {
                                sendNet("Non abbiamo riconosciuto quel comando, riprova");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean login() {
        loadData();
        if (!utenti.isEmpty()) {
            sendNet("Inserisci la tua email");
            String email = readNet();
            sendNet("Inserisci la tua password");
            String password = readNet();
            for (Utente utente : utenti) {
                if (email.trim().equalsIgnoreCase(utente.getEmail())) {
                    if (password.trim().equals(utente.getPassword())) {
                        sendNet("Accesso riuscito, benvenuto/a " + utente.getEmail());
                        currentUser = utente;
                        return true;
                    } else {
                        sendNet("Password errata, riprovare");
                    }
                } else {
                    sendNet("Utente non trovato, riprovare");
                }
            }
        } else {
            sendNet("Nessun utente presente, prova a registrarti");
        }
        return false;
    }

    private boolean register() {
        loadData();
        String email = null;
        String password;
        ArrayList<CartaDiCredito> carteDiCredito = new ArrayList<>();

        sendNet("Inserisci l'email per il tuo account");
        boolean isValid = false;
        boolean isPresent = false;
        while (!isValid) {
            email = readNet();
            if (email.contains("@")) {
                if (!utenti.isEmpty()) {
                    for (Utente utente : utenti) {
                        if (email.equalsIgnoreCase(utente.getEmail())) {
                            sendNet("L'email inserita è gia associata ad un account");
                            isPresent = true;
                        }
                    }
                }
                isValid = !isPresent;

            } else {
                sendNet("Non hai inserito un email, riprovare");
                isValid = false;
            }
        }
        sendNet("Inserisci la password per il tuo account");
        while ((password = readNet()).length() < 8) {
            sendNet("Inserisci una password di almeno 8 caratteri");
        }
        String readed;
        try {
            do {
                sendNet("Vuoi aggiungere una carta di credito? (si/no)");
                readed = readNet();
                if (!readed.equalsIgnoreCase("no")) {
                    String carta = null;
                    String cvv = null;
                    String data;
                    isValid = false;
                    sendNet("Inserisci il numero di carta");
                    while (!isValid) {
                        carta = readNet();
                        if (carta.trim().length() != 16 || !carta.matches("[0-9]+")) {
                            sendNet("Non hai inserito una carta valida, riprovare");
                            isValid = false;
                        } else {
                            if (!utenti.isEmpty()) {
                                for (Utente utente : utenti) {
                                    log("checking utente: " + utente);
                                    for (CartaDiCredito cartaDiCredito : utente.getCarteDiCredito()) {
                                        if (cartaDiCredito.getNumero().equalsIgnoreCase(carta)) {
                                            sendNet("Questa carta è gia associata ad un altro account, riprovare");
                                            isValid = false;
                                        } else {
                                            sendNet("Numero carta registrato");
                                            isValid = true;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                sendNet("Numero carta registrato");
                                isValid = true;
                                break;
                            }
                        }
                    }
                    isValid = false;
                    sendNet("Ora inserisci il codice cvv");
                    while (!isValid) {
                        cvv = readNet();
                        if (cvv.length() == 3 || !cvv.matches("[0-9]+")) {
                            isValid = true;
                        } else {
                            isValid = false;
                            sendNet("Non hai inserito un codice cvv valido, riprovare (Ricorda, è il numero di 3 cifre dietro la tua carta)");
                        }
                    }
                    sendNet("Inserire la data di scadenza");
                    data = readNet();
                    sendNet("Dati registrati correttamente, aggiunta della carta...");
                    carteDiCredito.add(new CartaDiCredito(carta, cvv, data));
                }
            } while (!(readed.equalsIgnoreCase("no")));
            currentUser = new Utente(email, password, carteDiCredito);
            utenti.add(currentUser);
            updateData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void addCarta() {
        try {
            String carta = null;
            String cvv = null;
            String data;
            boolean isValid = false;
            sendNet("Inserisci il numero di carta");
            while (!isValid) {
                carta = readNet();
                if (carta.trim().length() != 16 || !carta.matches("[0-9]+")) {
                    sendNet("Non hai inserito una carta valida, riprovare");
                    isValid = false;
                } else {
                    if (!utenti.isEmpty()) {
                        for (Utente utente : utenti) {
                            log("checking utente: " + utente);
                            if (!utente.getCarteDiCredito().isEmpty()) {
                                for (CartaDiCredito cartaDiCredito : utente.getCarteDiCredito()) {
                                    if (cartaDiCredito.getNumero().equalsIgnoreCase(carta)) {
                                        sendNet("Questa carta è gia associata ad un altro account, riprovare");
                                        isValid = false;
                                    } else {
                                        sendNet("Numero carta registrato");
                                        isValid = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        sendNet("Numero carta registrato");
                        isValid = true;
                        break;
                    }
                }
            }
            isValid = false;
            sendNet("Ora inserisci il codice cvv");
            while (!isValid) {
                cvv = readNet();
                if (cvv.length() == 3 || !cvv.matches("[0-9]+")) {
                    isValid = true;
                } else {
                    isValid = false;
                    sendNet("Non hai inserito un codice cvv valido, riprovare (Ricorda, è il numero di 3 cifre dietro la tua carta)");
                }
            }
            sendNet("Inserire la data di scadenza");
            data = readNet();
            sendNet("Dati registrati correttamente, aggiunta della carta...");
            currentUser.addCartaDiCredito(new CartaDiCredito(carta, cvv, data));
            log("Update dati");
            updateData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSpesa() {
        try {
            sendNet("Aggiungi una descrizione della spesa effettuata");
            String descrizione = readNet();
            sendNet("Perfetto, ora inserisci l'importo speso (non inserire il simbolo della valuta)");
            double importo = Double.parseDouble(readNet());
            sendNet("Spesa aggiunta correttamente, il tuo cashback sarà calcolato a breve");
            currentUser.addSpesaEffettuate(new Spesa(descrizione, importo));
            log("update dati");
            updateData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkSpese() {
        StringBuilder sb = new StringBuilder();
        if (!currentUser.getSpeseEffettuate().isEmpty() || currentUser.getSpeseEffettuate() != null) {
            for (Spesa spesa : currentUser.getSpeseEffettuate()) {
                sb.append("Descrizione: " +
                        spesa.getDescrizione() +
                        " Importo: " +
                        spesa.getPrezzo() +
                        "€\n");
            }
        }
        sendNet(sb.toString());
    }

    private void checkCashback() {
        sendNet("Il tuo cashback attuale ammonta a: " + currentUser.getValoreCashback() + "€");
    }

    private void checkAccount() {
        sendNet("Email: " + currentUser.getEmail() + " Password: " + currentUser.getPassword());
        sendNet("Carte di credito: ");
        for (CartaDiCredito cartaDiCredito : currentUser.getCarteDiCredito()) {
            sendNet("Codice: " + cartaDiCredito.getNumero() + " cvv: " + cartaDiCredito.getCvv() + " data di scadenza: " + cartaDiCredito.getDataDiScadenza());
        }
    }

    synchronized private void loadData() {
        try {
            if (!Files.exists(Paths.get("data/dataUtenti.txt"))) {
                Files.createFile(Paths.get("data/dataUtenti.txt"));
            }
            BufferedReader reader = new BufferedReader(new FileReader("data/dataUtenti.txt", StandardCharsets.UTF_8));
            String readed;
            while ((readed = reader.readLine()) != null) {
                Scanner scanner = new Scanner(readed);
                scanner.useDelimiter(";");
                String email = scanner.next();
                String password = scanner.next();
                ArrayList<CartaDiCredito> carteDiCredito = new ArrayList<>();
                String cartaDiCredito;
                while (!(cartaDiCredito = scanner.next()).equalsIgnoreCase("endCarte")) {
                    carteDiCredito.add(new CartaDiCredito(cartaDiCredito, scanner.next(), scanner.next()));
                }
                ArrayList<Spesa> speseEffettuate = new ArrayList<>();
                String scanned;
                while (!(scanned = scanner.next()).equalsIgnoreCase("endSpesa")) {
                    speseEffettuate.add(new Spesa(scanned, Double.parseDouble(scanner.next())));
                }
                Utente newUtente = new Utente(email, password, carteDiCredito, speseEffettuate);
                boolean isAdded = false;
                for (Utente utente : utenti) {
                    if (utente.getEmail().equals(newUtente.getEmail())) {
                        utenti.remove(utente);
                        utenti.add(newUtente);
                        isAdded = true;
                    }
                }
                if (!isAdded) {
                    utenti.add(newUtente);
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    synchronized private void updateData() throws IOException {
        loadData();
        for (Utente utente : utenti) {
            if (utente.getEmail().equals(currentUser.getEmail())) {
                utente = currentUser;
            }
        }
        FileWriter writer;
        Files.delete(Paths.get("data/dataUtenti.txt"));
        writer = new FileWriter("data/dataUtenti.txt", true);
        for (Utente utente : utenti) {
            writer.write(utente.getEmail() + ";");
            writer.flush();
            writer.write(utente.getPassword() + ";");
            writer.flush();
            for (CartaDiCredito cartaDiCredito : utente.getCarteDiCredito()) {
                writer.write(cartaDiCredito.getNumero() + ";");
                writer.flush();
                writer.write(cartaDiCredito.getCvv() + ";");
                writer.flush();
                writer.write(cartaDiCredito.getDataDiScadenza() + ";");
                writer.flush();
            }
            writer.write("endCarte;");
            writer.flush();
            for (Spesa spesa : utente.getSpeseEffettuate()) {
                writer.write(spesa.getDescrizione() + ";");
                writer.flush();
                writer.write(spesa.getPrezzo() + ";");
                writer.flush();
            }
            writer.write("endSpesa;");
            writer.flush();
        }
        log("file scritti");
        writer.close();
    }

    private void sendNet(Object message) {
        outNet.println(message);
        outNet.flush();
    }

    private String readNet() {
        String message = null;
        try {
            message = inNet.readLine();
            log("Il client ha inviato: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    private void log(Object message) {
        System.out.println(message);
    }

}