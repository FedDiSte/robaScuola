package controller;

import java.io.PrintWriter;
import java.net.Socket;

public class Runner implements Runnable{

    private String serverAddr;
    private final int serverPort = 80;

    private Socket socket;

    private PrintWriter outNet;

    public Runner(String serverAddr) {
        try {
            this.serverAddr = serverAddr;
            socket = new Socket(serverAddr, serverPort);
            outNet = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        outNet.println("GET /̵̨̨̧͎̜̩͔̖̥̝̜͕̰̮̄͋̔̆͝ͅa̴̝̥͈̲̘̞̗͎̗̥̥̤̝̰̋͒̕͝ṡ̵͇͎̬͙d̸̡̛̬̤̗̬͕̫͔͉͈͇͚̋̍͒̈̍̇́͠͝ą̶̭̪̫̦̀̇͘ś̷͚");
        outNet.flush();
        System.out.println("Completato!");
    }

}
