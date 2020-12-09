package control;

import view.GuiChat;
import view.GuiChatSelector;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Output{

    private PrintWriter out;
    private Socket socket = new Socket();

    private String myname;

    public Output() {
        try {
            socket = new Socket("localhost", 3031);
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, String destination) {
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(message);
        System.err.println("messaggio inviato: " + message);
        out.flush();
        out.println(destination);
        System.err.println("inviato a: " + destination);
        out.flush();
    }

    public void setMyName(String myname) {
        this.myname = myname;
        out.println(myname);
        out.flush();
    }

}
