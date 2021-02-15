package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerUDP implements Runnable{

    private static final int PACKET_SIZE = 1024;
    private DatagramSocket socket;
    private byte[] data;

    private BufferedReader inTastiera;

    private Controller controller;

    public ServerUDP(int port) {
        try {
            socket = new DatagramSocket(port);
            data = new byte[PACKET_SIZE];
            inTastiera = new BufferedReader(new InputStreamReader(System.in));
            controller = new Controller();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            new Thread(new UDPListener(socket, controller)).start();
            log("Per terminare la comunicazione scrivi 'exit'");
            String message = null;
            while(!message.equals("exit")) {
                message = inTastiera.readLine();
                data = message.getBytes();
                if (controller.hasClientIP() && controller.hasClientPort()) {
                    socket.send(new DatagramPacket(data, data.length, controller.getClientIP(), controller.getClientPort()));
                } else {
                    log("Ancora non c'è nessuno qui, aspetta che il client ti invia un messaggio");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        System.out.println(message);
    }

}
