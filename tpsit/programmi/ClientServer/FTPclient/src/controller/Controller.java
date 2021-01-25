package controller;

public class Controller {

    private boolean terminate;
    private String filename;
    private long size;

    public Controller() {
        size = 0;
        terminate = false;
        filename = null;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFileSize() {
        return size;
    }

    public void setFileSize(long size) {
        this.size = size;
    }

}
