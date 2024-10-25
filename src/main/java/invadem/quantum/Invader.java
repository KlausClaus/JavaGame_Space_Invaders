package invadem.quantum;
import processing.core.PApplet;
import processing.core.*;

public class Invader implements quantum {
    private int x;
    private int y;
    private int width;
    private int height;
    private int[] velocity;
    private PImage img;
    private int HP;
    private String imageName;

    public Invader(PImage img, int x, int y, int width, int height, int[] velocity, int HP, String imageName) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        this.HP = HP;
        this.imageName = imageName;
    }


    public void tick() {

    }
    public void draw(PApplet app) {
        app.image(img, x, y, width, height);
        tick();
    }
    public void BeenCrash(){
        HP --;
    }
    public int GetCrash(){
        return HP;
    }
    public void minusX(){
        x -= velocity[0];
    }
    public void plusX(){
        x += velocity[0];
    }
    public int getX(){return this.x;}
    public void minusY(){
        y -= velocity[1];
    }
    public void plusY(){
        y += velocity[1];
    }
    public void setVelocity(int[] a){
        this.velocity = a;
    }
    public void setImage(PImage image){
        this.img = image;
    }
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
    public void setImageName(String imageName){};
    public String getImageName(){return this.imageName;}
    public int getVelocity(){return 0;}

}

