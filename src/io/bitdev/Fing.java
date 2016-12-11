package io.bitdev;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by gentryrolofson on 12/8/16.
 */
public class Fing {
    public static BinBat call = new BinBat();
    public static JTextPane statusText;

    public static void main(String[] args){
        // Container for our container, basically the root
        JFrame frame = new JFrame("Finglonger");

        // Container for everything else
        JPanel panel =  new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));

        // Container for buttons
        JPanel buttonPanel = new JPanel();

        // Status Button
        JButton statusButton = new JButton("Check Status");
        statusButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(statusButton);
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reply;
                try {
                    reply = call.exec();
                } catch(IOException exception) {
                    reply = "Fing Error: " + exception;
                }
                statusText.setText(reply);
            }
        });

        // Add buttons to panel
        panel.add(buttonPanel);

        // Text Pane for status
        statusText = new JTextPane();
//        statusText.setText("");
        statusText.setEditable(false);
        statusText.setMargin(new Insets(10,10,10,10));
        panel.add(statusText);

        // Throw it onto the frame.
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640,480);
        frame.setVisible(true);

    }
}
