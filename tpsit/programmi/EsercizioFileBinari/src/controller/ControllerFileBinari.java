package controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ControllerFileBinari {

    public ControllerFileBinari() {

    }

    public byte[] loadFile(String nomeFile) {
        byte[] result = null;
        try {
            if(Files.exists(Paths.get(nomeFile))) {
                long fileSize = Files.size(Paths.get(nomeFile));
                result = new byte[(int) fileSize];
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(nomeFile));
                in.read(result);
                in.close();
            } else {
                System.out.println("file non trovato");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
