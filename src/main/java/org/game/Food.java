package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Food {
    public Rect background;
    private Snake snake;
    public int width, height;
    public Rect rect;
    public int xPadding;
    public boolean isSpawned = false;
    public FoodType foodType;
    private List<BufferedImage> goodFood;
    private List<BufferedImage> badFood;
    private BufferedImage currentImage;
    private Random random;
    private Game game;
    BufferedImage apple, banana, banana_peel, rock, mouse;
    private Map<FoodKind, BufferedImage> foodImages = new HashMap<>();
    FoodKind foodKind;

    public Food(Rect background, Snake snake, int width, int height, Game game) {
        this.background = background;
        this.snake = snake;
        this.width = width;
        this.height = height;
        this.game = game;
        this.rect = new Rect(0, 0, width, height, snake.direction);
        goodFood = new ArrayList<>();
        badFood = new ArrayList<>();

        random = new Random();

        xPadding = (int) ((Constant.TILE_WIDTH - this.width) / 2.0);

        try{
            apple = ImageIO.read(new File("pictures/apple.png"));
            banana = ImageIO.read(new File("pictures/banana.png"));
            banana_peel = ImageIO.read(new File("pictures/banana-peel.png"));
            rock = ImageIO.read(new File("pictures/rock.png"));
            mouse = ImageIO.read(new File("pictures/mouse.png"));

            foodImages.put(FoodKind.APPLE, apple);
            foodImages.put(FoodKind.BANANA, banana);
            foodImages.put(FoodKind.MOUSE, mouse);
            foodImages.put(FoodKind.ROCK, rock);
            foodImages.put(FoodKind.BANANA_PEEL, banana_peel);

            goodFood.add(apple);
            goodFood.add(banana);
            badFood.add(banana_peel);
            badFood.add(rock);
            goodFood.add(mouse);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void spawn(FoodType foodType) {
        this.foodType = foodType;
        do {
            double randX = (int) (Math.random() * (int) (background.width / Constant.TILE_WIDTH)) * Constant.TILE_WIDTH + background.x;
            double randY = (int) (Math.random() * (int) (background.height / Constant.TILE_WIDTH)) * Constant.TILE_WIDTH + background.y;
            this.rect.x = randX;
            this.rect.y = randY;
        } while (snake.intersectingWithRect(this.rect));

        this.foodType = random.nextInt(100) < 69 ? FoodType.GOOD : FoodType.BAD;

        if(this.foodType == FoodType.GOOD) {
            int index = random.nextInt(goodFood.size());
            currentImage = goodFood.get(index);
            foodKind = FoodKind.values()[index];
        } else {
            int index = random.nextInt(badFood.size());
            currentImage = badFood.get(index);
            foodKind = FoodKind.values()[index + 3];
        }

        this.isSpawned = true;
    }

    public void update(double dt) {
        if (snake.intersectingWithRect(this.rect)) {
            if(foodType == FoodType.GOOD){
                game.incrementScore(this);
                snake.grow();
                snake.resetBadFoodEatenCount();
            } else if(foodType == FoodType.BAD){
                snake.shrink();
                snake.addBadFoodEaten();
                game.incrementScore(this);
            }
            this.isSpawned = false;
        }
    }

    public void despawn() {
        this.isSpawned = false;
    }

    public void draw(Graphics2D g2) {
        if(isSpawned) {
            g2.drawImage(currentImage, (int) this.rect.x + xPadding, (int) this.rect.y + xPadding, width, height, null);
        }
    }
}
