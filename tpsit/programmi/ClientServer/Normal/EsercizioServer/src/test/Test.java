
public class Test {
    public static void main(String[] args) {
        Gui gui = new Gui();
        Thread sender = new Thread(new ServerSender(gui));
        Thread reciever = new Thread(new ServerReciever(gui));
        
        sender.start();
        reciever.start();
    }

}
