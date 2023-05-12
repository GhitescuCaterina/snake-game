package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainMenu extends Scene{

    public KeyListener keyListener;
    public MouseListener mouseListener;

    public BufferedImage title, play, playPressed, exit, exitPressed, learn, learnPressed;
    public Rect titleRect, playRect, learnRect, exitRect;

    public BufferedImage playCurrentImage, exitCurrentImage, learnCurrentImage;

    public MainMenu(KeyListener keyListener, MouseListener mouseListener){
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;
        try{
            title = ImageIO.read(new File("")); //schimbare pozele
            play = ImageIO.read(new File(""));
            playPressed = ImageIO.read(new File(""));
            learn = ImageIO.read(new File(""));
            learnPressed = ImageIO.read(new File(""));
            exit = ImageIO.read(new File(""));
            exitPressed = ImageIO.read(new File(""));

        }catch(Exception e){
            e.printStackTrace();
        }

        playCurrentImage = play;
        learnCurrentImage = learn;
        exitCurrentImage = exit;

        titleRect = new Rect(320, 100, 600, 200, Direction.UP);
        playRect = new Rect(150, 350, 400, 200, Direction.UP);
        learnRect = new Rect(800, 475, 300, 150, Direction.UP);
        exitRect = new Rect(525, 650, 250, 75, Direction.UP);

    }
    @Override
    public void update(double dt) {
        if(mouseListener.getX() >= playRect.x && mouseListener.getX() <= playRect.x + playRect.width &&
                mouseListener.getY() >= playRect.y && mouseListener.getY() <= playRect.y + playRect.height) {
            playCurrentImage = playPressed;
            if(mouseListener.isPressed())
                Window.getWindow().changeState(1);
        }else{
            playCurrentImage = play;
        }

        if(mouseListener.getX() >= learnRect.x && mouseListener.getX() <= learnRect.x + learnRect.width &&
                mouseListener.getY() >= learnRect.y && mouseListener.getY() <= learnRect.y + learnRect.height) {
            learnCurrentImage = learnPressed;

        }else{
            learnCurrentImage = learn;
        }


        if(mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width &&
                mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height) {
            exitCurrentImage = exitPressed;
            if(mouseListener.isPressed())
                Window.getWindow().close();
        }else{
            exitCurrentImage = exit;
        }

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(95, 134, 59));
        g.fillRect(0, 0 , Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        g.drawImage(title, (int)titleRect.x, (int)titleRect.y, (int)titleRect.width, (int)titleRect.height, null);
        g.drawImage(playCurrentImage, (int)playRect.x, (int)playRect.y, (int)playRect.width, (int)playRect.height, null);
        g.drawImage(learnCurrentImage, (int)learnRect.x, (int)learnRect.y, (int)learnRect.width, (int)learnRect.height, null);
        g.drawImage(exitCurrentImage, (int)exitRect.x, (int)exitRect.y, (int)exitRect.width, (int)exitRect.height, null);
    }
}