package main.java.controller;

import javax.swing.JPanel;

public class GlobalScoreBoard extends JPanel {
    private int sceneIndex = 2;

    public int getSceneIndex() {
        return sceneIndex;
    }

    public void setSceneIndex(int sceneIndex) {
        this.sceneIndex = sceneIndex;
    }
}
