package controller;

import model.Persona;
import model.Utenti;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.BufferUnderflowException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ObjectServer implements Runnable{

    //Sockets
    private ServerSocket serverSocket;
    private ServerSocket dataServerSocket;

    //I/O
    private BufferedReader inC;
    private PrintWriter outC;
    private ObjectOutputStream out;

    private Utenti utenti;

    public ObjectServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            dataServerSocket = new ServerSocket(port + 1);
            utenti = new Utenti();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            log("server attivo, attesa di connessione");
            Socket socket = serverSocket.accept();
            Socket dataSocket = dataServerSocket.accept();
            log("un client si è connesso");
            //inizializzazione
            inC = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outC = new PrintWriter(socket.getOutputStream());
            out = new ObjectOutputStream(dataSocket.getOutputStream());
            boolean terminate = false;
            do {
                send("Inserire:\n" +
                        "list per ottenere la lista degli utenti\n" +
                        "insert per inserire un nuovo utente\n" +
                        "get numeroUtente per ottenere l'utente i esimo\n" +
                        "search cognome nome per ricercare i dati dell'utente desiderato\n" +
                        "exit per terminare il programma"
                );
                Scanner scanner = new Scanner(read());
                if(scanner.hasNext()) {
                    switch (scanner.next()) {
                        case "list": {
                            log("Invio lista al client");
                            out.writeObject(utenti.list());
                            out.flush();
                            break;
                        }
                        case "insert": {
                            send("Inviare il cognome dell'utente da aggiungere");
                            String cognome = read();
                            send("Inviare il nome");
                            String nome = read();
                            send("Inviare la data di nascita(dd-mm-yyyy)");
                            Scanner s = new Scanner(read());
                            s.useDelimiter("[-/]");
                            int giorno = Integer.parseInt(s.next());
                            int mese = Integer.parseInt(s.next());
                            int anno = Integer.parseInt(s.next());
                            LocalDate dataNascita = LocalDate.of(anno, mese, giorno);
                            utenti.insert(new Persona(nome, cognome, dataNascita));
                            break;
                        }
                        case "get": {
                            if (scanner.hasNext()) {
                                out.writeObject(utenti.get(Integer.parseInt(scanner.next())));
                                out.flush();
                            } else {
                                send("Non hai inviato il comando correttamente");
                            }
                            break;
                        }
                        case "search": {
                            if (scanner.hasNext()) {
                                String cognome = scanner.next();
                                if (scanner.hasNext())  {
                                    String nome = scanner.next();
                                    Persona persona;
                                    if((persona = utenti.search(cognome, nome)) != null) {
                                        out.writeObject(persona);
                                        out.flush();
                                    } else {
                                        send("Utente non trovato");
                                    }
                                } else {
                                    send("Non hai inserito il nome");
                                }
                            } else {
                                send("Non hai inserito il cognome");
                            }
                            break;
                        }
                        case "exit": {
                            terminate = true;
                            send("Hai richiesto la disconnessione, chiusura imminente");
                            log("Il client ha richiesto la disconnessione");
                            break;
                        }
                        default: {
                            send("Comando non riconosciuto, riprovare");
                            break;
                        }
                    }
                } else {
                    outC.write("Non hai inviato nessun comando, riprova");
                }
            } while(!terminate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(Object message) {
        System.out.println(message);
    }

    private void send(String message) {
        outC.write(message);
        outC.flush();
    }

    private String read() throws IOException { return inC.readLine(); };

}
