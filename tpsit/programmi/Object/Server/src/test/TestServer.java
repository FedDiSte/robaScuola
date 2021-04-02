package test;

import controller.ObjectServer;

public class TestServer {

    public static void main(String[] args) {
        new Thread(new ObjectServer(40000)).start();
    }

}
