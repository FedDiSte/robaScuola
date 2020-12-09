package control;

import java.io.IOException;
import java.io.PrintWriter;

//classe utilizzata per aggiornare i client sullo stato attuale dei client connessi al server
public class Update implements Runnable {

    private Accept accept;
    private long lastUpdate = System.currentTimeMillis();

    public Update(Accept accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        try {
            do {
                //Controllo se è trascorso abbastanza tempo dall'ultimo aggiornamento, default 30 secondi
                if ((System.currentTimeMillis() - lastUpdate) > 30000) {
                    System.out.println("Aggiornamento...");
                    //Ad ogni client connesso invio i nomi degli altri connessi
                    for (Send destinatario : accept.getOutputSockets()) {
                        //Apro un printwriter con il destinatario dell'update
                        PrintWriter writer = new PrintWriter(destinatario.getSocket().getOutputStream());
                        //Stringa utilizzata dal client per capire se è in arrivo un update
                        writer.println("update");
                        writer.flush();
                        //leggo i nomi dei client connessi e se non è uguale al nome del client destinazione invio l'update
                        for (Send other : accept.getOutputSockets()) {
                            if (!other.getName().equals(destinatario.getName())) {
                                System.out.println("Sto inviando il nome: " + other.getName() + " al client:" + destinatario.getName());
                                writer.println(other.getName());
                                writer.flush();
                            }
                        }
                        writer.println("stop");
                        writer.flush();
                    }
                    lastUpdate = System.currentTimeMillis();
                    System.out.println("Aggiornamento completato...\n");
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
