package test;

import control.Accept;
import control.Update;

public class Test {
    public static void main(String[] args) {
        Accept accept = new Accept();
        Thread t = new Thread(accept);
        t.start();
    }
}
