package main.java.controller;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class RestartMenu extends JPanel {
    // JFrame size
    public static final int FRAME_WIDTH = 360;
    public static final int FRAME_HEIGHT = 640;
    // Lose properties
    JButton jbt1 = new JButton("Button1");
    JButton jbt2 = new JButton("Button3");

    Box box = Box.createVerticalBox();

    public RestartMenu(FlappyBird f) {
        // Lose handler
        this.setLayout(new GridBagLayout()); // Use GridBagLayout to center the box

        // Create GridBagConstraints to center the box in both directions
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Center horizontally
        gbc.gridy = 0; // Center vertically
        gbc.weightx = 1; // Allow horizontal stretching
        gbc.weighty = 1; // Allow vertical stretching
        gbc.anchor = GridBagConstraints.CENTER; // Center the component
        gbc.fill = GridBagConstraints.NONE; // Do not stretch the box
        this.add(box, gbc);

        box.add(jbt1);
        box.add(Box.createVerticalStrut(50));
        box.add(jbt2);

        f.setLayout(new BorderLayout());
        f.add(this, BorderLayout.CENTER);
    }
}
