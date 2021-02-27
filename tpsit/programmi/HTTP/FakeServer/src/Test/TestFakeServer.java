package Test;

import Controller.ServerHTTP;

public class  TestFakeServer {

    public static void main(String[] args) {
        new Thread(new ServerHTTP(40000)).start();
    }

}
