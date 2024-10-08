package main.java.service;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import main.java.controller.FlappyBird;

public class SceneManager {

    JButton playButton;
    JPanel currentScene;

    FlappyBird flappyBird;

    public SceneManager(JFrame frame) throws IOException {
        // init stage
        flappyBird = new FlappyBird(frame);

        frame.add(flappyBird);
        frame.pack();
        // Request focus for flappybird class: required class flappybird setFocusable()
        flappyBird.requestFocus();
        frame.setVisible(true);

        // Reset the game scene
        JButton restaButton = flappyBird.getRestartBtn();
        restaButton.addActionListener((ActionEvent e) -> {
            try {
                frame.remove(flappyBird);
                @SuppressWarnings("unused")
                SceneManager sceneManager = new SceneManager(frame);
            } catch (IOException ex) {
            }
        });
    }
}
