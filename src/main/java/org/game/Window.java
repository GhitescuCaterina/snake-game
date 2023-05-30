package org.game;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class Window extends JFrame implements Runnable {

    public static Window window = null;
    public int curentState;
    public Scene currentScene;
    public boolean isRunning = true;
    private GameOverScreen gameOverScreen;
    public KeyListener keyListener = new KeyListener();
    public MouseListener mouseListener = new MouseListener();
    private Scene currentState;
    public Window(int width ,int height, String title){
        setSize(width, height);
        setTitle(title);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );

        isRunning = true;
        changeState(0);
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        gameOverScreen = new GameOverScreen();
    }

    public static Window getWindow(){
        if(Window.window == null){
            Window.window = new Window(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, Constant.SCREEN_TITLE);
        }

        return Window.window;
    }

    public void close(){
        isRunning = false;
    }

    public void changeState(int newState){
        curentState = newState;
        switch (curentState) {
            case 0 -> currentScene = new MainMenu(keyListener, mouseListener);
            case 1 -> {
                currentScene = new Game(keyListener);
                ((Game) currentScene).start();
            }
            case 2 -> currentScene = gameOverScreen;
        }
    }

    public void changePanel(JPanel newPanel) {
        this.setContentPane(newPanel);
        this.revalidate();
    }

    public void update(double dt){
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();

        this.draw(dbg);
        getGraphics().drawImage(dbImage, 0, 0, this);

        currentScene.update(dt);
        if (curentState == 2) {
            gameOverScreen.update();
        }
    }

    public void draw(Graphics g){
        Graphics2D g2= (Graphics2D)g;
        currentScene.draw(g);

        if (curentState == 2) {
            gameOverScreen.draw(g2);
        }
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        try{
            while(isRunning){
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;

                update(deltaTime);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        this.dispose();
    }
}