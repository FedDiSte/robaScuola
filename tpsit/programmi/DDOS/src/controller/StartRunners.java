package controller;

public class StartRunners implements Runnable{

    private static int MAX_THREADS = 100000000;

    private String serverAddr;

    public StartRunners(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    @Override
    public void run() {
        for(int i = 0; i < MAX_THREADS; i++) {
            new Thread(new Runner(serverAddr)).start();
        }
    }

}
