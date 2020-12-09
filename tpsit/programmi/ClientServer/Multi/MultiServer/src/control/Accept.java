package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


//thread che accetta la connessione dei singoli thread, dividendo il flusso output e input
public class Accept implements Runnable {

    private ServerSocket server;

    private ArrayList<Send> outputSockets = new ArrayList<>();

    public Accept() {
        System.out.println("Server avviato, in attesa di client");
        try {
            server = new ServerSocket(3031);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BufferedReader reader;
        try {
            Boolean isRunning = false;
            Thread update = new Thread(new Update(this));
            do {
                //Controllo se ci sono almeno due client connessi, in caso avvio il thread per aggiornare i client online
                if(outputSockets.size() >= 2 && !(isRunning)) {
                    update.start();
                    isRunning = true;
                } else if(outputSockets.size() < 2 && isRunning) {
                    update.interrupt();
                    isRunning = false;
                }
                System.out.println("in attesa di client");
                Socket tmp = server.accept();
                Socket tmp2 = server.accept();
                reader = new BufferedReader(new InputStreamReader(tmp.getInputStream()));
                System.out.println("ora aspetto il nome del client");
                String name = reader.readLine();
                System.out.println(name + " si è connesso! " + tmp.getInetAddress());
                outputSockets.add(new Send(tmp2, name));
                Thread t = new Thread(new Read(tmp, outputSockets, name));
                t.start();

            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Send> getOutputSockets() {
        return outputSockets;
    }

}
