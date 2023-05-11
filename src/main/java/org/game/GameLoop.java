package org.game;

import java.awt.*;

public class GameLoop extends Thread {
    private static final int FPS = 10;
    private static final long FRAME_TIME = 1000 / FPS;

    private Snake snake;
    private Food food;
    private GameCanvas gameCanvas;

    private boolean running;

    public GameLoop(Snake snake, Food food, GameCanvas gameCanvas) {
        this.snake = snake;
        this.food = food;
        this.gameCanvas = gameCanvas;
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            long startTime = System.currentTimeMillis();

            updateGame();
            renderGame();

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = FRAME_TIME - elapsedTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopGame() {
        running = false;
    }

    private void updateGame() {
        snake.move();

        if (checkCollision()) {
            // Handle collision
            stopGame();
        }

        if (checkFoodCollision()) {
            // Handle food collision
            snake.grow();
            food.generateRandomPosition(gameCanvas.getWidth() / GameCanvas.BODY_SIZE,
                    gameCanvas.getHeight() / GameCanvas.BODY_SIZE);
        }
    }

    private void renderGame() {
        gameCanvas.repaint();
    }

    private boolean checkCollision() {
        // Check collision with the boundaries of the game board
        int headX = snake.getBody().get(0).x;
        int headY = snake.getBody().get(0).y;
        int boardWidth = gameCanvas.getWidth() / GameCanvas.BODY_SIZE;
        int boardHeight = gameCanvas.getHeight() / GameCanvas.BODY_SIZE;

        return headX < 0 || headX >= boardWidth || headY < 0 || headY >= boardHeight;
    }

    private boolean checkFoodCollision() {
        Point foodPosition = food.getPosition();
        Point headPosition = snake.getBody().get(0);

        return foodPosition.equals(headPosition);
    }
}