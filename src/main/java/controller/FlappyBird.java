package main.java.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import main.java.App;
import main.java.model.Pipe;
import main.java.model.Player;
import main.java.model.Position2D;
import main.java.service.PipeManager;

public class FlappyBird extends BaseScene {

    // Physics 2D
    int playerDeathX;
    Random rand = new Random();

    // Score
    double score = 0;
    private JButton restartBtn = new JButton("Restart");
    private JButton menuBtn = new JButton("Menu");

    public FlappyBird() throws IOException {
        // SceneIndex
        sceneIndex = 1;

        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        // setFocusable: make sure falppybird class is mainly take on the key events
        setFocusable(true);

        playerImg = new ImageIcon(getClass().getResource("../../resources/img/flappybird.png")).getImage();
        backgroundImg = new ImageIcon(getClass().getResource("../../resources/img/flappybirdbg.png")).getImage();
        higherPipe = new ImageIcon(getClass().getResource("../../resources/img/toppipe.png")).getImage();
        lowerpipe = new ImageIcon(getClass().getResource("../../resources/img/bottompipe.png")).getImage();

        gameLoop = new Timer(1000 / 90, this);

        player = new Player(playerImg);
        pipeManager = new PipeManager(higherPipe, lowerpipe);

        setUpLosePanel();

        playerDeathX = 0;
    }

    private void setUpLosePanel() {
        // addKeyListener()
        addKeyListener(this);

        // Set the layout display horizontally
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Set the buttons to be Center both directions
        restartBtn.setPreferredSize(new Dimension(80, 30));
        restartBtn.setMaximumSize(new Dimension(80, 30));
        restartBtn.setAlignmentX(CENTER_ALIGNMENT);
        restartBtn.setVisible(false);

        restartBtn.addActionListener(actionEvent -> App.sceneManager.loadScene(1));

        menuBtn.setPreferredSize(new Dimension(80, 30));
        menuBtn.setMaximumSize(new Dimension(80, 30));
        menuBtn.setAlignmentX(CENTER_ALIGNMENT);
        menuBtn.setVisible(false);

        menuBtn.addActionListener(actionEvent -> App.sceneManager.loadScene(0));

        // Space-between display
        this.add(Box.createVerticalGlue());
        this.add(restartBtn);
        this.add(Box.createVerticalStrut(10));
        this.add(menuBtn);
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

        // Draw the pipes
        List<Pipe> pipes = pipeManager.getPipes();

        for (Pipe p : pipes) {
            g.drawImage(p.getPipeImage(), p.getPosition2D().getX(), p.getPosition2D().getY(), p.getPipeWidth(),
                    p.getPipeHeight(), null);
        }

        // Draw the score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Rockwell", Font.PLAIN, 16));
        if (isLose) {
            g.drawString("", 10, 35);
        } else {
            g.drawString("Score: " + (int) score, 10, 35);
        }

        if (player.getPosition2D().getY() > FRAME_HEIGHT) {
            isLose = true;
        }
    }

    // Collision 2D
    public boolean collision2D(Player player, Pipe pipe) {
        return player.getPosition2D().getX() < pipe.getPosition2D().getX() + pipe.getPipeWidth()
                && player.getPosition2D().getX() + player.getPlayerWidth() > pipe.getPosition2D().getX()
                && player.getPosition2D().getY() < pipe.getPosition2D().getY() + pipe.getPipeHeight()
                && player.getPosition2D().getY() + player.getPlayerHeight() > pipe.getPosition2D().getY();
    }

    // Move method
    @Override
    public void move() {
        System.out.println(isStart);
        // Apply physics to velocity (the movement of player)
        velocityY += GRAVITY;

        // Move player
        // Get player current position
        Position2D playerPosition = player.getPosition2D();
        int x = playerPosition.getX();
        int y = playerPosition.getY();

        // Set the position with physics applied velocity
        x += playerDeathX;
        y += velocityY;
        if (isFirst) {
            y = Math.max(y, 0);
        }
        Position2D newPos = new Position2D(x, y);
        player.setPosition2D(newPos);

        if (y > FRAME_HEIGHT + 500) {
            restartBtn.setVisible(true);
            menuBtn.setVisible(true);
            // Release all the memory
            gameLoop.stop();
            gameLoop.removeActionListener(this);
            gameLoop = null;

            pipeManager.getPipeGenerateLoop().stop();
            pipeManager.getPipeGenerateLoop().removeActionListener(pipeManager);
            pipeManager.setPipeGenerateLoop(null);
        }

        // Move pipes
        // Get pipe current position
        if (!isLose) {
            List<Pipe> pipes = pipeManager.getPipes();
            for (Pipe p : pipes) {
                if (collision2D(player, p)) {
                    isLose = true;
                }
                Position2D pipePsition = p.getPosition2D();
                int pipeX = pipePsition.getX();
                int pipeY = pipePsition.getY();

                // Add score at the first time pass the pipes (upper and lower pipe)
                if (!p.getIsPassed()
                        && playerPosition.getX() > p.getPosition2D().getX()
                                + p.getPipeWidth()) {
                    score += 0.5;
                    p.setIsPassed(!p.getIsPassed());
                }

                pipeX -= velocityX;
                Position2D newPipePos = new Position2D(pipeX, pipeY);
                p.setPosition2D(newPipePos);
            }
        }
    }

    public void deathAnimation() {
        playerDeathX = rand.nextInt(-5, 10);
        velocityY = -20;

        // Change image
        playerImg = new ImageIcon(getClass().getResource("../../resources/img/flappybird - backflip.png"))
                .getImage();
        player.setAsset(playerImg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        revalidate();
        move();
        repaint();

        if (isLose && isFirst) {
            isFirst = !isFirst;

            deathAnimation();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        /*
         * Too little options of keys
         */
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isLose && e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -12;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*
         * The game needs to response as the player clicks not releases
         */
    }

    public JButton getRestartBtn() {
        return restartBtn;
    }

    public void setRestartBtn(JButton restartBtn) {
        this.restartBtn = restartBtn;
    }

    public JButton getMenuBtn() {
        return menuBtn;
    }

    public void setMenuBtn(JButton menuBtn) {
        this.menuBtn = menuBtn;
    }
}
