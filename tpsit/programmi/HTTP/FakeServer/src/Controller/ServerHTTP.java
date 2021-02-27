package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHTTP implements Runnable{

    ServerSocket serverSocket;

    private BufferedReader inNet;

    public ServerHTTP(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            inNet = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            do {
                log(inNet.readLine());
            } while (socket.isBound());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(Object message) {
        System.out.println(message);
    }

}
