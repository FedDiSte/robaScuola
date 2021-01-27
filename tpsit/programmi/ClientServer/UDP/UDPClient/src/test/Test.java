package test;

import controller.ClientUDP;

public class Test {

    public static void main(String[] args) {
        new Thread(new ClientUDP("localhost", 1606)).start();
    }

}
