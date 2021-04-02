package test;

import controller.StartRunners;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DDOStest {

    public static void main(String[] args) {
        try {
            BufferedReader inTastiera = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Inserire l'indirizzo del server");
            new Thread(new StartRunners(inTastiera.readLine())).start();
            System.err.println("Avviato");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
