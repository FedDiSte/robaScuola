import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

public class Gui {
    JFrame frame = new JFrame("Server");
    JPanel panel = new JPanel();
    JPanel subPanel = new JPanel();
    JTextPane textPane = new JTextPane();
    JScrollPane textScrollPane = new JScrollPane(textPane);
    JTextField textField = new JTextField();
    JButton button = new JButton("Invio");

    public Gui() {
        frame.add(panel);
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.add(textScrollPane);
        textPane.setAutoscrolls(true);
        textPane.setEditable(false);
        panel.add(subPanel);
        subPanel.setLayout(new GridLayout(1, 2, 10, 10));
        subPanel.add(textField);
        textField.setText("");
        subPanel.add(button);
        button.setEnabled(false);
        button.setSize(new Dimension(100, 50));
        textField.setSize(new Dimension(100, 50));

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(!textField.getText().equals("")) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!textField.getText().equals("")) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
            
        });

        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void onTextArea(String line) {
        if(line.contains("exit")) {
            textPane.setForeground(Color.BLACK);
        } else if(line.contains("Server")) {
            textPane.setForeground(Color.RED);
        } else if(line.contains("Client")) {
            textPane.setForeground(Color.BLUE);
        }
        textPane.setText(textPane.getText() + line + "\n");
    }
}
