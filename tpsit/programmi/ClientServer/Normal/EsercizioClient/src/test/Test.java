
public class Test {
    public static void main(String[] args) {
        Gui gui = null;
        gui = new Gui();
        Thread sender = new Thread(new ClientSender(gui));
        Thread reciever = new Thread(new ClientReciever(gui));

        reciever.start();
        sender.start();
    }
}
