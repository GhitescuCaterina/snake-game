package org.game;

public class Rect {

    public double x,y, width, height;
    public Direction direction;
    public Turn turn;

    public Rect(double x, double y, double width, double height, Direction direction){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.direction= direction;
    }
}