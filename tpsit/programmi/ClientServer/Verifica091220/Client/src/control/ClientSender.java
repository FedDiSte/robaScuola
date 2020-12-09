package control;

import control.ClientListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSender implements Runnable{

    private Socket socket;

    private BufferedReader inNet;
    private PrintWriter outNet;

    private BufferedReader inTastiera;

    public ClientSender(String ip, int port) {
        try{
            System.err.println("Client avviato, in attesa di connessione");
            socket = new Socket(ip, port);
            inNet = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outNet = new PrintWriter(socket.getOutputStream());
            inTastiera = new BufferedReader(new InputStreamReader(System.in));
            new Thread(new ClientListener(inNet)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String readed = "";
        do {
            try {
                outNet.println(inTastiera.readLine());
                outNet.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(!(readed.toLowerCase().trim().equals("quit")));
        System.err.println("Hai chiesto la chiusura della comunicazione, shutdown imminente");
        try {
            inTastiera.close();
            outNet.close();
            inNet.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
