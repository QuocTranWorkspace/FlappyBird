package main.java.controller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import main.java.App;
import main.java.model.Player;
import main.java.model.Position2D;

public class StartMenu extends BaseScene {

    private int redirectIndex = 1;

    JButton playBtn = new JButton("Start");
    JButton globalScoreBtn = new JButton("Score Board");
    JButton personalScoreBtn = new JButton("Score Board");
    JButton exitBtn = new JButton("Exit");

    transient List<JButton> btnContainer = new ArrayList<>(
            Arrays.asList(playBtn, globalScoreBtn, personalScoreBtn, exitBtn));

    public StartMenu() throws IOException {
        sceneIndex = 0;

        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        // setFocusable: make sure falppybird class is mainly take on the key events
        setFocusable(true);
        playerImg = new ImageIcon(getClass().getResource("../../resources/img/flappybird.png")).getImage();
        backgroundImg = new ImageIcon(getClass().getResource("../../resources/img/flappybirdbg.png")).getImage();

        player = new Player(playerImg);

        gameLoop = new Timer(1000 / 90, this);

        setUpStartPanel();
    }

    @SuppressWarnings("static-access")
    private void setUpStartPanel() {
        // Set the layout display horizontally
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());

        for (JButton btn : btnContainer) {
            btn.setFont(pixelFont);
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
        g.drawImage(backgroundImg, 0, 0, 360, 640, null);
        // Draw the player
        g.drawImage(player.getAsset(), player.getPosition2D().getX(), player.getPosition2D().getY(),
                player.getPlayerWidth(), player.getPlayerHeight(),
                null);
    }

    // Move method
    @Override
    public void move() {
        // Apply physics to velocity (the movement of player)
        if (velocityY == 11) {
            velocityY = -12;
        }
        velocityY += GRAVITY;

        // Move player
        // Get player current position
        Position2D playerPosition = player.getPosition2D();
        int x = playerPosition.getX();
        int y = playerPosition.getY();

        // Set the position with physics applied velocity
        y += velocityY;
        y = Math.max(y, 0);
        Position2D newPos = new Position2D(x, y);
        player.setPosition2D(newPos);

        if (!isStart) {
            gameLoop.stop();
            gameLoop.removeActionListener(this);
            gameLoop = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        revalidate();
        move();
        repaint();
    }

    public int getRedirectIndex() {
        return redirectIndex;
    }

    public void setRedirectIndex(int redirectIndex) {
        this.redirectIndex = redirectIndex;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}