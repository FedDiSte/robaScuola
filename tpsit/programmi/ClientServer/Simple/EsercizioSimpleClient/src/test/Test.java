package test;

import control.SimpleClient;

public class Test {
    public static void main(String[] args) {
        Thread client = new Thread(new SimpleClient());

        client.start();
    }
}
