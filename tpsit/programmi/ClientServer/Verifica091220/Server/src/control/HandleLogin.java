package control;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class HandleLogin implements Runnable {

    private Socket s;
    private BufferedReader inClient;
    private PrintWriter outClient;

    private Utente utente;
    private PrintWriter dataOutFile;
    private InputStreamReader dataInFile;
    private float saldo;

    private ArrayList<Utente> utenti;
    private static final String fileUtenti = "fileUtenti.txt";
    private BufferedReader inFile;
    private PrintWriter outFile;

    public HandleLogin(Socket s) {
        try {
            loadUtenti();
            this.s = s;
            inClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outClient = new PrintWriter(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Boolean hasLogged = false;
            do {
                outClient.println("Che cosa vuoi fare?\n" +
                        "1 Nome Cognome Username Password per registrarti\n" +
                        "2 Username Password per loggare");
                Scanner scanner = new Scanner(inClient.readLine());
                scanner.useDelimiter(" ");
                switch(scanner.next()) {
                    case "1" : {
                        String nome;
                        String cognome;
                        String username;
                        String password;
                        if(scanner.hasNext()) {
                            nome = scanner.next();
                        } else {
                            outClient.println("non sono stati inseriti abbastanza dati, riprovare");
                            System.out.println("il client non ha inserito abbastanza dati");
                            break;
                        }
                        if(scanner.hasNext()) {
                            cognome = scanner.next();
                        } else {
                            outClient.println("non sono stati inseriti abbastanza dati, riprovare");
                            System.out.println("il client non ha inserito abbastanza dati");
                            break;
                        }
                        if(scanner.hasNext()) {
                            username = scanner.next();
                        } else {
                            outClient.println("non sono stati inseriti abbastanza dati, riprovare");
                            System.out.println("il client non ha inserito abbastanza dati");
                            break;
                        }
                        if(scanner.hasNext()) {
                            password = scanner.next();
                        } else {
                            outClient.println("non sono stati inseriti abbastanza dati, riprovare");
                            System.out.println("il client non ha inserito abbastanza dati");
                            break;
                        }
                        utente = createUser(nome, cognome, username, password);
                        createDataFile();
                        hasLogged = true;
                        break;
                    }
                    case "2" : {
                        //Il Client proverà a loggarsiù
                        String username;
                        String password;
                        if(scanner.hasNext()) {
                            username = scanner.next();
                        } else {
                            outClient.println("non sono stati inseriti abbastanza dati, riprovare");
                            System.out.println("il client non ha inserito abbastanza dati");
                            break;
                        }
                        if(scanner.hasNext()) {
                            password = scanner.next();
                        } else {
                            outClient.println("non sono stati inseriti abbastanza dati, riprovare");
                            System.out.println("il client non ha inserito abbastanza dati");
                            break;
                        }
                        utente = loginUser(username, password);
                        updateSaldo();
                        hasLogged = true;
                        break;
                    }
                }
            } while (!hasLogged);
            outClient.println("Ciao " + utente.getUsername() + " che cosa vuoi fare?\n" +
                    "1 Quantità per depositare\n" +
                    "2 Quantità per prelevare\n" +
                    "3 per la stampa per prelevare");
            switch (inClient.readLine()) {
                case "1" : {
                    //TODO
                }
                case "2" : {
                    //TODO
                }
                case "3" : {
                    //TODO
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUtenti() {
        try {
            if(Files.exists(Paths.get(fileUtenti))) {
                inFile = new BufferedReader(new InputStreamReader(new FileInputStream(fileUtenti)));
                Scanner s;
                String line;
                while((line = inFile.readLine()) != null) {
                    s = new Scanner(line);
                    s.useDelimiter(";");
                    utenti.add(new Utente(s.next(), s.next(), s.next(), s.next()));
                }
            } else {
                Files.createFile(Paths.get(fileUtenti));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Utente createUser(String nome, String cognome, String username, String password) {
        Utente nuovoUtente = new Utente(nome, cognome, username, password);
        utenti.add(nuovoUtente);
        if(Files.exists(Paths.get(fileUtenti))) {
            try {
                Files.delete(Paths.get(fileUtenti));
                Files.createFile(Paths.get(fileUtenti));
                outFile = new PrintWriter(new FileWriter(new File(fileUtenti), true));
                for (Utente utente : utenti) {
                    outFile.println(utente.getNome() + ";" + utente.getCognome() + ";" + utente.getUsername() + ";" + utente.getPassword());
                }
                outFile.close();
                outClient.println("Utente creato correttamente");
                outClient.flush();
                System.out.println("Nuovo utente creato: " + nuovoUtente);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nuovoUtente;
    }

    public Utente loginUser(String username, String password) {
        for(Utente utente : utenti) {
            if(utente.getUsername().trim().equals(username.trim()) && utente.getPassword().trim().equals(password.trim())) {
                return utente;
            }
        }
        outClient.println("Utente o password non trovato, riprovare");
        return null;
    }

    public void createDataFile() {
        try {
            Files.createFile(Paths.get(utente.getUsername()));
            dataOutFile = new PrintWriter(new FileWriter(new File(utente.getUsername())));
            dataOutFile.println("0");
            saldo = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSaldo() {
        try {
            if(Files.exists(Paths.get(utente.getUsername()))) {
                inFile = new BufferedReader(new InputStreamReader(new FileInputStream(fileUtenti)));
                String line;
                while((line = inFile.readLine()) != null) {
                    saldo = Float.parseFloat(line);
                }
            } else {
                Files.createFile(Paths.get(fileUtenti));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Deposito() {
        //TODO
    }

    public void Prelievo() {
        //TODO
    }

    public void StampaSaldo() {

    }
}
