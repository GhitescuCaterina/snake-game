package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOverScreen extends Scene {

    private BufferedImage retry, exit, retryPressed, exitPressed, back, backPressed;
    private MouseListener mouseListener;
    public BufferedImage exitCurrentImage, retryCurrentImage, backCurrentImage;
    public Rect exitRect, retryRect, backRect;
    public KeyboardListener keyboardListener;

    public GameOverScreen(MouseListener mouseListener, KeyListener keyListener) {
        try {
            retry = ImageIO.read(new File("pictures/retry.png"));
            retryPressed = ImageIO.read(new File("pictures/retry-pressed.png"));
            exit = ImageIO.read(new File("pictures/exit2.png"));
            exitPressed = ImageIO.read(new File("pictures/exit2-pressed.png"));
            back = ImageIO.read(new File("pictures/back.png"));
            backPressed = ImageIO.read(new File("pictures/back-pressed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mouseListener = mouseListener;
        this.keyboardListener = keyboardListener;

        exitCurrentImage = exit;
        retryCurrentImage = retry;
        backCurrentImage = back;

        exitRect = new Rect(320, 100, 600, 200, Direction.UP);
        retryRect = new Rect(150, 350, 400, 200, Direction.UP);
        backRect = new Rect(150, 350, 400, 200, Direction.UP);
    }

    @Override
    public void update(double dt) {

        if(mouseListener.getX() >= retryRect.x && mouseListener.getX() <= retryRect.x + retryRect.width &&
                mouseListener.getY() >= retryRect.y && mouseListener.getY() <= retryRect.y + retryRect.height) {
            retryCurrentImage = retryPressed;
            if(mouseListener.isPressed())
                Window.getWindow().changeState(1);
        }else{
            retryCurrentImage = retry;
        }

        if(mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width &&
                mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height) {
            exitCurrentImage = exitPressed;
            if(mouseListener.isPressed())
                Window.getWindow().close();
        }else{
            exitCurrentImage = exit;
        }

        if(mouseListener.getX() >= backRect.x && mouseListener.getX() <= backRect.x + backRect.width &&
                mouseListener.getY() >= backRect.y && mouseListener.getY() <= backRect.y + backRect.height) {
            backCurrentImage = backPressed;
            if(mouseListener.isPressed())
                Window.getWindow().changeState(0);
        }else{
            backCurrentImage = back;
        }

        boolean isOverAnyButton = checkMouseAndSetCursor(retryRect, retryCurrentImage, retryPressed);
        isOverAnyButton = checkMouseAndSetCursor(backRect, backCurrentImage, backPressed) || isOverAnyButton;
        isOverAnyButton = checkMouseAndSetCursor(exitRect, exitCurrentImage, exitPressed) || isOverAnyButton;

        if (!isOverAnyButton) {
            Window.getWindow().setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // cursor normal
        }

    }

    private boolean checkMouseAndSetCursor(Rect rect, BufferedImage currentImage, BufferedImage pressedImage) {
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        if(mouseListener.getX() >= rect.x && mouseListener.getX() <= rect.x + rect.width &&
                mouseListener.getY() >= rect.y && mouseListener.getY() <= rect.y + rect.height) {
            currentImage = pressedImage;
            Window.getWindow().setCursor(handCursor); // cursor pointer
            return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(new Color(146, 151, 196));
        g.fillRect(0, 0 , Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        g.setColor(new Color(30, 100, 81));
        g2d.setFont(new Font("Century Gothic", Font.BOLD, 70));
        g2d.drawString("You lost! :(", (Constant.SCREEN_WIDTH)/2 - 180, 200);
        g2d.drawString("What do you wanna do now?", (Constant.SCREEN_WIDTH)/2 - 500, 300);

        retryRect = new Rect(330, (Constant.SCREEN_HEIGHT - retryRect.height)/2 + 5, 210, 134, Direction.UP);
        exitRect = new Rect(680, (Constant.SCREEN_HEIGHT - exitRect.height)/2, 213, 140, Direction.UP);
        backRect = new Rect((Constant.SCREEN_WIDTH - backRect.width) / 2, (Constant.SCREEN_HEIGHT - backRect.height)/2 + 150, 324, 120, Direction.UP);

        g.drawImage(retryCurrentImage, (int)retryRect.x, (int)retryRect.y, (int)retryRect.width, (int)retryRect.height, null);
        g.drawImage(exitCurrentImage, (int)exitRect.x, (int)exitRect.y, (int)exitRect.width, (int)exitRect.height, null);
        g.drawImage(backCurrentImage, (int)backRect.x, (int)backRect.y, (int)backRect.width, (int)backRect.height, null);

    }
}