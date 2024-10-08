package main.java.service;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import main.java.controller.FlappyBird;
import main.java.model.Pipe;
import main.java.model.Position2D;

public class PipeManager implements ActionListener {

    private List<Pipe> pipes = new ArrayList<>();
    private Timer pipeGenerateLoop;
    private Image higherPipeImage;
    private Image lowerPipeImage;

    public static final int SCORE_SPACE = FlappyBird.FRAME_HEIGHT / 4;
    private static final Random random = new Random();

    public PipeManager(Image higherPipeImage, Image lowerPipeImage) {
        this.higherPipeImage = higherPipeImage;
        this.lowerPipeImage = lowerPipeImage;

        pipeGenerateLoop = new Timer(1500, this);
        pipeGenerateLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int rand = random.nextInt(FlappyBird.FRAME_HEIGHT / 2);

        Pipe upperPipe = new Pipe(higherPipeImage);
        Pipe lowerPipe = new Pipe(lowerPipeImage);

        int posYUpper = -upperPipe.getPipeHeight() + FlappyBird.FRAME_HEIGHT / 8 + rand;
        int posYLower = FlappyBird.FRAME_HEIGHT / 8 + rand + SCORE_SPACE;

        upperPipe.setPosition2D(new Position2D(FlappyBird.FRAME_WIDTH, posYUpper));
        lowerPipe.setPosition2D(new Position2D(FlappyBird.FRAME_WIDTH, posYLower));

        pipes.add(upperPipe);
        pipes.add(lowerPipe);
    }

    public Timer getPipeGenerateLoop() {
        return pipeGenerateLoop;
    }

    public void setPipeGenerateLoop(Timer pipeGenerateLoop) {
        this.pipeGenerateLoop = pipeGenerateLoop;
    }

    public List<Pipe> getPipes() {
        return pipes;
    }

    public void setPipes(List<Pipe> pipes) {
        this.pipes = pipes;
    }

    public Image getHigherPipeImage() {
        return higherPipeImage;
    }

    public void setHigherPipeImage(Image higherPipeImage) {
        this.higherPipeImage = higherPipeImage;
    }

    public Image getLowerPipeImage() {
        return lowerPipeImage;
    }

    public void setLowerPipeImage(Image lowerPipeImage) {
        this.lowerPipeImage = lowerPipeImage;
    }

}
