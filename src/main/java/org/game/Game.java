package org.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Game extends Scene{
    KeyListener keyListener;
    Rect background, foreground;
    Snake snake;
    public Food food;
    public Game(KeyListener keyListener){
        background = new Rect(0,0,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, Direction.UP);
        foreground = new Rect(24, 48, Constant.TILE_WIDTH*31, Constant.TILE_WIDTH*30, Direction.UP);
        snake = new Snake(5, 48, 48+24, 24, 24, foreground);
        this.keyListener = keyListener;
        food = new Food(foreground, snake, 12, 12, Color.RED);
        food.spawn();
    }
    @Override
    public void update(double dt) {
        if(keyListener.isKeyPressed(KeyEvent.VK_W)){
            snake.changeDirection(Direction.UP);
        } else if(keyListener.isKeyPressed(KeyEvent.VK_S)){
            snake.changeDirection(Direction.DOWN);
        } else if(keyListener.isKeyPressed(KeyEvent.VK_D)){
            snake.changeDirection(Direction.RIGHT);
        } else if(keyListener.isKeyPressed(KeyEvent.VK_A)){
            snake.changeDirection(Direction.LEFT);
        }
        if(!food.isSpawned) food.spawn();

        food.update(dt);
        snake.update(dt);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g.setColor(Color.black);
        g2.fill(new Rectangle2D.Double(background.x, background.y, background.width, background.height));

        g2.setColor(Color.white);
        g2.fill(new Rectangle2D.Double(foreground.x, foreground.y, foreground.width, foreground.height));
        snake.draw(g2);
        food.draw(g2);

    }
}