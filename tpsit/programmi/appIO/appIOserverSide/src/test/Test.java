package test;

import control.connectionHandler;

public class Test {

    public static void main(String[] args) {
        new Thread(new connectionHandler(1606)).start();
    }

}
