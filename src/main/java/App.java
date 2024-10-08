package main.java;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import main.java.service.SceneManager;

public class App extends JFrame {

    public void prepareGUI() {
        int frameWidth = 360;
        int frameHeight = 640;

        JFrame frame = new JFrame();
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            @SuppressWarnings("unused")
            SceneManager sceneManager = new SceneManager(frame);
        } catch (IOException ex) {
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.prepareGUI();
    }
}
