package test;

import controller.Client;

public class TestFakeClient {

    public static void main(String[] args) {
        new Thread(new Client("localhost", 40000)).start();
    }

}
