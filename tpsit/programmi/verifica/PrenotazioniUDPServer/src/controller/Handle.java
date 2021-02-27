package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Handle implements Runnable{

    private int myPort;
    private DatagramSocket socket;

    private boolean terminate = false;

    private InetAddress clientAddress;
    private int clientPort;

    SharedMemory memory;

    public Handle(int myPort, InetAddress clientAddress, int clientPort, SharedMemory memory) {
        this.myPort = myPort;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.memory = memory;
        try {
            socket = new DatagramSocket(myPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        do {
            try {
                send("Che cosa vuoi fare?\n" +
                        "1: Controlla i turni di un determinato giorno\n" +
                        "2: Prenota un giorno");
                switch (recieve()) {
                    case "1": {
                        send("Inviare 'Giorno Mese' per controllare i turni di un determinato giorno");
                        Scanner s = new Scanner(recieve());
                        w
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(!terminate);
    }

    public String recieve() throws IOException {
        byte[] data = new byte[1024];
        DatagramPacket packet;
        socket.receive(packet = new DatagramPacket(data, data.length));
        return new String(packet.getData());
    }

    public void send(String toSend) throws IOException{
        byte[] data = toSend.getBytes();
        socket.send(new DatagramPacket(data, data.length, clientAddress, clientPort));
    }

}
