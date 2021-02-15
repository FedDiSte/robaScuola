package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPListener implements Runnable{

    private DatagramSocket socket;
    private Controller controller;
    private DatagramPacket packet;

    public UDPListener(DatagramSocket socket, Controller controller) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(!controller.isTerminate()) {
            try {
                socket.receive(packet);
                if(!controller.hasClientIP() && !controller.hasClientPort()) {
                    controller.setClientIP(packet.getAddress());
                    controller.setClientPort(packet.getPort());
                }
                String message;
                log(message = new String(packet.getData()));
                if (message.equals("exit")) {
                    controller.setTerminate(true);
                    log("Il client ha richiesto la chiusura, arrivederci!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void log(Object message) {
        System.out.println(message);
    }

}
