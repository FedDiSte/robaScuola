package controller;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ClientUDP implements Runnable{

    private static final int PACKET_SIZE = 1024;

    private DatagramSocket socket;
    private DatagramPacket datagramPacket;
    private byte[] data;

    private int serverPort;
    private InetAddress serverIP;

    public ClientUDP(String ip, int port) {
        try {
            this.serverIP = InetAddress.getByAddress(ip.getBytes(StandardCharsets.UTF_8));
            this.serverPort = port;
            data = new byte[PACKET_SIZE];
            socket = new DatagramSocket(port);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message = "bella lello";
            data = message.getBytes(StandardCharsets.UTF_8);
            socket.send(datagramPacket = new DatagramPacket(data, data.length, serverIP, serverPort));
            socket.receive(datagramPacket);
            log(new String(datagramPacket.getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) {
        System.out.println(message);
    }

}
