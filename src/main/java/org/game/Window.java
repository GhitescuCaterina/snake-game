package org.game;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class Window extends JFrame implements Runnable {

    public static Window window = null;
    public int currentState;
    public Scene currentScene;
    public boolean isRunning = true;

    public KeyListener keyListener = new KeyListener();
    public MouseListener mouseListener = new MouseListener();
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
//        setOpacity(0.5f);
//        setForeground(Color.black);
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
        currentState = newState;
        switch(currentState){
            case 0:
                currentScene = new MainMenu(keyListener, mouseListener);
                break;
            case 1:
                currentScene = new Game(keyListener);
                break;
        }
    }

    public void update(double dt){
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();

        this.draw(dbg);
        getGraphics().drawImage(dbImage, 0, 0, this);

        currentScene.update(dt);
    }

    public void draw(Graphics g){
        Graphics2D g2= (Graphics2D)g;
        currentScene.draw(g);
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