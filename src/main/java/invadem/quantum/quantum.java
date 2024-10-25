package invadem.quantum;
import processing.core.*;

public interface quantum {


    public void draw(PApplet app);
    public void tick();
    public void BeenCrash();
    public void minusX();
    public void plusX();
    public int getX();
    public void minusY();
    public void plusY();
    public void setVelocity(int[] a);
    public void setImage(PImage image);
    public int getY();
    public int getWidth();
    public int getHeight();
    public PImage getImg();
    public int GetCrash();
    public void setImageName(String imageName);
    public String getImageName();
    public int getVelocity();
}