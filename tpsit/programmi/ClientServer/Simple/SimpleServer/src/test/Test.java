package test;

import control.SimpleServer;

public class Test {
    public static void main(String[] args) {
        Thread server = new Thread(new SimpleServer());
        server.start();
    }

}
