package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class SimpleServer implements Runnable{

    private ServerSocket server = null;
    private int porta = 1234;

    private BufferedReader inTastiera = new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter outServer = null;

    public SimpleServer() {
        try {
            server = new ServerSocket(porta);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public SimpleServer(int porta) {
        this.porta = porta;
        try {
            server = new ServerSocket(porta);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try{

            Socket socketAccettato = server.accept();
            System.out.println("Server: ora il client riceve e invia messaggi!");
            outServer = new PrintWriter(socketAccettato.getOutputStream());
            BufferedReader inClient = new BufferedReader(new InputStreamReader(socketAccettato.getInputStream()));
            String line = "";
            do {
                line = inTastiera.readLine();
                outServer.println(line);
                outServer.flush();
                line = inClient.readLine();
                System.out.println("Client: " + line);
            } while (!line.equalsIgnoreCase("exit"));
            outServer.close();
            socketAccettato.close();
            server.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
}