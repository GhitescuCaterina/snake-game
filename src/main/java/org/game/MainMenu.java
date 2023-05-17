package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Cursor;

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
            title = ImageIO.read(new File("pictures/title.png"));
            play = ImageIO.read(new File("pictures/start.png"));
            playPressed = ImageIO.read(new File("pictures/start-pressed.png"));
            learn = ImageIO.read(new File("pictures/learn.png"));
            learnPressed = ImageIO.read(new File("pictures/learn-pressed.png"));
            exit =ImageIO.read(new File("pictures/exit.png"));
            exitPressed = ImageIO.read(new File("pictures/exit-pressed.png"));

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

        checkMouseAndSetCursor(playRect, playCurrentImage, playPressed);
        checkMouseAndSetCursor(learnRect, learnCurrentImage, learnPressed);
        checkMouseAndSetCursor(exitRect, exitCurrentImage, exitPressed);

        if(mouseListener.isPressed()) {
            if (playCurrentImage == playPressed) {
                Window.getWindow().changeState(1);
            } else if (exitCurrentImage == exitPressed) {
                Window.getWindow().close();
            }
        }
    }

    private void checkMouseAndSetCursor(Rect rect, BufferedImage currentImage, BufferedImage pressedImage) {
        Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        if(mouseListener.getX() >= rect.x && mouseListener.getX() <= rect.x + rect.width &&
                mouseListener.getY() >= rect.y && mouseListener.getY() <= rect.y + rect.height) {
            currentImage = pressedImage;
            Window.getWindow().setCursor(handCursor); // cursor pointer
        } else {
            Window.getWindow().setCursor(defaultCursor); // cursor normal
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(95, 134, 59));
        g.fillRect(0, 0 , Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        titleRect = new Rect((Constant.SCREEN_WIDTH - titleRect.width) / 2, 60, 630,300 , Direction.UP);
        playRect = new Rect((Constant.SCREEN_WIDTH - playRect.width) / 2, 380, 800,100, Direction.UP );
        learnRect = new Rect((Constant.SCREEN_WIDTH - learnRect.width) / 2, 480, 800,100, Direction.UP);
        exitRect = new Rect((Constant.SCREEN_WIDTH - exitRect.width) / 2, 580, 800, 100, Direction.UP);

        g.drawImage(title, (int)titleRect.x, (int)titleRect.y, (int)titleRect.width, (int)titleRect.height, null);
        g.drawImage(playCurrentImage, (int)playRect.x, (int)playRect.y, (int)playRect.width, (int)playRect.height, null);
        g.drawImage(learnCurrentImage, (int)learnRect.x, (int)learnRect.y, (int)learnRect.width, (int)learnRect.height, null);
        g.drawImage(exitCurrentImage, (int)exitRect.x, (int)exitRect.y, (int)exitRect.width, (int)exitRect.height, null);
    }
}