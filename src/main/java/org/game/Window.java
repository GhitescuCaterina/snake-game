package org.game;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements Runnable {

    public static Window window = null;
    public int currentState;
    public Scene currentScene;
    public boolean isRunning = true;
    private GameOverScreen gameOverScreen;
    private Game game;
    private About about;
    private NameInputScreen nameInputScreen;
    public KeyListener keyListener = new KeyListener();
    public MouseListener mouseListener = new MouseListener();

    public Window(int width, int height, String title) {
        setSize(width, height);
        setTitle(title);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Game(keyListener);
        window = this;

        isRunning = true;
        changeState(0);
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        gameOverScreen = new GameOverScreen(mouseListener, keyListener);
        about = new About(mouseListener, keyListener);

        nameInputScreen = new NameInputScreen(game, window, mouseListener, keyListener);
    }

    public static Window getWindow() {
        if (Window.window == null) {
            Window.window = new Window(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, Constant.SCREEN_TITLE);
        }

        return Window.window;
    }

    public void close() {
        isRunning = false;
    }

    public void changeState(int newState) {
        currentState = newState;
        switch (currentState) {
            case 0 -> currentScene = new MainMenu(keyListener, mouseListener);
            case 1 -> {
                currentScene = new Game(keyListener);
                ((Game) currentScene).start();
            }
            case 2 -> currentScene = gameOverScreen;
            case 3 -> currentScene = nameInputScreen;
            case 4 -> currentScene = about;
        }
    }

    public void changePanel(JPanel newPanel) {
        this.setContentPane(newPanel);
        this.revalidate();
    }

    public void update(double dt) {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();

        this.draw(dbg);
        getGraphics().drawImage(dbImage, 0, 0, this);

        currentScene.update(dt);
        if (currentState == 2) {
            gameOverScreen.update(dt);
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        currentScene.draw(g2d);

        if (currentState == 2) {
            gameOverScreen.draw(g2d);
        }
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        try {
            while (isRunning) {
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;

                update(deltaTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.dispose();
    }
}