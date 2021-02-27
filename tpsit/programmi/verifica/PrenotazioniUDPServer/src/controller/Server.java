package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server implements Runnable{

    public DatagramSocket acceptSocket;

    public DatagramPacket packet;

    public SharedMemory memory;

    public int port;

    public Server(int port) {
        try {
            acceptSocket = new DatagramSocket(port);
            memory = new SharedMemory();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
         do {
             try {
                 acceptSocket.receive(packet);
                 log("Tentata connessione da parte di un client");
                 if (new String(packet.getData()).equals("connect")) {
                     int newPort = memory.getUsablePort();
                     if(newPort != -1) {
                         new Thread(new Handle(newPort, packet.getAddress(), packet.getPort(), memory)).start();
                         log("Un client si è connesso, assegnata la porta: " + newPort);
                     } else {
                         byte[] data = "Non possiamo gestire questa comunicazione al momento, riprova più tardi".getBytes();
                         acceptSocket.send(new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort()));
                         log("Un client non è riuscito a connettersi (porte non disponibili)");
                     }
                 } else {
                     byte[] data = "È impossibile che questo messaggio esce".getBytes();
                     acceptSocket.send(new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort()));
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         } while (!memory.isStopAccept());
    }

    private void log(Object qualcosa) {
        System.out.println(qualcosa);
    }

}
