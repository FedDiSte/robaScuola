package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

//classe utilizzata per leggere il flusso di dati del client mittente e inviarlo al destinatario
public class Read implements Runnable {

    private Socket me;
    private ArrayList<Send> socketsOutput;
    private String name;

    private BufferedReader reader;
    private PrintWriter writer;

    public Read(Socket me, ArrayList<Send> socketsOutput, String name) {
        this.me = me;
        this.socketsOutput = socketsOutput;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(me.getInputStream()));
            do {
                //leggo il messaggio
                String msg = reader.readLine();

                //leggo il mittente
                String destinatario = reader.readLine();

                //logging
                System.out.println("Messaggio da parte di: " + name);
                System.out.println("il messaggio: " + msg);
                System.out.println("Diretto a: " + destinatario + "\n\n");

                //cerco in ogni Oggetto di tipo Send, nell'ArrayList socketsOutput il destinatario
                for (Send a: socketsOutput) {
                    if(a.getName().equals(destinatario)) {
                        //una volta trovato invio il frame di messaggi seguendo lo schema "messaggio" -> nome del mittente -> messaggio attuale formattato come nomemittente: messaggio
                        writer = new PrintWriter(a.getSocket().getOutputStream(), true);
                        //Stringa utilizzata dal client per capire se è in arrivo un messaggio
                        writer.println("messaggio");
                        writer.flush();
                        writer.println(name);
                        writer.flush();
                        writer.println(name + ": " + msg);
                        writer.flush();
                        System.out.println("messaggio inviato correttamente a: " + destinatario);
                    }
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
