package main.java.controller;

import javax.swing.JPanel;

public class PersonalScoreBoard extends JPanel {
    private int sceneIndex = 3;

    public int getSceneIndex() {
        return sceneIndex;
    }

    public void setSceneIndex(int sceneIndex) {
        this.sceneIndex = sceneIndex;
    }
}
