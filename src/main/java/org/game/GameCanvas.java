package org.game;
import java.awt.*;
import javax.swing.JPanel;

public class GameCanvas extends JPanel {
    public static final int BODY_SIZE = 20;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Snake snake;
    private Food food;

    public GameCanvas(Snake snake, Food food) {
        this.snake = snake;
        this.food = food;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render the snake
        for (Point bodyPart : snake.getBody()) {
            int x = bodyPart.x * BODY_SIZE;
            int y = bodyPart.y * BODY_SIZE;

            g.setColor(Color.GREEN);
            g.fillRect(x, y, BODY_SIZE, BODY_SIZE);
        }

        // Render the food
        int foodX = food.getPosition().x * BODY_SIZE;
        int foodY = food.getPosition().y * BODY_SIZE;

        g.setColor(Color.RED);
        g.fillOval(foodX, foodY, BODY_SIZE, BODY_SIZE);
    }
}