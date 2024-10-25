package invadem.quantum;

import processing.core.*;
import processing.event.*;

public class Tank implements quantum {
    private int x;
    private int y;
    private int width;
    private int height;
    private int[] velocity;
    private PImage img;
    private int count_crash = 0;

    public Tank(PImage img, int x, int y, int width, int height, int[] velocity) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;

    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    @Override
    public void tick() {
        x+= velocity[0];
    }
    public void setImage(PImage image){}

    public void draw(PApplet app) {
        app.image(img, x, y, width, height);

        tick();
    }

    public void BeenCrash(){
        count_crash+=1;
    }
    public void minusX(){
        x -= velocity[0];
    }
    public void plusX(){
        x += velocity[0];
    }
    public void minusY(){}
    public void plusY(){}
    public void setVelocity(int[] a){
        velocity = a;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public PImage getImg(){
        return this.img;
    }
    public int GetCrash(){
        return count_crash;
    }
    public void setImageName(String imageName){};
    public String getImageName(){return "";};
    public int getVelocity(){return this.velocity[1];}

}

