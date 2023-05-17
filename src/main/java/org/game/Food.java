package org.game;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Food {
    public Rect background;
    public Snake snake;
    public int width, height;
    public Color color;
    public Rect rect;
    public int xPadding;
    public boolean isSpawned = false;
    private List<BufferedImage> foodImages;
    private BufferedImage currentImage;
    private Random random;

    public Food(Rect background, Snake snake, int width, int height, Color color){
        this.background = background;
        this.snake= snake;
        this.width= width;
        this.height=height;
        this.color=color;
        this.rect= new Rect(0,0,width,height, snake.direction);
        xPadding = (int)((Constant.TILE_WIDTH - this.width) / 2.0);

        foodImages = new ArrayList<>();
        random = new Random();

        try{
            foodImages.add(ImageIO.read(new File("pictures/apple.png")));
            foodImages.add(ImageIO.read(new File("pictures/banana.png")));
            foodImages.add(ImageIO.read(new File("pictures/banana-peel.png")));
            foodImages.add(ImageIO.read(new File("pictures/mouse.png")));
            foodImages.add(ImageIO.read(new File("pictures/rock.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentImage = foodImages.get(random.nextInt(foodImages.size()));
    }

    public void spawn(){

        do{
            double randX = (int)(Math.random() * (int)(background.width/Constant.TILE_WIDTH)) * Constant.TILE_WIDTH + background.x;
            double randY = (int)(Math.random() * (int)(background.height/Constant.TILE_WIDTH)) * Constant.TILE_WIDTH + background.y;
            this.rect.x=randX;
            this.rect.y=randY;
        } while(snake.intersectingWithRect(this.rect));

        currentImage = foodImages.get(random.nextInt(foodImages.size()));
        this.isSpawned = true;
    }

    public void update(double dt){
        if(snake.intersectingWithRect(this.rect)){
            snake.grow();
            this.rect.x= -100;
            this.rect.y= -100;
            isSpawned = false;
        }
    }

    public void draw(Graphics2D g2){
//        g2.setColor(color);
//        g2.fillRect((int)this.rect.x + xPadding,(int)this.rect.y + xPadding,width, height);
        int scaledWidth = this.width * 4; // scale width
        int scaledHeight = this.height * 4; // scale height
        g2.drawImage(currentImage, (int)this.rect.x + xPadding, (int)this.rect.y + xPadding, scaledWidth, scaledHeight, null);

    }
}