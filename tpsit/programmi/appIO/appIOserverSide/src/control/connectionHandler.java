package control;

import java.io.IOException;
import java.net.ServerSocket;

public class connectionHandler implements Runnable{

    ServerSocket serverSocket;

    AdminListener adminListener = new AdminListener();
    Thread adminListenerThread;

    public connectionHandler(int port) {
        try {
            serverSocket = new ServerSocket(port);
            adminListenerThread = new Thread(adminListener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        adminListenerThread.start();
        do {
            try {
                new Thread(new MainApplication(serverSocket.accept())).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!adminListener.isTerminate());
    }

}
