package com.company;
import java.awt.*;
import java.util.*;

public class Ball extends Rectangle{

    private  Random random;
    private  int xVelocity;
    private int yVelocity;
    private  int initialSpeed = 2;

    Ball(int x,int y,int width,int height){
        super(x,y,width,height);
        random = new Random();
        int randomXDirection = random.nextInt(2);
        if(randomXDirection == 0)
            randomXDirection--;
        setXDirection(randomXDirection*initialSpeed);

        int randomYDirection = random.nextInt(2);
        if(randomYDirection == 0)
            randomYDirection--;
        setYDirection(randomYDirection*initialSpeed);

    }

    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;

    }

    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;

    }

    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x,y,height,width);
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(int getyVelocity) {
        this.yVelocity = getyVelocity;
    }
}