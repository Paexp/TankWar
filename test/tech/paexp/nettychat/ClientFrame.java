package tech.paexp.nettychat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    public static final ClientFrame INSTANCE = new ClientFrame();

    private final TextArea ta = new TextArea();
    private final TextField tf = new TextField();

    private Client c = null;

    private ClientFrame() {
        this.setSize(300, 400);
        this.setLocation(400, 20);
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);
        this.setTitle("ChatRoom");

        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // send to server
                c.send(tf.getText());
                //ta.setText(ta.getText() + tf.getText() + "\r\n");
                tf.setText("");
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                c.closeConnection();
                System.exit(0);

            }
        });
    }

    public void connectToServer() {
        c = new Client();
        c.connect();
    }


    public static void main(String[] args) {
        ClientFrame clientFrame = ClientFrame.INSTANCE;
        clientFrame.setVisible(true);
        clientFrame.connectToServer();
    }

    public void updateText(String str) {
        ta.setText(ta.getText() + str + "\r\n");
    }
}
