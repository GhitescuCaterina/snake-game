package org.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodManager {

    private List<Food> foods;
    private Rect foreground;
    private Snake snake;
    private int width, height;
    private Random random;
    private Game game;
    MouseListener mouseListener;

    public FoodManager(Rect foreground, Snake snake, int width, int height, Game game) {
        this.foreground = foreground;
        this.snake = snake;
        this.width = width;
        this.height = height;
        this.game = game;

        this.foods = new ArrayList<>();
        this.random = new Random();

        for (int i = 0; i < 3; i++) {
            Food food = new Food(foreground, snake, width, height, game);
            foods.add(food);
        }

        spawnFoods();
    }

    private void spawnFoods() {
        foods.get(0).spawn(FoodType.GOOD);

        for (int i = 1; i < foods.size(); i++) {
            foods.get(i).spawn(random.nextBoolean() ? FoodType.GOOD : FoodType.BAD);
        }
    }

    public void update(double dt) {
        boolean goodFoodEaten = false;

        for (Food food : foods) {
            food.update(dt);

            if (!food.isSpawned && food.foodType == FoodType.GOOD) {
                goodFoodEaten = true;
            }

            if (!food.isSpawned) {
                food.spawn(random.nextBoolean() ? FoodType.GOOD : FoodType.BAD);
            }
        }

        if (goodFoodEaten) {
            List<Food> badFoods = new ArrayList<>();
            for (Food food : foods) {
                if (food.foodType == FoodType.BAD && food.isSpawned) {
                    badFoods.add(food);
                }
            }
            if (!badFoods.isEmpty()) {
                Food randomBadFood = badFoods.get(random.nextInt(badFoods.size()));
                randomBadFood.despawn();
                randomBadFood.spawn(random.nextBoolean() ? FoodType.GOOD : FoodType.BAD);
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (Food food : foods) {
            food.draw(g2);
        }
    }
}