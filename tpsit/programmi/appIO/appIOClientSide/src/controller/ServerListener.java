package controller;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerListener implements Runnable {

    BufferedReader inNet;
    Controller controller;

    public ServerListener(BufferedReader inNet, Controller controller) {
        this.inNet = inNet;
        this.controller = controller;
    }

    @Override
    public void run() {
        do {
            try {
                log(inNet.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!controller.isTerminate());
    }

    private void log(Object message) {
        System.out.println(message);
    }

}
