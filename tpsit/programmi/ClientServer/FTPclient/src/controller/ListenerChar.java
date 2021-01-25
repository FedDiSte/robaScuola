package controller;

import java.io.*;

public class ListenerChar implements Runnable{

    BufferedReader inNet;
    Controller controller;

    public ListenerChar(BufferedReader inNet, Controller controller) {
        this.inNet = inNet;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            log("listener in esecuzione");
            do {
                String message = inNet.readLine();
                if(message.contains("OK")) {
                    log(message);
                    controller.setFileSize(Long.parseLong(message.replace("OK", "").trim()));
                } else {
                    log(message);
                }
            } while(!controller.isTerminate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        System.out.println(message);
    }

}
