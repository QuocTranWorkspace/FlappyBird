package main.java.model;

import java.awt.Image;

public class Pipe {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 512;

    private Position2D position2D;
    private int pipeWidth;
    private int pipeHeight;
    private boolean isPassed;
    private Image pipeImage;

    public Pipe(Image pipeImage) {
        this.pipeWidth = WIDTH;
        this.pipeHeight = HEIGHT;
        this.pipeImage = pipeImage;
        this.isPassed = false;
    }

    public Position2D getPosition2D() {
        return position2D;
    }

    public void setPosition2D(Position2D position2D) {
        this.position2D = position2D;
    }

    public int getPipeWidth() {
        return pipeWidth;
    }

    public void setPipeWidth(int pipeWidth) {
        this.pipeWidth = pipeWidth;
    }

    public int getPipeHeight() {
        return pipeHeight;
    }

    public void setPipeHeight(int pipeHeight) {
        this.pipeHeight = pipeHeight;
    }

    public boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    public Image getPipeImage() {
        return pipeImage;
    }

    public void setPipeImage(Image pipeImage) {
        this.pipeImage = pipeImage;
    }

}