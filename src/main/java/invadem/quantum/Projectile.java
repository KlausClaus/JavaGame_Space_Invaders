package invadem.quantum;

import processing.core.*;

public class Projectile implements quantum {
    private int x;
    private int y;
    private int width;
    private int height;
    private int[] velocity;
    private PImage img;
    private String imageName;

    public Projectile(PImage img, int x, int y, int width, int height, int[] velocity, String imageName) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        this.imageName = imageName;
    }


    public void tick() {
        this.x += velocity[0];
        this.y += velocity[1];
    }
    public void draw(PApplet app) {
        app.image(img, x, y, width, height);
        tick();
    }
    public void BeenCrash(){}
    public void minusX(){}
    public void plusX(){}
    public int getX(){return this.x;}
    public void minusY(){}
    public void plusY(){}
    public void setVelocity(int[] a){
        this.velocity = a;
    }
    public void setImage(PImage image){}
    public int getY(){return this.y;}
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
        return 0;
    }
    public void setImageName(String imageName){};
    public String getImageName(){return this.imageName;}
    public int getVelocity(){return this.velocity[1];}
}

