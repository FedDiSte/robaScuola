package test;

import controller.ServerUDP;

public class Test {

    public static void main(String[] args) {
        new Thread(new ServerUDP(1606)).start();
    }

}
