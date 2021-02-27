package model;

public class Port {

    private boolean occupied;
    private int port;

    public Port(int port) {
        this.port = port;
        occupied = false;
    }

    public int getPort() {
        return port;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied() {
        occupied = true;
    }

    public void removeOccupied() {
        occupied = false;
    }

}
