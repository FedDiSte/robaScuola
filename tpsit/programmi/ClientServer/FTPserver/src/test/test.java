package test;

import controller.ServerFTP;

public class test {

    public static void main(String[] args) {
        new Thread(new ServerFTP(3030)).start();
    }

}
