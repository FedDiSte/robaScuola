package test;

import controller.ClientSide;

public class Test {

    public static void main(String[] args) {
        new Thread(new ClientSide("localhost", 1606)).start();
    }

}
