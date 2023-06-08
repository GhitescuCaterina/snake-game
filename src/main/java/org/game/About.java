package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class About extends Scene{

    private MouseListener mouseListener;
    private BufferedImage back, backPressed, backCurrentImage;
    public Rect backRect;
    public KeyboardListener keyboardListener;

    public About(MouseListener mouseListener, KeyListener keyListener){
        this.mouseListener = mouseListener;
        this.keyboardListener = keyboardListener;
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

        g.setColor(new Color(30, 100, 81));
        g2d.setFont(new Font("Century Gothic", Font.ITALIC, 30));
        g2d.drawString("I tried to recreate", (Constant.SCREEN_WIDTH)/2 - 140, 220);
        g2d.drawString("one of my favorite childhood games.", (Constant.SCREEN_WIDTH)/2 - 300, 260);
        g2d.drawString("Here are the brief rules!", (Constant.SCREEN_WIDTH)/2 - 200, 300);
        g2d.drawString("The good foods are apples, bananas and mice.", (Constant.SCREEN_WIDTH)/2 - 350, 340);
        g2d.drawString("The bad foods are rocks and banana peels.", (Constant.SCREEN_WIDTH)/2 - 330, 380);
        g2d.drawString("Try and get the biggest highscore!", (Constant.SCREEN_WIDTH)/2 - 270, 420);
        g2d.drawString("Good luck!", (Constant.SCREEN_WIDTH)/2 - 100, 460);

        backRect = new Rect((Constant.SCREEN_WIDTH - backRect.width) / 2, (Constant.SCREEN_HEIGHT - backRect.height)/2 + 150, 324, 120, Direction.UP);
        g.drawImage(backCurrentImage, (int)backRect.x, (int)backRect.y, (int)backRect.width, (int)backRect.height, null);

    }
}
