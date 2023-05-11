package org.game;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::startGame);
    }

    private static void startGame() {
        Snake snake = new Snake();
        Food food = new Food();

        GameCanvas gameCanvas = new GameCanvas(snake, food);

        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameCanvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start the game loop
        GameLoop gameLoop = new GameLoop(snake, food, gameCanvas);
        gameLoop.start();
    }
}