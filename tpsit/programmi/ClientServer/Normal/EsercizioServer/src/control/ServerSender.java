//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;



public class ServerSender implements Runnable{

    private ServerSocket server = null;
    private int porta = 1044;
    private Gui gui = null;

    //private BufferedReader inTastiera = new BufferedReader(new InputStreamReader(System.in));
    private PrintWriter outServer = null;

    public ServerSender(Gui gui) {
        try {
            this.gui = gui;
            server = new ServerSocket(porta);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public ServerSender(Gui gui, int porta) {
        this.porta = porta;
        try {
            this.gui = gui;
            server = new ServerSocket(porta);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try{
            gui.onTextArea("In attesa di connessione...");
            Socket socketAccettato = server.accept();
            gui.textField.setText("");
            gui.onTextArea("Server: Ora il client riceve messaggi!");
            System.out.println("Server: ora il client riceve messaggi!");
            outServer = new PrintWriter(socketAccettato.getOutputStream());

            gui.button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String line = gui.textField.getText() + " (inviato alle: " + new Timestamp(System.currentTimeMillis()).toString() +")";
                    System.out.println(line);
                    gui.textField.setText("");
                    gui.button.setEnabled(false);
                    outServer.println(line);
                    outServer.flush();
                    gui.onTextArea("Server: " + line);
                    if(line.equalsIgnoreCase("exit")){
                        try{
                            outServer.close();
                            socketAccettato.close();
                            server.close();
                        } catch(IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            /*do {
                line = inTastiera.readLine();
                outServer.println(line);
                outServer.flush();
            } while (!line.equalsIgnoreCase("exit"));*/
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
}
