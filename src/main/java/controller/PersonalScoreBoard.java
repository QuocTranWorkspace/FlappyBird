package main.java.controller;

import javax.swing.JPanel;
import main.java.App;

public class PersonalScoreBoard extends JPanel {
    // JFrame size
    static final int FRAME_WIDTH = App.FRAME_WIDTH;
    static final int FRAME_HEIGHT = App.FRAME_HEIGHT;

    private int sceneIndex = 3;

    public int getSceneIndex() {
        return sceneIndex;
    }

    public void setSceneIndex(int sceneIndex) {
        this.sceneIndex = sceneIndex;
    }
}
