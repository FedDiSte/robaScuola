import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.Socket;

public class ClientReciever implements Runnable{

    private Socket client = null;
    private int porta = 1044;
    private Gui gui = null;

    public ClientReciever(Gui gui) {
        try {
            client = new Socket("127.0.0.1", porta);
            this.gui=gui;
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public ClientReciever(Gui gui, int porta) {
        this.porta = porta;
        try {
            client = new Socket("127.0.0.1", porta);
            this.gui = gui;
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Ora posso ricevere messaggi!");
            gui.onTextArea("Client: ora posso ricevere messaggi!");
            BufferedReader inClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = "";
            do{
                line = inClient.readLine();
                System.out.println("Server: " + line);
                gui.onTextArea("Server: " + line);
            } while(!line.equalsIgnoreCase("exit"));
            inClient.close();
            client.close();
            
        } catch(IOException e) {
            System.out.println(e);
        }
    }

}