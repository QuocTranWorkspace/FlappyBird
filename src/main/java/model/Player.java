package main.java.model;

import java.awt.Image;
import main.java.App;

public class Player {
    private static final int WIDTH = 41;
    private static final int HEIGHT = 29;

    private static final int INIT_X = App.FRAME_WIDTH / 8;
    private static final int INIT_Y = App.FRAME_HEIGHT / 2;

    private Image asset;
    private Position2D position2D;
    private int playerWidth;
    private int playerHeight;

    public Player(Image asset) {
        this.asset = asset;
        this.playerWidth = WIDTH;
        this.playerHeight = HEIGHT;
        this.position2D = new Position2D(INIT_X, INIT_Y);
    }

    public Image getAsset() {
        return asset;
    }

    public void setAsset(Image asset) {
        this.asset = asset;
    }

    public Position2D getPosition2D() {
        return position2D;
    }

    public void setPosition2D(Position2D position2D) {
        this.position2D = position2D;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public void setPlayerWidth(int playerWidth) {
        this.playerWidth = playerWidth;
    }

    public int getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(int playerHeight) {
        this.playerHeight = playerHeight;
    }
}
