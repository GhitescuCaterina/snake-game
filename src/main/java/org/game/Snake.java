package org.game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final int INITIAL_LENGTH = 3;
    private static final int BODY_SIZE = 20;

    private List<Point> body;
    private Direction direction;

    public Snake() {
        body = new ArrayList<>();
        direction = Direction.RIGHT;

        // Initialize the snake with an initial length and position
        for (int i = 0; i < INITIAL_LENGTH; i++) {
            body.add(new Point(i, 0));
        }
    }

    public List<Point> getBody() {
        return body;
    }

    public void move() {
        Point head = body.get(0);
        int x = head.x;
        int y = head.y;

        // Update the head position based on the current direction
        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }

        // Create a new head with the updated position
        Point newHead = new Point(x, y);

        // Move the snake by adding the new head at the front and removing the tail
        body.add(0, newHead);
        body.remove(body.size() - 1);
    }

    public void setDirection(int keyCode) {
        Direction newDirection = Direction.fromKeyCode(keyCode);

        if (newDirection != null) {
            // Prevent the snake from reversing its direction completely
            if (direction == Direction.UP && newDirection != Direction.DOWN
                    || direction == Direction.DOWN && newDirection != Direction.UP
                    || direction == Direction.LEFT && newDirection != Direction.RIGHT
                    || direction == Direction.RIGHT && newDirection != Direction.LEFT) {
                direction = newDirection;
            }
        }
    }

    public void grow() {
        // Add a new tail segment to the snake
        Point tail = body.get(body.size() - 1);
        body.add(new Point(tail));
    }

    public void render(Graphics g) {
        for (Point segment : body) {
            int x = segment.x * BODY_SIZE;
            int y = segment.y * BODY_SIZE;

            // Draw each segment as a square on the game board
            g.setColor(Color.GREEN);
            g.fillRect(x, y, BODY_SIZE, BODY_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, BODY_SIZE, BODY_SIZE);
        }
    }
}