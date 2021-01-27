package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ServerUDP implements Runnable{

    private static final int PACKET_SIZE = 1024;
    private DatagramSocket socket;
    private DatagramPacket datagramPacket;
    private byte[] data;

    private int clientPort;
    private InetAddress clientAddress;

    public ServerUDP(int port) {
        try {
            socket = new DatagramSocket(port);
            data = new byte[PACKET_SIZE];
            datagramPacket = new DatagramPacket(data, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String messaggio = "bella fratm";
            socket.receive(datagramPacket);
            log(new String(datagramPacket.getData()));
            data = messaggio.getBytes();

            clientAddress = datagramPacket.getAddress();
            clientPort = datagramPacket.getPort();

            socket.send(datagramPacket = new DatagramPacket(data, data.length, clientAddress, clientPort));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        System.out.println(message);
    }

}
