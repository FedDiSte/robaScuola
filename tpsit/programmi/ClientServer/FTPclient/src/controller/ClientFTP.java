package controller;

import java.io.*;
import java.net.Socket;

public class ClientFTP implements Runnable {

    private Socket net;
    private Socket bitNet;

    private BufferedReader inTastiera;

    private BufferedReader inNet;
    private PrintWriter outNet;

    private Controller controller;

    private BufferedInputStream inBitNet;

    public ClientFTP(String ip, int porta) {
        try {
            log("Client creato, in attesa di connesione");
            controller = new Controller();
            inTastiera = new BufferedReader(new InputStreamReader(System.in));

            net = new Socket(ip, porta);
            inNet = new BufferedReader(new InputStreamReader(net.getInputStream()));
            outNet = new PrintWriter(net.getOutputStream(), true);

            bitNet = new Socket(ip, porta + 1);
            inBitNet = new BufferedInputStream(bitNet.getInputStream());
            new Thread(new ListenerChar(inNet, controller)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            log("il server è connesso");
            do {
                String message = inTastiera.readLine();
                outNet.println(message);
                if (message.trim().toLowerCase().contains("send")) {
                    controller.setFilename(message.replace("send", "").trim().toLowerCase());
                    new Thread(new ListenerBit(inBitNet, controller)).start();
                }
                outNet.flush();
            } while (!controller.isTerminate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) {
        System.out.println(message);
    }

}
