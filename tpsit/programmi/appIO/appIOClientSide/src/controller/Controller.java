package controller;

public class Controller {

    boolean terminate;

    public Controller() {
        terminate = false;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate() {
        terminate = true;
    }

}
