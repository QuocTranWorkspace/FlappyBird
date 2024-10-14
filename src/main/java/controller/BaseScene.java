package main.java.controller;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.java.App;
import main.java.model.Player;
import main.java.service.PipeManager;
import main.java.service.SceneManager;

public abstract class BaseScene extends JPanel implements ActionListener, KeyListener {

    // Scene index fo loading scene
    int sceneIndex;

    // JFrame size
    static final int FRAME_WIDTH = App.FRAME_WIDTH;
    static final int FRAME_HEIGHT = App.FRAME_HEIGHT;

    // Fonts
    Font pixelFont = SceneManager.getPixelFont();

    // Physics 2D
    int velocityX = 3;
    int velocityY = 0;
    static final int GRAVITY = 1;

    // Component's asset
    transient Image playerImg = null;
    transient Image backgroundImg = null;
    transient Image higherPipe = null;
    transient Image lowerpipe = null;

    // Player component
    transient Player player = null;

    // Pipe Component
    transient PipeManager pipeManager = null;

    // Game timer
    transient Timer gameLoop = null;
    boolean isLose = false;
    boolean isFirst = true;
    /*
     * @Problem: Exist 2 or more time loops on initiation
     * 
     * @Solution: Check if started to start the time loop, else top the time loop of
     * previous scene
     */
    boolean isStart = false;

    // Move method
    public void move() {
    }

    public int getSceneIndex() {
        return sceneIndex;
    }

    public void setSceneIndex(int sceneIndex) {
        this.sceneIndex = sceneIndex;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
        if (isStart) {
            gameLoop.start();
        }
    }

}