package invadem.quantum;

import processing.core.PApplet;
import processing.core.PImage;

public class ScreenPrint implements quantum{
        private int x;
        private int y;
        private int width;
        private int height;
        private PImage img;
        private int count_crash = 0;
        private String imageName;

        public ScreenPrint(PImage img, int x, int y, int width, int height, int[] velocity, String imageName) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.imageName = imageName;
        }


        public void tick() {}
        public void draw(PApplet app) {
            app.image(img, x, y, width, height);
            tick();
        }

        public void BeenCrash(){
            this.count_crash+=1;
        }
        public void minusX(){}
        public void plusX(){}
        public int getX(){return this.x;}
        public void minusY(){}
        public void plusY(){}
        public void setVelocity(int[] a){}
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
        public int GetCrash(){
            return count_crash;
        }
        public void setImageName(String imageName){
            this.imageName = imageName;
        }
        public String getImageName(){
            return this.imageName;
        }
    public int getVelocity(){return 0;}

}



