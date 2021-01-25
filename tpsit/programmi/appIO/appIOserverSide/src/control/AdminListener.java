package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminListener implements Runnable{

    private BufferedReader inTastiera = new BufferedReader(new InputStreamReader(System.in));

    private Boolean terminate = false;

    public AdminListener() {

    }

    @Override
    public void run() {
        System.err.println("Quando vuoi terminare il server premi invio");
        try {
            inTastiera.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Hai richiesto la chiusura del server");
        terminate = true;
    }

    public boolean isTerminate() {
        return terminate;
    }

}
