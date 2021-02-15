package controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Controller {

    private boolean terminate;

    private Integer clientPort = null;
    private InetAddress clientIP = null;

    public Controller()  {
        terminate = false;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public Integer getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

    public boolean hasClientPort() {
        return clientPort != null;
    }

    public InetAddress getClientIP() {
        return clientIP;
    }

    public void setClientIP(InetAddress clientIP) throws UnknownHostException {
        this.clientIP = clientIP;
    }

    public boolean hasClientIP() {
        return clientIP != null;
    }

}
