package controller;

import java.util.ArrayList;

import model.Port;

public class SharedMemory {

    private boolean stopAccept = false;

    private ArrayList<Port> porte;

    public SharedMemory() {
        porte = new ArrayList<>();
        for(int i = 40000; i < 50000; i++) {
            porte.add(new Port(i));
        }
    }

    public synchronized int getUsablePort() {
        for (Port p : porte) {
            if(!p.isOccupied()) {
                p.setOccupied();
                return p.getPort();
            }
        }
        return -1;
    }

    public synchronized void freeAPort(int port) {
        for (Port p : porte) {
            if(p.getPort() == port) {
                p.removeOccupied();
            }
        }
    }

    public boolean isStopAccept() {
        return stopAccept;
    }

    public void setStopAccept(boolean stopAccept) {
        this.stopAccept = stopAccept;
    }

}
