package main.java.controller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import main.java.App;

public class StartMenu extends JPanel {

    // JFrame size
    static final int FRAME_WIDTH = App.FRAME_WIDTH;
    static final int FRAME_HEIGHT = App.FRAME_HEIGHT;

    private int sceneIndex = 0;
    private int redirectIndex = 1;

    // Component's asset
    transient Image backgroundImage;

    JButton playBtn = new JButton("Start");
    JButton globalScoreBtn = new JButton("Score Board");
    JButton personalScoreBtn = new JButton("Score Board");
    JButton exitBtn = new JButton("Exit");

    transient List<JButton> btnContainer = new ArrayList<>(
            Arrays.asList(playBtn, globalScoreBtn, personalScoreBtn, exitBtn));

    public StartMenu() throws IOException {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        // setFocusable: make sure falppybird class is mainly take on the key events
        setFocusable(true);
        backgroundImage = new ImageIcon(getClass().getResource("../../resources/img/flappybirdbg.png")).getImage();

        setUpStartPanel();
    }

    @SuppressWarnings("static-access")
    private void setUpStartPanel() {
        // Set the layout display horizontally
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());

        for (JButton btn : btnContainer) {
            btn.setPreferredSize(new Dimension(150, 30));
            btn.setMaximumSize(new Dimension(150, 30));
            btn.setAlignmentX(CENTER_ALIGNMENT);
            btn.setVisible(true);

            int currentRedirectIndex = redirectIndex;
            if (currentRedirectIndex < App.sceneManager.getSceneNum()) {
                btn.addActionListener(actionEvent ->
                /*
                 * @Problem: the redirectIndex is shared across all button listeners,
                 * so every button will use the final value of redirectIndex
                 * 
                 * @Solution: Declared the value at the time action is added
                 */
                App.sceneManager.loadScene(currentRedirectIndex));
            } else {
                btn.addActionListener(actionEvent -> App.sceneManager.closeApp());
            }

            this.add(btn);
            this.add(Box.createVerticalStrut(10));
            redirectIndex += 1;
        }

        this.add(Box.createVerticalGlue());
    }

    @Override
    public void paintComponent(Graphics g) {
        drawComponents(g);
    }

    private void drawComponents(Graphics g) {
        // Draw the background
        g.drawImage(backgroundImage, 0, 0, 360, 640, null);
    }

    public int getSceneIndex() {
        return sceneIndex;
    }

    public void setSceneIndex(int sceneIndex) {
        this.sceneIndex = sceneIndex;
    }

    public int getRedirectIndex() {
        return redirectIndex;
    }

    public void setRedirectIndex(int redirectIndex) {
        this.redirectIndex = redirectIndex;
    }

}
