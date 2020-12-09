package test;

import control.Server;

public class Test {

    public static void main(String[] args) {
        new Thread(new Server(3030)).start();
    }

}
