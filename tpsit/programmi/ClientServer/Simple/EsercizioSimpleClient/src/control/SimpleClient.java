package control;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.Socket;

public class SimpleClient implements Runnable{
    private Socket client = null;
    private int porta = 1234;

    private BufferedReader inTastiera = new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter outClient = null;

    public SimpleClient() {
        try {
            client = new Socket("127.0.0.1", porta);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public SimpleClient(int porta) {
        this.porta = porta;
        try {
            client = new Socket("127.0.0.1", porta);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Client: ora posso inviare e ricevere messaggi!");
            outClient = new PrintWriter(client.getOutputStream());
            BufferedReader inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = "";
            do {
                line = inClient.readLine();
                System.out.println("Server: " + line);
                line = inTastiera.readLine();
                outClient.println(line);
                outClient.flush();
            } while (!line.equalsIgnoreCase("exit"));
            outClient.close();
            client.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
