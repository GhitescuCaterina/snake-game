package org.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Snake {
    public Rect[] body = new Rect[100];
    public double bodyWidth, bodyHeight;
    MouseListener mouseListener;
    public int size;
    public int tail = 0;
    public int head = 0;

    public Direction direction = Direction.RIGHT;
    public double ogWaitBetweenUpdates = 0.075f;
    public double waitTimeLeft = ogWaitBetweenUpdates;
    public Rect background;
    //Rect newBodyPiece = new Rect(newX, newY, bodyWidth, bodyHeight, direction);
    BufferedImage headImage, bodyImage, tailImage, archedBodyImage;
    private Game game;
    private int badFoodsEatenInARow;

    public Snake(int size, double startX, double startY, double bodyWidth, double bodyHeight, Rect background, Game game) {
        this.size = size;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
        this.background = background;
        this.game = game;

        for (int i = 0; i <= size; i++) {
            Rect bodyPiece = new Rect(startX + i * bodyWidth, startY, bodyWidth, bodyHeight, direction);
            body[i] = bodyPiece;
            head++;
        }
        head--;

        try {
            headImage = ImageIO.read(new File("pictures/snake-head.png"));
            bodyImage = ImageIO.read(new File("pictures/snake-body.png"));
            tailImage = ImageIO.read(new File("pictures/snake-tail.png"));
            archedBodyImage = ImageIO.read(new File("pictures/arch.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDirection(Direction newDirection) {
        if (newDirection == Direction.RIGHT && direction != Direction.LEFT) {
            body[head].turn = Turn.RIGHT;
            direction = newDirection;
        } else if (newDirection == Direction.LEFT && direction != Direction.RIGHT) {
            body[head].turn = Turn.LEFT;
            direction = newDirection;
        } else if (newDirection == Direction.UP && direction != Direction.DOWN) {
            body[head].turn = Turn.UP;
            direction = newDirection;
        } else if (newDirection == Direction.DOWN && direction != Direction.UP) {
            body[head].turn = Turn.DOWN;
            direction = newDirection;
        }
    }

    public void update(double dt){

        body[head].direction = direction;

        if(waitTimeLeft > 0){
            waitTimeLeft -= dt;
            return;
        }

        if(intersectingWithSelf()){
            Window.getWindow().changeState(0);
        }

        waitTimeLeft= ogWaitBetweenUpdates;
        double newX= 0;
        double newY = 0;

        if(direction == Direction.RIGHT){
            newX = body[head].x + bodyWidth;
            newY = body[head].y;
        }else if (direction == Direction.LEFT){
            newX = body[head].x - bodyWidth;
            newY = body[head].y;
        }
        else if (direction == Direction.UP){
            newX = body[head].x;
            newY = body[head].y - bodyHeight;
        }
        else if (direction == Direction.DOWN){
            newX = body[head].x;
            newY = body[head].y + bodyHeight;
        }

        body[(head + 1) % body.length] = body[tail];
        body[tail] = null;
        head = (head + 1) % body.length;
        tail = (tail + 1) % body.length;

        body[head] = new Rect(newX, newY, bodyWidth, bodyHeight, direction);


    }

    public boolean intersectingWithSelf(){
        Rect headR = body[head];
        return intersectingWithRect(headR) || intersectingWithScreenBoundaries(headR);
    }

    public boolean intersectingWithRect(Rect rect) {
        for (int i = tail; i != head; i = (i + 1) % body.length) {
            if (intersecting(rect, body[i])) return true;
        }
        return false;
    }

    public boolean intersectingWithScreenBoundaries(Rect head) {
        return (head.x < background.x || (head.x + head.width) > background.x + background.width ||
                head.y < background.y || (head.y + head.height) > background.y + background.height);
    }

    public void grow(){
        double newX = 0;
        double newY = 0;

        if(direction == Direction.RIGHT){
            newX = body[tail].x - bodyWidth;
            newY = body[tail].y;
        }else if (direction == Direction.LEFT){
            newX = body[tail].x + bodyWidth;
            newY = body[tail].y;
        }
        else if (direction == Direction.UP){
            newX = body[tail].x;
            newY = body[tail].y + bodyHeight;
        }
        else if (direction == Direction.DOWN){
            newX = body[tail].x;
            newY = body[tail].y - bodyHeight;
        }

        Direction rDirection = Direction.LEFT;
        if (direction == Direction.RIGHT) rDirection=Direction.LEFT;
        else if (direction == Direction.LEFT) rDirection=Direction.RIGHT;
        else if (direction == Direction.UP) rDirection=Direction.DOWN;
        else if (direction == Direction.DOWN) rDirection=Direction.UP;

        Rect newBodyPiece = new Rect(newX, newY, bodyWidth, bodyHeight, rDirection);
        tail = ((tail - 1) + body.length) % body.length;
        body[tail] = newBodyPiece;

        newBodyPiece.turn = Turn.STRAIGHT;
        resetBadFoodEatenCount();
    }

    public boolean intersecting(Rect r1, Rect r2) {
        return r1.x < r2.x + r2.width && r1.x + r1.width > r2.x &&
                r1.y < r2.y + r2.height && r1.y + r1.height > r2.y;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private BufferedImage rotateImage(BufferedImage image, int degrees) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(degrees), image.getWidth() / 2, image.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }

    public int calculateTurnAngle(Direction currentDirection, Direction nextDirection) {
        int currentAngle = directionToDegrees(currentDirection);
        int nextAngle = directionToDegrees(nextDirection);
        int rotationAngle = nextAngle - currentAngle;
        // If rotationAngle is negative, add 360 to it to get the equivalent positive angle
        if (rotationAngle < 0) {
            rotationAngle += 360;
        }
        return rotationAngle;
    }

    private int directionToDegrees(Direction direction) {
        switch (direction) {
            case RIGHT:
                return 0;
            case DOWN:
                return 90;
            case LEFT:
                return 180;
            case UP:
                return 270;
            default:
                return 0;
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = tail; i != head; i = (i + 1) % body.length) {
            Rect piece = body[i];
            Rect nextPiece = i == head ? null : body[(i + 1) % body.length];

            if (i == tail) {
                BufferedImage resizedTailImage = resizeImage(rotateImage(tailImage, directionToDegrees(piece.direction)), (int) bodyWidth, (int) bodyHeight);
                g2.drawImage(resizedTailImage, (int) piece.x, (int) piece.y, null);
            } else if (i == (head - 1 + body.length) % body.length) {
                BufferedImage resizedHeadImage = resizeImage(rotateImage(headImage, directionToDegrees(piece.direction)), (int) bodyWidth, (int) bodyHeight);
                g2.drawImage(resizedHeadImage, (int) piece.x, (int) piece.y, null);
            } else if (nextPiece != null && piece.direction != nextPiece.direction) {  // We draw the arched body image if the snake is turning
                int turnAngle = calculateTurnAngle(piece.direction, nextPiece.direction);
                BufferedImage rotatedArchedBodyImage = rotateImage(archedBodyImage, -turnAngle);
                BufferedImage resizedArchedBodyImage = resizeImage(rotatedArchedBodyImage, (int) bodyWidth, (int) bodyHeight);

                g2.drawImage(resizedArchedBodyImage, (int) piece.x, (int) piece.y, null);
            } else {
                BufferedImage resizedBodyImage = resizeImage(rotateImage(bodyImage, directionToDegrees(piece.direction)), (int) bodyWidth, (int) bodyHeight);
                g2.drawImage(resizedBodyImage, (int) piece.x, (int) piece.y, null);
            }
        }
    }

    public void shrink() {
        if (size > 2) {
            body[tail] = null;
            tail = (tail + 1) % body.length;
            size--;
        } else {
            this.game.gameOver();
        }
        addBadFoodEaten();
    }

    public void addBadFoodEaten() {
        this.badFoodsEatenInARow++;
        if (this.badFoodsEatenInARow >= 3) {
            this.game.gameOver();
        }
    }

    public void resetBadFoodEatenCount() {
        this.badFoodsEatenInARow = 0;
    }
}
