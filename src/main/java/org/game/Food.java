package org.game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Food {
    private static final int SIZE = 20;

    private Point position;
    private Random random;

    public Food() {
        position = new Point();
        random = new Random();
    }

    public Point getPosition() {
        return position;
    }

    public void generateRandomPosition(int boardWidth, int boardHeight) {
        int x = random.nextInt(boardWidth);
        int y = random.nextInt(boardHeight);
        position.setLocation(x, y);
    }

    public void render(Graphics g) {
        int x = position.x * SIZE;
        int y = position.y * SIZE;

        g.setColor(Color.RED);
        g.fillOval(x, y, SIZE, SIZE);
    }
}