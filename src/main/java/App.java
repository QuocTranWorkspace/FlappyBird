package main.java;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import main.java.service.SceneManager;

public class App extends JFrame {

    // App size
    public static final int FRAME_WIDTH = 360;
    public static final int FRAME_HEIGHT = 640;

    private static final JFrame frame = new JFrame();

    public static final SceneManager sceneManager = new SceneManager(frame);

    public void prepareGUI() {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Need to be pack after add to frame to display and apply the preferred size
        frame.pack();
        frame.setVisible(true);
    }

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        App app = new App();
        app.prepareGUI();
    }

}