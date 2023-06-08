package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainMenu extends Scene{

    public KeyListener keyListener;
    public MouseListener mouseListener;

    public BufferedImage title, play, playPressed, exit, exitPressed, about, aboutPressed, scores, scoresPressed;
    public Rect titleRect, playRect, aboutRect, exitRect, scoresRect;
    private Game game;
    public BufferedImage playCurrentImage, exitCurrentImage, aboutCurrentImage, scoresCurrentImage;

    public MainMenu(KeyListener keyListener, MouseListener mouseListener, Game game) {
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;
        this.game = game;
        try{
            title = ImageIO.read(new File("pictures/title.png"));
            play = ImageIO.read(new File("pictures/start.png"));
            playPressed = ImageIO.read(new File("pictures/start-pressed.png"));
            about = ImageIO.read(new File("pictures/about.png"));
            aboutPressed = ImageIO.read(new File("pictures/about-pressed.png"));
            exit = ImageIO.read(new File("pictures/exit.png"));
            exitPressed = ImageIO.read(new File("pictures/exit-pressed.png"));
            scores = ImageIO.read(new File("pictures/scores.png"));
            scoresPressed = ImageIO.read(new File("pictures/scores-pressed.png"));

        }catch(Exception e){
            e.printStackTrace();
        }

        playCurrentImage = play;
        aboutCurrentImage = about;
        exitCurrentImage = exit;
        scoresCurrentImage = scores;

        titleRect = new Rect(320, 100, 600, 200, Direction.UP);
        playRect = new Rect(150, 350, 400, 200, Direction.UP);
        aboutRect = new Rect(800, 475, 300, 150, Direction.UP);
        exitRect = new Rect(525, 650, 250, 75, Direction.UP);
        scoresRect = new Rect(400, 500, 250, 70, Direction.UP);

    }
    @Override
    public void update(double dt) {
        if (mouseListener.getX() >= playRect.x && mouseListener.getX() <= playRect.x + playRect.width &&
                mouseListener.getY() >= playRect.y && mouseListener.getY() <= playRect.y + playRect.height) {
            playCurrentImage = playPressed;
            if (mouseListener.isPressed()) {
                InputDialog inputDialog = new InputDialog(Window.getWindow(), game); // Pass the game instance
                inputDialog.setVisible(true);

                // Start the game after the input dialog is closed
                if (!inputDialog.isVisible()) {
                    Window.getWindow().changeState(1);
                }
            }
        } else {
            playCurrentImage = play;
        }

        if(mouseListener.getX() >= aboutRect.x && mouseListener.getX() <= aboutRect.x + aboutRect.width &&
                mouseListener.getY() >= aboutRect.y && mouseListener.getY() <= aboutRect.y + aboutRect.height) {
            aboutCurrentImage = aboutPressed;

            if(mouseListener.isPressed())
                Window.getWindow().changeState(4);

        }else{
            aboutCurrentImage = about;
        }

        if(mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width &&
                mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height) {
            exitCurrentImage = exitPressed;
            if(mouseListener.isPressed())
                Window.getWindow().close();
        }else{
            exitCurrentImage = exit;
        }

        if (mouseListener.getX() >= scoresRect.x && mouseListener.getX() <= scoresRect.x + scoresRect.width &&
                mouseListener.getY() >= scoresRect.y && mouseListener.getY() <= scoresRect.y + scoresRect.height) {
            scoresCurrentImage = scoresPressed;
            if (mouseListener.isPressed()) {
                Window.getWindow().changeState(5);
            }
        } else {
            scoresCurrentImage = scores;
        }

        boolean isOverAnyButton = checkMouseAndSetCursor(playRect, playCurrentImage, playPressed);
        isOverAnyButton = checkMouseAndSetCursor(aboutRect, aboutCurrentImage, aboutPressed) || isOverAnyButton;
        isOverAnyButton = checkMouseAndSetCursor(exitRect, exitCurrentImage, exitPressed) || isOverAnyButton;
        isOverAnyButton = checkMouseAndSetCursor(scoresRect, scoresCurrentImage, scoresPressed) || isOverAnyButton;

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
        g.setColor(new Color(146, 151, 196));
        g.fillRect(0, 0 , Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        titleRect = new Rect((Constant.SCREEN_WIDTH - titleRect.width) / 2, 60, 630,300 , Direction.UP);
        playRect = new Rect((Constant.SCREEN_WIDTH - playRect.width) / 2, 380, 500,100, Direction.UP );
        aboutRect = new Rect((Constant.SCREEN_WIDTH - aboutRect.width) / 2, 580, 500,100, Direction.UP);
        exitRect = new Rect((Constant.SCREEN_WIDTH - exitRect.width) / 2, 680, 500, 100, Direction.UP);
        scoresRect = new Rect((Constant.SCREEN_WIDTH - scoresRect.width)/2, 480, 500, 100, Direction.UP);

        g.drawImage(title, (int)titleRect.x, (int)titleRect.y, (int)titleRect.width, (int)titleRect.height, null);
        g.drawImage(playCurrentImage, (int)playRect.x, (int)playRect.y, (int)playRect.width, (int)playRect.height, null);
        g.drawImage(scoresCurrentImage, (int)scoresRect.x, (int)scoresRect.y, (int)scoresRect.width, (int)scoresRect.height, null);
        g.drawImage(aboutCurrentImage, (int)aboutRect.x, (int)aboutRect.y, (int)aboutRect.width, (int)aboutRect.height, null);
        g.drawImage(exitCurrentImage, (int)exitRect.x, (int)exitRect.y, (int)exitRect.width, (int)exitRect.height, null);
    }
}