package view;

import control.Output;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GuiChatSelector{

    private final JFrame frame1 = new JFrame();
    private final JPanel panel1 = new JPanel();
    private final JTextArea textArea1 = new JTextArea();
    private final JScrollPane scrollPane1 = new JScrollPane(textArea1);
    private final JLabel label1 = new JLabel("Seleziona un client dalla lista:");
    private final JTextField textField1 = new JTextField();
    private final JButton button1 = new JButton("Apri Chat");

    private final String name = "";

    private ArrayList<GuiChat> guiChat = new ArrayList<>();


    public GuiChatSelector(Output out) {

        frame1.add(panel1);
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textArea1.setEditable(false);

        button1.setEnabled(false);

        GroupLayout frame1ContentPaneLayout = new GroupLayout(panel1);
        panel1.setLayout(frame1ContentPaneLayout);
        frame1ContentPaneLayout.setHorizontalGroup(
                frame1ContentPaneLayout.createParallelGroup()
                        .addGroup(frame1ContentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(frame1ContentPaneLayout.createParallelGroup()
                                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                                        .addGroup(frame1ContentPaneLayout.createSequentialGroup()
                                                .addComponent(label1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                                                .addComponent(button1)))
                                .addContainerGap())
        );
        frame1ContentPaneLayout.setVerticalGroup(
                frame1ContentPaneLayout.createParallelGroup()
                        .addGroup(frame1ContentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(frame1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button1))
                                .addContainerGap(34, Short.MAX_VALUE))
        );

        frame1.pack();
        frame1.setVisible(true);

        textField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //TODO
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                button1.setEnabled(!textField1.getText().equals(""));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                button1.setEnabled(!textField1.getText().equals(""));
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea1.getText().contains(textField1.getText())) {
                    guiChat.add(new GuiChat(textField1.getText(), out));
                }
            }
        });
    }

    public ArrayList<GuiChat> getGuiChat() { return guiChat; }

    public void updateOnlineNames(String name) {
        textArea1.setText(name);
    }

    public String dialogGetter() {
        JDialog dialog = new JDialog(frame1, "scegli il tuo nome", true);
        JPanel panel = new JPanel(new FlowLayout());
        JButton button = new JButton("invia");
        JTextField textArea = new JTextField(10);
        final String[] name = {""};

        button.setEnabled(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textArea.getText().equals("")) {
                    name[0] = textArea.getText();
                    dialog.dispose();
                }
            }
        });

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(!textArea.getText().equals("")) {
                        name[0] = textArea.getText();
                        dialog.dispose();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(!textArea.getText().equals("")) {
                    button.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!textArea.getText().equals("")) {
                    button.setEnabled(true);
                }
            }
        });

        dialog.add(panel);

        panel.add(textArea);
        panel.add(button);

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        dialog.setSize(300, 100);
        dialog.setVisible(true);
        frame1.setTitle(name[0]);
        return name[0];
    }

}
