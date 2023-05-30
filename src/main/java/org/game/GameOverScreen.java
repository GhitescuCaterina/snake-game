package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameOverScreen extends Scene {

    private BufferedImage retry, exit;
    private MouseListener mouseListener;

    public GameOverScreen() {
        try {
            retry = ImageIO.read(new File("pictures/retry.png"));
            exit = ImageIO.read(new File("pictures/exit2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mouseListener = new MouseListener();
    }

//    public void draw(Graphics2D g2) {
//        // Draw your game over message
//        g2.setColor(Color.RED);
//        g2.drawString("You lost! :( I'm sorry", 100, 100);
//
//        // Draw your buttons
//        g2.drawImage(retry, 100, 150, null);
//        g2.drawImage(exit, 200, 150, null);
//    }

    private int retryButtonX = 100;
    private int retryButtonY = 150;
    private int retryButtonWidth = 50;  // Adjust based on your image size
    private int retryButtonHeight = 50;  // Adjust based on your image size

    private int exitButtonX = 200;
    private int exitButtonY = 150;
    private int exitButtonWidth = 50;  // Adjust based on your image size
    private int exitButtonHeight = 50;  // Adjust based on your image size

    // Update your game over screen
    public void update() {
        if (this.mouseListener != null) {
            this.mouseListener.getMouseX();
        }
        int mouseX = (int) mouseListener.getMouseX();
        int mouseY = (int) mouseListener.getMouseY();
        boolean isMousePressed = mouseListener.isPressed();

        // Check if the retry button is pressed
        if (isMousePressed && mouseX >= retryButtonX && mouseX <= retryButtonX + retryButtonWidth && mouseY >= retryButtonY && mouseY <= retryButtonY + retryButtonHeight) {
            Window.getWindow().changeState(1);  // Restart the game
        }

        // Check if the exit button is pressed
        if (isMousePressed && mouseX >= exitButtonX && mouseX <= exitButtonX + exitButtonWidth && mouseY >= exitButtonY && mouseY <= exitButtonY + exitButtonHeight) {
            Window.getWindow().close();  // Exit the game
        }
    }

    @Override
    public void update(double dt) {
        if (mouseListener.isPressed()) {
            // retry
            if (mouseListener.getX() >= retryButtonX
                    && mouseListener.getX() <= retryButtonX + retryButtonWidth
                    && mouseListener.getY() >= retryButtonY
                    && mouseListener.getY() <= retryButtonY + retryButtonHeight) {

                Window.getWindow().changeState(1);  // restart
            }
            // exit
            else if (mouseListener.getX() >= exitButtonX
                    && mouseListener.getX() <= exitButtonX + exitButtonWidth
                    && mouseListener.getY() >= exitButtonY
                    && mouseListener.getY() <= exitButtonY + exitButtonHeight) {

                Window.getWindow().close();  // exit
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(new Color(95, 134, 59));
        g.fillRect(0, 0 , Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString("You lost! :( I'm sorry", 100, 100);

        // Draw the retry and exit buttons
        if (retry != null) {
            g2d.drawImage(retry, retryButtonX, retryButtonY, retryButtonWidth, retryButtonHeight, null);
        }

        if (exit != null) {
            g2d.drawImage(exit, exitButtonX, exitButtonY, exitButtonWidth, exitButtonHeight, null);
        }
    }
}