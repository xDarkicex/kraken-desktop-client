package io.bitdev.kraken;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Created by gentryrolofson on 12/10/16.
 */
public class UI {
    public static Client kraken;
    public static JTextPane statusText;

    public static void main(String[] args) {
        // Open connection to kraken
        try {
            kraken = new Client("localhost", 4980);
        } catch(IOException e) {
            // Dialog! Later.
            System.err.println("Error Connecting to Kraken Arm!");
        }

        // Container for our container, basically the root
        JFrame frame = new JFrame("Finglonger");

        // Container for everything else
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        // Container for buttons
        JPanel buttonPanel = new JPanel();

        // Status Button
        JButton statusButton = new JButton("Check Status");
        statusButton.setPreferredSize(new Dimension(120, 40));
        buttonPanel.add(statusButton);
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusText.setText(kraken.status());
            }
        });

        // Add buttons to panel
        panel.add(buttonPanel);

        // Text Pane for status
        statusText = new JTextPane();
//        statusText.setText("");
        statusText.setEditable(false);
        statusText.setMargin(new Insets(10, 10, 10, 10));
        panel.add(statusText);

        // Throw it onto the frame.
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setVisible(true);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> kraken.close()));
    }
}
