package main.java.controller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class StartMenu extends JPanel {

    // JFrame size
    public static final int FRAME_WIDTH = 360;
    public static final int FRAME_HEIGHT = 640;

    // Component's asset
    transient Image backgroundImage;

    public StartMenu() throws IOException {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        // setFocusable: make sure falppybird class is mainly take on the key events
        setFocusable(true);
        backgroundImage = new ImageIcon(getClass().getResource("../../resources/img/flappybirdbg.png")).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawComponents(g);
    }

    private void drawComponents(Graphics g) {
        // Draw the background
        g.drawImage(backgroundImage, 0, 0, 360, 640, null);
    }

}
