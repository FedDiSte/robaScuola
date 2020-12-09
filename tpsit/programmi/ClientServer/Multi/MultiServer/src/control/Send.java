package control;

import java.net.Socket;

//classe contenente la socket e il nome di ogni client connesso al server, verrà utilizzata nella classe read per inviare il messaggio al destinatario
public class Send {

    private Socket socket;
    private String name;

    public Send(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    public String getName() { return name; }

    public Socket getSocket() { return socket; }

}
