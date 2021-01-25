package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSide implements Runnable{

    private Socket socket;

    private BufferedReader inNet;
    private PrintWriter outNet;

    private BufferedReader inTastiera;

    private Controller controller;

    public ClientSide(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            inNet = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outNet = new PrintWriter(socket.getOutputStream(), true);
            inTastiera = new BufferedReader(new InputStreamReader(System.in));
            controller = new Controller();
            log("Sei connesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            new Thread(new ServerListener(inNet, controller)).start();
            String readed = "";
            while(!readed.equals("exit")) {
                readed = inTastiera.readLine();
                sendNet(readed);
            }
            controller.setTerminate();
            inTastiera.close();
            outNet.close();
            inNet.close();
            socket.close();
            log("Arrivederci!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNet(String message) {
        outNet.println(message);
        outNet.flush();
    }

    private void log(Object message) {
        System.out.println(message);
    }

}
