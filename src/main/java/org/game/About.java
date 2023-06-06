package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;

public class About extends Scene{

    private MouseListener mouseListener;
    public KeyListener keyListener;
    private BufferedImage back, backPressed, backCurrentImage;
    public Rect backRect;

    public About(MouseListener mouseListener, KeyListener keyListener){
        this.mouseListener = mouseListener;
        this.keyListener = keyListener;
        try{
            back = ImageIO.read(new File("pictures/back.png"));
            backPressed = ImageIO.read(new File("pictures/back-pressed.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        backCurrentImage = back;
        backRect = new Rect(320, 100, 600, 200, Direction.UP);
    }
    @Override
    public void update(double dt) {



        if(mouseListener.getX() >= backRect.x && mouseListener.getX() <= backRect.x + backRect.width &&
                mouseListener.getY() >= backRect.y && mouseListener.getY() <= backRect.y + backRect.height) {
            backCurrentImage = backPressed;
            if(mouseListener.isPressed())
                Window.getWindow().changeState(0);
        }else{
            backCurrentImage = back;
        }

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(new Color(146, 151, 196));
        g.fillRect(0, 0 , Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);

        backRect = new Rect((Constant.SCREEN_WIDTH - backRect.width) / 2, (Constant.SCREEN_HEIGHT - backRect.height)/2 + 150, 324, 120, Direction.UP);
        g.drawImage(backCurrentImage, (int)backRect.x, (int)backRect.y, (int)backRect.width, (int)backRect.height, null);

    }
}
