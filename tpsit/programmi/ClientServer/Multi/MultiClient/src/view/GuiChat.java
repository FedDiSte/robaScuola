package view;

import control.Output;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

public class GuiChat {

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JFormattedTextField jFormattedTextField = new JFormattedTextField();
    private JScrollPane scrollPane1 = new JScrollPane(jFormattedTextField);
    public JTextField textField1 = new JTextField();
    public JButton button1 = new JButton("invia");

    public GuiChat(String name, Output out) {

        System.out.println("Guichat nel costruttore: " + this);

        frame.add(panel);
        frame.setTitle("Chat con: " + name);

        jFormattedTextField.setEditable(false);

        button1.setEnabled(false);

        GroupLayout contentPaneLayout = new GroupLayout(panel);
        panel.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)
                                                .addComponent(button1)))
                                .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(button1)
                                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(10, Short.MAX_VALUE))
        );

        textField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
                out.sendMessage(textField1.getText(), getName());
                jFormattedTextField.setText(jFormattedTextField.getText() + "tu: " + textField1.getText() + "\n");
                textField1.setText("");
                button1.setEnabled(false);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    public GuiChat() {

    }

    public void onTextArea(String line) {
        System.err.println("guichat nella funzione: " + this);
        jFormattedTextField.setText(jFormattedTextField .getText() + line + "\n");
    }

    public String getName() {
        String out = frame.getTitle().replace("Chat con: ", "").trim();
        return out;
    }

}