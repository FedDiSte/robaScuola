
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerReciever implements Runnable{

    private int porta = 3030;
    private ServerSocket server = null;
    private Gui gui = null;

    public ServerReciever(Gui gui) {
        try {
            this.gui = gui;
            server = new ServerSocket(porta);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public ServerReciever(Gui gui, int porta) {
        this.porta = porta;
        try {
            this.gui = gui;
            server = new ServerSocket(porta);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    @Override

    public void run(){
        try {

            Socket socketAccettato = server.accept();
            System.out.println("Server: ora il client invia messaggi!");
            gui.onTextArea("Server: ora il client invia messaggi!");
            BufferedReader inClient = new BufferedReader(new InputStreamReader(socketAccettato.getInputStream()));
            String line = "";
            do{
                line = inClient.readLine();
                System.out.println("Client: " + line);
                gui.onTextArea("Client: " + line);
            } while(!line.equalsIgnoreCase("exit"));
            inClient.close();
            socketAccettato.close();
            server.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

}