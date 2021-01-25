package test;

import controller.ClientFTP;

public class test {

    public static void main(String[] args) {
        new Thread(new ClientFTP("localhost", 3030)).start();
    }

}
