package test;

import control.ClientSender;

public class Test {

    public static void main(String[] args) {
            new Thread(new ClientSender("localhost", 3030)).start();
    }

}

