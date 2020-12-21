package test;

import controller.ControllerFileBinari;

public class TestFileBinari {

    public static void main(String[] args) {
        ControllerFileBinari controllerFileBinari = new ControllerFileBinari();
        byte[] data = controllerFileBinari.loadFile("C:\\Users\\feded\\Documents\\RobaScuola\\robaScuola\\tpsit\\programmi\\EsercizioFileBinari\\src\\dati ");
        System.out.println(new String(data));
    }

}
