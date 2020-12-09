package control;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    private ServerSocket serverSocket;

    private BufferedReader inNet;
    private PrintWriter outNet;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            do {
                Socket s = serverSocket.accept();
                new Thread(new HandleLogin(s)).start();
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
