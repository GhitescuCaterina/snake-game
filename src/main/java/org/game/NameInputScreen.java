package org.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class NameInputScreen extends Scene {
    private Game game;
    private Window window;
    private MouseListener mouseListener;
    private KeyboardListener keyboardListener;
    private KeyListener keyListener;
    private Connection conn;

    private BufferedImage name, confirm, nameCurrentImage, confirmCurrentImage, namePressed, confirmPressed;
    private Rect confirmRect, nameRect;

    private int nameFieldX = 100;
    private int nameFieldY = 150;
    private int nameFieldWidth = 150;
    private int nameFieldHeight = 50;

    private int confirmButtonX = 100;
    private int confirmButtonY = 220;
    private int confirmButtonWidth = 150;
    private int confirmButtonHeight = 50;

    private StringBuilder playerNameBuilder = new StringBuilder();
    private String playerName = "";

    public NameInputScreen(Game game, Window window, MouseListener mouseListener, KeyListener keyListener) {
        this.game = game;
        this.window = window;
        this.mouseListener = mouseListener;
        this.keyListener = keyListener;
        this.keyboardListener = keyListener.getKeyboardListener();

        try {
            confirm = ImageIO.read(new File("pictures/confirm.png"));
            confirmPressed = ImageIO.read(new File("pictures/confirm-pressed.png"));
            name = ImageIO.read(new File("pictures/name.png"));
            namePressed = ImageIO.read(new File("pictures/name-pressed.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        nameCurrentImage = name;
        confirmCurrentImage = confirm;

        confirmRect = new Rect(320, 100, 600, 200, Direction.UP);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/snake_database", "root", "Loseyourself!2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    @Override
    public void update(double dt) {
        if (keyboardListener.hasNewKeyPress()) {
            char lastKey = keyboardListener.getLastKeyPressed();
            if (lastKey == KeyEvent.VK_ENTER) {
                submitNameToDatabase();
            } else {
                playerNameBuilder.append(lastKey);
                playerName = playerNameBuilder.toString();
            }
        }

        if(mouseListener.getX() >= confirmRect.x && mouseListener.getX() <= confirmRect.x + confirmRect.width &&
                mouseListener.getY() >= confirmRect.y && mouseListener.getY() <= confirmRect.y + confirmRect.height) {
            confirmCurrentImage = confirmPressed;
            if(mouseListener.isPressed())
                Window.getWindow().changeState(1);
        }else{
            confirmCurrentImage = confirm;
        }

        if (mouseListener.isPressed()) {
            if (mouseListener.getX() >= confirmButtonX
                    && mouseListener.getX() <= confirmButtonX + confirmButtonWidth
                    && mouseListener.getY() >= confirmButtonY
                    && mouseListener.getY() <= confirmButtonY + confirmButtonHeight) {

                submitNameToDatabase();
                window.changeState(1);
            }
        }

    }

    private void submitNameToDatabase() {
        String playerName = playerNameBuilder.toString();

        Thread dbThread = new Thread(() -> {
            try {
                String sql = "INSERT INTO player_scores (name, score, time_taken) VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, playerName);
                statement.setInt(2, game.getPlayerScore());
                statement.setLong(3, game.timeTaken()); // Updated method name
                statement.executeUpdate();
                statement.close();

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Name saved successfully!");
                });
            } catch (SQLException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Error saving name!");
                });
            }
        });

        dbThread.start();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(new Color(146, 151, 196));
        g.fillRect(0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        g2d.setFont(new Font("Century Gothic", Font.BOLD, 40));
        g2d.drawString("Enter your name:", 100, 100);

        g2d.drawString(playerName, nameFieldX, nameFieldY + nameFieldHeight / 2);

        // Draw the name field and confirm button
        if (name != null) {
            g2d.drawImage(name, nameFieldX, nameFieldY, nameFieldWidth, nameFieldHeight, null);
        }

        if (confirm != null) {
            g2d.drawImage(confirm, confirmButtonX, confirmButtonY, confirmButtonWidth, confirmButtonHeight, null);
        }
    }
}