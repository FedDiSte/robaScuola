package control;

import view.GuiChat;
import view.GuiChatSelector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.Socket;
import java.util.ArrayList;

public class Input implements Runnable {

    private ArrayList<GuiChat> guiChat;
    private GuiChatSelector guiChatSelector;
    private int port;
    private BufferedReader in;
    private Socket socket;

    public Input(ArrayList guiChat, GuiChatSelector guiChatSelector) {
        this.guiChat = guiChat;
        this.guiChatSelector = guiChatSelector;
        port = 3031;
        try {
            socket = new Socket("localhost", 3031);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line = "";
            do {
                line = in.readLine();
                System.out.println("Readed: " + line);
                switch (line) {
                    case "update" : {
                        StringBuilder sb = new StringBuilder();
                        line = in.readLine();
                        while(!line.equals("stop")) {
                            sb.append(line);
                            sb.append("\n");
                            line = in.readLine();
                        }
                        guiChatSelector.updateOnlineNames(sb.toString());
                        break;
                    }
                    case "messaggio" : {
                        String mittente = in.readLine();
                        System.out.println("readed: " + mittente);
                        for(GuiChat gui : guiChat) {
                            if(gui.getName().equals(mittente)) {
                                gui.onTextArea(in.readLine());
                            }
                        }
                        break;
                    }
                }
            } while(!line.equalsIgnoreCase("exit"));
            in.close();
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}