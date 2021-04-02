import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.Socket;
import java.sql.Timestamp;

public class ClientSender implements Runnable {
    private Socket client = null;
    private int porta = 4000;
    private Gui gui = null;

    private BufferedReader inTastiera = new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter outClient = null;

    public ClientSender(Gui gui) {
        this.gui = gui;
        try {
            client = new Socket("127.0.0.1", porta);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ClientSender(Gui gui, int porta) {
        this.porta = porta;
        this.gui = gui;
        try {
            client = new Socket("127.0.0.1", porta);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Client: ora posso inviare messaggi!");
            gui.onTextArea("Client: Ora posso inviare messaggi!");
            outClient = new PrintWriter(client.getOutputStream());

            gui.button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String line = gui.textField.getText() + " (inviato alle: " + new Timestamp(System.currentTimeMillis()).toString() +")";
                    System.out.println(line);
                    gui.textField.setText("");
                    gui.button.setEnabled(false);
                    outClient.println(line + "inviato alle: " + System.currentTimeMillis());
                    outClient.flush();
                    gui.onTextArea("Client: " + line);
                    if(line.equalsIgnoreCase("exit")){
                        try{
                            outClient.close();
                            client.close();
                        } catch(IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        //do {        
            //line = inTastiera.readLine();
            //outClient.println(line);
            //outClient.flush();
        //} while (!line.equalsIgnoreCase("exit"));
        //outClient.close();
        //client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
