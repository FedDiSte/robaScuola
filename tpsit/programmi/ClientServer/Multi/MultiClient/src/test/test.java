package test;

import control.Input;
import control.Output;
import view.*;

public class test {
    public static void main(String[] args) {
        Output out = new Output();
        GuiChatSelector gui = new GuiChatSelector(out);
        String myname = gui.dialogGetter();
        Input input = new Input(gui.getGuiChat(), gui);

        out.setMyName(myname);

        Thread t1 = new Thread(input);
        t1.start();
    }
}
