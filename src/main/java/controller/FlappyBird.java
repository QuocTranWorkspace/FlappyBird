package main.java.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.java.model.Pipe;
import main.java.model.Player;
import main.java.model.Position2D;
import main.java.service.PipeManager;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    // JFrame size
    public static final int FRAME_WIDTH = 360;
    public static final int FRAME_HEIGHT = 640;
    private JFrame frame;

    // Physics 2D
    int velocityX = -3;
    int velocityY = 0;
    static final int GRAVITY = 1;

    // Component's asset
    transient Image playerImg;
    transient Image backgroundImage;
    transient Image higherPipe;
    transient Image lowerpipe;

    // Player component
    transient Player player;

    // Pipe Component
    transient PipeManager pipeManager;

    // Game timer
    Timer gameLoop;
    boolean isLose = false;

    // Score
    double score = 0;
    private JButton restartBtn = new JButton("Restart");
    private JButton menuBtn = new JButton("Menu");

    public FlappyBird(JFrame frame) throws IOException {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        // setFocusable: make sure falppybird class is mainly take on the key events
        setFocusable(true);

        // addKeyListener()
        addKeyListener(this);

        playerImg = new ImageIcon(getClass().getResource("../../resources/img/flappybird.png")).getImage();
        backgroundImage = new ImageIcon(getClass().getResource("../../resources/img/flappybirdbg.png")).getImage();
        higherPipe = new ImageIcon(getClass().getResource("../../resources/img/toppipe.png")).getImage();
        lowerpipe = new ImageIcon(getClass().getResource("../../resources/img/bottompipe.png")).getImage();

        player = new Player(playerImg);

        pipeManager = new PipeManager(higherPipe, lowerpipe);

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        setUpLosePanel();

        this.frame = frame;
    }

    private void setUpLosePanel() {
        // Set the layout display horizontally
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Set the buttons to be Center both directions
        restartBtn.setPreferredSize(new Dimension(80, 30));
        menuBtn.setMaximumSize(new Dimension(80, 30));
        restartBtn.setAlignmentX(CENTER_ALIGNMENT);
        restartBtn.setVisible(false);

        menuBtn.setPreferredSize(new Dimension(80, 30));
        restartBtn.setMaximumSize(new Dimension(80, 30));
        menuBtn.setAlignmentX(CENTER_ALIGNMENT);
        menuBtn.setVisible(false);

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
        g.drawImage(backgroundImage, 0, 0, 360, 640, null);

        // Draw the player
        g.drawImage(player.getAsset(), player.getPosition2D().getX(), player.getPosition2D().getY(),
                player.getPlayerWidth(), player.getPlayerHeight(),
                null);

        // Draw the pipes
        List<Pipe> pipes = pipeManager.getPipes();

        for (Pipe p : pipes) {
            g.drawImage(p.getPipeImage(), p.getPosition2D().getX(), p.getPosition2D().getY(), p.getPipeWidth(),
                    p.getPipeHeight(), null);
            if (collision2D(player, p)) {
                isLose = true;
            }
        }

        // Draw the score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Rockwell", Font.PLAIN, 16));
        if (isLose) {
            restartBtn.setVisible(true);
            menuBtn.setVisible(true);
            g.drawString("", 10, 35);
        } else {
            g.drawString("Score: " + (int) score, 10, 35);
        }

        if (player.getPosition2D().getY() > FRAME_HEIGHT) {
            player.getPosition2D().setY(FRAME_HEIGHT - 2 * player.getPlayerHeight());
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
    public void move() {
        // Apply physics to velocity (the movement of player)
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

        // Move pipes
        // Get pipe current position
        List<Pipe> pipes = pipeManager.getPipes();
        for (Pipe p : pipes) {
            Position2D pipePsition = p.getPosition2D();
            int pipeX = pipePsition.getX();
            int pipeY = pipePsition.getY();

            // Add score at the first time pass the pipes (upper and lower pipe)
            if (!p.getIsPassed()
                    && playerPosition.getX() - player.getPlayerWidth() > p.getPosition2D().getX() + p.getPipeWidth()) {
                score += 0.5;
                p.setIsPassed(!p.getIsPassed());
            }

            if (pipeX + p.getPipeWidth() < 0) {
                pipes.remove(p);
                return;
            }

            pipeX += velocityX;
            Position2D newPipePos = new Position2D(pipeX, pipeY);
            p.setPosition2D(newPipePos);
        }
    }

    public void deathBgAnimation() {
        Point currLocation;
        int iDisplaceXBy = 5;
        int iDisplaceYBy = -10;
        currLocation = this.getLocationOnScreen();

        Point position1 = new Point(currLocation.x + iDisplaceXBy, currLocation.y
                + iDisplaceYBy);
        Point position2 = new Point(currLocation.x - iDisplaceXBy, currLocation.y
                - iDisplaceYBy);
        for (int i = 0; i < 1200; i++) {
            this.setLocation(position1);
            this.setLocation(position2);
        }
        this.setLocation(currLocation);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        revalidate();
        move();
        repaint();

        if (isLose) {
            for (int i = 0; i < 30; i++) {
                velocityY = -i;
            }
            Timer timer = new Timer(200, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("im ded");
                    // Release all the memory
                    gameLoop.stop();
                    gameLoop.removeActionListener(this);

                    pipeManager.getPipeGenerateLoop().stop();
                    pipeManager.getPipeGenerateLoop().removeActionListener(pipeManager);
                }
            });
            timer.setRepeats(false);
            timer.start();
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
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
