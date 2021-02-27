package test;

import controller.Server;

public class PrenotazioniUDPServerTest {

    public static void main(String[] args) {
        new Thread(new Server(1606)).start();
    }

}
