package org.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class Game extends Scene{
    KeyListener keyListener;
    private ScoreRepository scoreRepository;
    Rect background, foreground, scoreboard;
    private Snake snake;
    public Food food;
    private Game game;
    private FoodManager foodManager;
    private FoodType foodType;
    private int score = 0;
    private FoodKind foodKind;
    private String playerName;
    private Rect rect;
    private long startTime;
    private long endTime;
    public Game(KeyListener keyListener){
        background = new Rect(0,0,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT, Direction.UP);
        foreground = new Rect(24, 48, Constant.TILE_WIDTH*25, Constant.TILE_WIDTH*25, Direction.UP);
        scoreboard = new Rect(790, 48, Constant.TILE_WIDTH*13, Constant.TILE_WIDTH*25, Direction.UP);
        snake = new Snake(5, 48, 48+24, 24, 24, foreground, this);
        this.keyListener = keyListener;
        this.foodManager = new FoodManager(foreground, snake, (int) Constant.TILE_WIDTH, (int) Constant.TILE_WIDTH, this);
        food = new Food(foreground, snake, (int) Constant.TILE_WIDTH, (int) Constant.TILE_WIDTH, this);
        if(!food.isSpawned) food.spawn(foodType);

        scoreRepository = new ScoreRepository();
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        food = new Food(foreground, snake, (int) Constant.TILE_WIDTH, (int) Constant.TILE_WIDTH, this);
        this.foodManager = new FoodManager(foreground, snake, (int) Constant.TILE_WIDTH, (int) Constant.TILE_WIDTH, this);
    }

    public void incrementScore(Food food) {
        FoodKind foodKind = food.foodKind;
        if (foodKind == FoodKind.APPLE) {
            score += 2;
        } else if (foodKind == FoodKind.BANANA) {
            score += 5;
        } else if (foodKind == FoodKind.MOUSE) {
            score += 10;
        } else if (foodKind == FoodKind.ROCK) {
            score -= 5;
        } else if (foodKind == FoodKind.BANANA_PEEL) {
            score -= 3;
        }
    }

    public int getPlayerScore() {
        return score;
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
        if(!food.isSpawned) food.spawn(foodType);

        food.update(dt);
        snake.update(dt);

        this.foodManager.update(dt);

        if(snake.intersectingWithSelf() || snake.intersectingWithScreenBoundaries(snake.body[snake.head])){
            gameOver();
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g.setColor(new Color(70, 70, 70));
        g2.fill(new Rectangle2D.Double(background.x, background.y, background.width, background.height));

        g2.setColor(Color.white);
        g2.fill(new Rectangle2D.Double(foreground.x, foreground.y, foreground.width, foreground.height));

        g2.setColor(new Color(144, 238, 144));
        g2.fill(new Rectangle2D.Double(scoreboard.x, scoreboard.y, scoreboard.width, scoreboard.height));
        snake.draw(g2);
        food.draw(g2);

        this.foodManager.draw(g2);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Century Gothic", Font.ITALIC, 20));
        g.drawString("Score: " + score, (int) (scoreboard.x + 30), (int) (scoreboard.y + 50));
        g.drawString("Here is what each food is worth:", (int) (scoreboard.x + 30), (int) (scoreboard.y + 90));
    }

    public void gameOver() {
        this.endTime = System.currentTimeMillis();
        Window.getWindow().changeState(2);
    }

    public long timeTaken() {
        return endTime - startTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}