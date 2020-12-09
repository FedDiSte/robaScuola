package control;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListener implements Runnable{

    BufferedReader inNet;

    public ClientListener(BufferedReader inNet) {
        this.inNet = inNet;
        System.err.println("Listener del client avviato");
    }

    @Override
    public void run() {
        String line = "";
        do{
            try {
                line = inNet.readLine();
                if(line.trim().toLowerCase().equals("quit")) {
                    System.exit(0);
                }
                if(line.contains("Errore: ")) {
                    System.err.println(line);
                } else {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(true);
    }
}
