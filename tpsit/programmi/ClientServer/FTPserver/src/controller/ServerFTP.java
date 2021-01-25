package controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ServerFTP implements Runnable{

    private ServerSocket charServerSocket;
    private ServerSocket bitServerSocket;

    private Socket charNet;
    private Socket bitNet;

    private BufferedReader inNet;
    private PrintWriter outNet;
    private BufferedOutputStream outBitNet;

    private boolean terminate;

    public ServerFTP(int port) {
        try {
            log("creazione server avviata");
            charServerSocket = new ServerSocket(port);
            bitServerSocket = new ServerSocket(port + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            log("in attesa di client");
            charNet = charServerSocket.accept();
            bitNet = bitServerSocket.accept();
            log("connessione ultimata");
            inNet = new BufferedReader(new InputStreamReader(charNet.getInputStream()));
            outNet = new PrintWriter(charNet.getOutputStream(), true);
            outBitNet = new BufferedOutputStream(bitNet.getOutputStream());
            Scanner scanner;
            do {
                send("Inserisci\n" +
                        "dir --> per ottenere l'elenco dei file scaricabili\n" +
                        "send nomefile --> per richiedere lo specifico file\n" +
                        "exit --> per uscire dall'applicazione\n");
                String readed = read();
                scanner = new Scanner(readed);
                if (scanner.hasNext()) {
                    switch (scanner.next().trim().toLowerCase()) {
                        case "dir": {
                            send(dir());
                            break;
                        }
                        case "send": {
                            if(scanner.hasNext()) {
                                String nomeFile = scanner.next();
                                log("Richiesto il file: " + nomeFile);
                                if(checkFile(nomeFile)) {
                                    log("File trovato!");
                                    send("OK " + Files.size(Paths.get(nomeFile)));
                                    log("inizio invio file");
                                    sendFile(nomeFile);
                                    log("File correttamente inviato");
                                } else {
                                    send("File non trovato");
                                    log("ERROR " + 404);
                                }
                            } else {
                                send("Comando errato");
                            }
                            break;
                        }
                        case "exit": {
                            log("il client ha richiesto lo spegnimento");
                            terminate = true;
                            terminate();
                            break;
                        }
                        default: {
                            send("Il comando inviato è errato");
                            break;
                        }
                    }
                }
            } while(!terminate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String dir() {
        StringBuilder stringBuilder = null;
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("."))) {
            for(Path p : stream) {
                if(Files.isRegularFile(p)) {
                    stringBuilder.append(p.getFileName() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private boolean checkFile(String nomeFile) {
        return Files.exists(Paths.get(nomeFile));
    }

    private void sendFile(String nomeFile) throws IOException {
        Path percorsoFile = Paths.get(nomeFile);
        log("inizio lettura byte da file");
        byte[] data = Files.readAllBytes(percorsoFile);
        log("lettura compleata, inizio invio " + data);
        outBitNet.write(data, 0, (int) Files.size(percorsoFile));
        outBitNet.flush();
        log("file inviato");
    }

    private void terminate() throws IOException{
        if(outBitNet != null) {
            outBitNet.close();
        }
        if(outNet != null) {
            outNet.close();
        }
        if(inNet != null) {
            inNet.close();
        }
        if(!bitNet.isClosed()) {
            bitNet.close();
        }
        if(!charNet.isClosed()) {
            charNet.close();
        }
        if(!bitServerSocket.isClosed()) {
            bitServerSocket.close();
        }
        if(!charServerSocket.isClosed()) {
            charServerSocket.close();
        }
    }

    private void log(Object message) {
        System.out.println(message);
    }

    private void send(String message) throws IOException{
        outNet.println(message);
        outNet.flush();
    }

    private String read() throws IOException {
        return inNet.readLine();
    }

}
