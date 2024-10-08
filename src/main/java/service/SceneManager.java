package main.java.service;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import main.java.controller.FlappyBird;

public class SceneManager {

    public SceneManager(JFrame frame) throws IOException {
        // init stage
        FlappyBird flappyBird = new FlappyBird();

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
                Logger.getLogger(ex.getMessage());
            }
        });
    }
}
