package controller;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ListenerBit implements Runnable {

    Socket bitNet;

    Controller controller;

    BufferedInputStream inBitNet;

    public ListenerBit(BufferedInputStream inBitNet, Controller controller) {
        this.inBitNet = inBitNet;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            log("download iniziato");
            byte[] b = downloadFile(controller.getFileSize());
            log("download completato, inizio salvataggio file");
            saveFile(b);
            log("salvataggio completato");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] downloadFile(long size) throws IOException {
        byte[] result = new
        return result;
    }

    private void saveFile(byte[] data) throws IOException {
        if(Files.exists(Paths.get(controller.getFilename()))) {
            log("Il file è gia presente sul dispositivo");
        } else {
            File file = new File(controller.getFilename());
            BufferedOutputStream outBitFile = new BufferedOutputStream(new FileOutputStream(file));
            outBitFile.write(data);
            outBitFile.close();
        }
    }

    private void log(Object message) {
        System.err.println(message);
    }

}
