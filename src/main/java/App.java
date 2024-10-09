package main.java;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import main.java.service.SceneManager;

public class App extends JFrame {

    public static SceneManager sceneManager;

    public void prepareGUI() {
        int frameWidth = 360;
        int frameHeight = 640;

        JFrame frame = new JFrame();
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        sceneManager = new SceneManager(frame);
    }

    public static void main(String[] args) {
        App app = new App();
        app.prepareGUI();
    }

}
