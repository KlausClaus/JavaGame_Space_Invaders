package invadem;

import java.util.List;
import java.util.ArrayList;

import processing.core.PApplet;
import invadem.quantum.quantum;
import invadem.quantum.Invader;
import invadem.quantum.Projectile;
import invadem.quantum.Barrier;
import invadem.quantum.Tank;
import invadem.quantum.ScreenPrint;
import processing.core.PFont;
import processing.core.PImage;
import processing.event.KeyEvent;
import java.util.*;

public class App extends PApplet {

    private List<quantum> barrier;
    private List<quantum> invader;
    private List<quantum> projectile;
    private List<quantum> Tank;
    private quantum[] ScreenPrint;
    private int count_bullet = 0;
    private int count_down = 0;
    private int[] keycount;
    private Random random = new Random();
    private List<quantum> removeInvader = new ArrayList<quantum>();
    private List<quantum> removeProjectile = new ArrayList<quantum>();
    private List<quantum> removeBarrier = new ArrayList<quantum>();
    private int RandomNumber;
    private boolean GameOver = false;
    private boolean NextLevel = false;
    private int countDelay = 0;
    private int level = 0;
    private int HighScore = 10000;
    private int CurrentScore = 0;
    private PFont myFont;


    public App(){}

    public void settings() {
        size(640, 480);
    }

    public void setup() {
        frameRate(60);
        myFont = createFont("Times-Roman",32);
        textFont(myFont);

        barrier = new ArrayList<>();
        invader = new ArrayList<>();
        projectile = new ArrayList<>();
        Tank = new ArrayList<>();
        ScreenPrint = new quantum[4];
        keycount = new int[3];
        ///////////////////////////////////
        //////////Set up materials/////////
        ///////////////////////////////////
        for(int i = 0; i < 3; i ++){
            barrier.add(new Barrier(loadImage("barrier_left1.png"), 210 + (i*105), 410, 10, 10, new int[]{0, 0},"barrier_left1.png"));
            barrier.add(new Barrier(loadImage("barrier_top1.png"), 220+(i*105), 410, 10, 10, new int[]{0, 0},"barrier_top1.png"));
            barrier.add(new Barrier(loadImage("barrier_right1.png"), 230+(i*105), 410, 10, 10, new int[]{0, 0},"barrier_right1.png"));
            barrier.add(new Barrier(loadImage("barrier_solid1.png"), 210+(i*105), 420, 10, 10, new int[]{0, 0},"barrier_solid1.png"));
            barrier.add(new Barrier(loadImage("barrier_solid1.png"), 210+(i*105), 430, 10, 10, new int[]{0, 0},"barrier_solid1.png"));
            barrier.add(new Barrier(loadImage("barrier_solid1.png"), 230+(i*105), 420, 10, 10, new int[]{0, 0},"barrier_solid1.png"));
            barrier.add(new Barrier(loadImage("barrier_solid1.png"), 230+(i*105), 430, 10, 10, new int[]{0, 0},"barrier_solid1.png"));
        }

        Tank.add(new Tank(loadImage("tank1.png"), 320, 460, 22, 14, new int[]{0, 0}));
        for(int i=0; i<40; i ++){
            if(i/10 == 0){
                invader.add(new Invader(loadImage("invader_armoured1.png"), 180+i*30, 100, 16, 16, new int[]{1, 1},3,"invader_armoured"));
            }
            if(i/10 == 1){
                invader.add(new Invader(loadImage("invader_power2.png"), 180+(i%10)*30, 132, 16, 16, new int[]{1, 1},1,"invader_power"));
            }
            if(i/10 == 2){
                invader.add(new Invader(loadImage("invader1.png"), 180+(i%20)*30, 164, 16, 16, new int[]{1, 1},1,"invader"));
            }
            if(i/10 == 3) {
                invader.add(new Invader(loadImage("invader1.png"), 180+(i%30)*30, 196, 16, 16, new int[]{1, 1},1,"invader"));
            }
        }

    }

    public void draw() {
        RandomNumber = random.nextInt(invader.size());
        background(0);
        textSize(19);
        ///////////////////////////////////
        //////Draw the Font on Screen//////
        ///////////////////////////////////
        text("Highest Score: "+ HighScore, 440, 30);
        text("Current Score: "+ CurrentScore, 20, 30);
        text("Press Shift to randomly shot", 190, 56);
        text("Press delete to stop time ", 205, 80);
        text("Health Point: " + (3 - Tank.get(0).GetCrash()), 238, 30);
        /////////////////////////////////////////////////////////
        //////////Every time when it get to the next level///////
        //////////// shoot speed changes/////////////////////////
        /////////////////////////////////////////////////////////
        if(level < 5){
            count_bullet = count_bullet % (300-level*60);
        }else{
            count_bullet = count_bullet%60;
        }
        ///////////////////////////////////
        //////////Shoot function///////////
        ///////////////////////////////////
        if(count_bullet==0){
            if(invader.get(RandomNumber).getImageName().equals("invader_power")){
                int x_axis = invader.get(RandomNumber).getX()+8;
                int y_axis = invader.get(RandomNumber).getY()+16;
                projectile.add(new Projectile(loadImage("projectile_lg.png"), x_axis, y_axis, 2, 5, new int[]{0, 1},"big"));
            }else {
                int x_axis = invader.get(RandomNumber).getX() + 8;
                int y_axis = invader.get(RandomNumber).getY() + 16;
                projectile.add(new Projectile(loadImage("projectile.png"), x_axis, y_axis, 1, 3, new int[]{0, 1}, "normal"));

            }
        }
        /////////////////////////////////////////
        //////////When the invader too close/////
        //////////////Game fail//////////////////
        /////////////////////////////////////////
        count_bullet ++;
        for(quantum s: invader){
            if(s.getY()>=384){
                GameOver = true;
            }
        }
        /////////////////////////////////////////
        //////////When the Barrier been crash////
        ////////////it will change image/////////
        /////////////////////////////////////////
        for(quantum b : projectile) {
            for (quantum a : barrier) {
                if (check_collision(a, b)){
                    if(b.getImageName().equals("big")){
                        removeProjectile.add(b);
                        removeBarrier.add(a);
                    }else{
                        a.BeenCrash();
                        removeProjectile.add(b);
                        if(a.GetCrash()==1){
                            if(a.getImageName().equals("barrier_left1.png")){
                                a.setImage(loadImage("barrier_left2.png"));
                                a.setImageName("barrier_left2.png");
                            }
                            if(a.getImageName().equals("barrier_right1.png")){
                                a.setImage(loadImage("barrier_right2.png"));
                                a.setImageName("barrier_right2.png");
                            }
                            if(a.getImageName().equals("barrier_top1.png")){
                                a.setImage(loadImage("barrier_top2.png"));
                                a.setImageName("barrier_top2.png");
                            }
                            if(a.getImageName().equals("barrier_solid1.png")){
                                a.setImage(loadImage("barrier_solid2.png"));
                                a.setImageName("barrier_solid2.png");
                            }
                        }else if(a.GetCrash() == 2){
                            if(a.getImageName().equals("barrier_left2.png")){
                                a.setImage(loadImage("barrier_left3.png"));
                                a.setImageName("barrier_left3.png");
                            }
                            if(a.getImageName().equals("barrier_right2.png")){
                                a.setImage(loadImage("barrier_right3.png"));
                                a.setImageName("barrier_right3.png");
                            }
                            if(a.getImageName().equals("barrier_top2.png")){
                                a.setImage(loadImage("barrier_top3.png"));
                                a.setImageName("barrier_top3.png");
                            }
                            if(a.getImageName().equals("barrier_solid2.png")) {
                                a.setImage(loadImage("barrier_solid3.png"));
                                a.setImageName("barrier_solid3.png");
                            }
                        }else if(a.GetCrash() == 3){
                            removeBarrier.add(a);
                        }
                    }
                }
            }
        }
        /////////////////////////////////////////
        //////////Draw barrier///////////////////
        ////////////Draw Tank////////////////////
        /////////////////////////////////////////
        for(quantum s : barrier) {
            s.draw(this);
        }
        Tank.get(0).draw(this);
        /////////////////////////////////////////
        ////////When the invader go downward/////
        //////////////Image changes//////////////
        /////////////////////////////////////////
        count_down = count_down % 152;
        if(count_down%2 == 0){

            if(count_down>=0 && count_down <60){
                for(quantum a : invader) {
                    a.plusX();
                    a.setImage(loadImage(a.getImageName()+1+".png"));
                }
            }
            if(count_down>=60 && count_down <76){
                for(quantum a : invader) {
                    a.plusY();
                    a.setImage(loadImage(a.getImageName()+2+".png"));
                }
            }
            if(count_down>=76 && count_down <136){
                for(quantum a : invader) {
                    a.minusX();
                    a.setImage(loadImage(a.getImageName()+1+".png"));
                }
            }
            if(count_down>=136){
                for(quantum a : invader) {
                    a.plusY();
                    a.setImage(loadImage(a.getImageName()+2+".png"));
                }
            }

        }
        /////////////////////////////////////////
        //////////When press shift///////////////
        //////////////Randomly Shoot/////////////
        /////////////////////////////////////////
        if(ScreenPrint[2]!=null){
            int a = RandomNumber -1;
            if(countDelay <20){
                ScreenPrint[2].draw(this);
                countDelay += 1;
            }
            if(countDelay == 20){
                countDelay = 0;
                if(a == -1) {
                    a = RandomNumber + 1;
                }
                if(invader.size()==1){
                    a = 0;
                }
                if (invader.get(a).getImageName().equals("invader")){
                    CurrentScore += 100;
                }else {
                    CurrentScore += 250;
                }
                invader.remove(a);
                ScreenPrint[2]=null;
                }
        }
        /////////////////////////////////////////
        //////////When press delete//////////////
        ////////////Time will stop///////////////
        /////////////////////////////////////////
        if(ScreenPrint[3]!=null){
            if(countDelay <120){
                ScreenPrint[3].draw(this);
                for(quantum p: invader){
                    p.setVelocity(new int[]{0,0});
                }
                for(quantum p: projectile){
                    if(p.getImageName().equals("big") || p.getImageName().equals("normal")){
                        p.setVelocity(new int[]{0,0});
                    }
                }
                countDelay += 1;
            }
            if(countDelay == 120){
                for(quantum p: invader){
                    p.setVelocity(new int[]{1,1});
                }
                for(quantum p: projectile){
                    if(p.getImageName().equals("big") || p.getImageName().equals("normal")){
                        p.setVelocity(new int[]{0,1});
                    }
                }
                countDelay = 0;
                ScreenPrint[3]=null;
            }
        }
        /////////////////////////////////////////
        //////////When the Tank be crashed///////
        ////////////It lose health point/////////
        /////////////////////////////////////////
        count_down += 1;
        for(quantum a: projectile){
            for(quantum b: Tank){
                if(check_collision(b,a)){
                    if(a.getVelocity()==1){
                        if(a.getImageName().equals("big")){
                            removeProjectile.add(a);
                            GameOver = true;
                        }else{
                            removeProjectile.add(a);
                            b.BeenCrash();
                        }
                    }
                }
            }
        }
        /////////////////////////////////////////
        //////////When the invader be crashed////
        ////////////It will be destroyed/////////
        /////////////////////////////////////////
        for(quantum a : invader) {
            if(projectile.size() >= 1){
                for(quantum b : projectile){
                    if(check_collision(b, a)){
                        if(b.getVelocity()==-1){
                            a.BeenCrash();
                            if(a.GetCrash()==0){
                                removeInvader.add(a);
                                if(a.getImageName().equals("invader")){
                                    CurrentScore += 100;
                                }else{
                                    CurrentScore += 250;
                                }
                            }
                            removeProjectile.add(b);
                        }

                    }
                }
            }
            a.draw(this);
        }
        /////////////////////////////////////////
        //////////Draw every stuff///////////////
        /////////////////////////////////////////
        for(quantum s: projectile){
            s.draw(this);
        }
        for(quantum d:removeInvader){
            invader.remove(d);
        }
        for(quantum e:removeProjectile){
            projectile.remove(e);
        }
        for(quantum f: removeBarrier){
            barrier.remove(f);
        }
        if(invader.size() == 0){
            NextLevel = true;
        }
        if(Tank.get(0).GetCrash() == 3){
            GameOver = true;
        }
        /////////////////////////////////////////
        //////////Handle case when gameover//////
        ///////////Or get to the next level//////
        /////////////////////////////////////////
        if(GameOver || NextLevel){
            if(GameOver){
                level = 0;
                if(CurrentScore>HighScore){
                    HighScore = CurrentScore;
                }
                CurrentScore = 0;
                this.clear();
                if(countDelay <120){
                    ScreenPrint[0] = (new ScreenPrint(loadImage("gameover.png"), 210, 210, 220, 40, new int[]{0,0}, "nextlevel"));
                    ScreenPrint[0].draw(this);
                    countDelay += 1;
                }
                if(countDelay == 120){
                    GameOver = false;
                    countDelay = 0;
                }

            }else if(NextLevel){
                this.clear();
                if(countDelay <120){
                    ScreenPrint[1] = (new ScreenPrint(loadImage("nextlevel.png"), 210, 210, 220, 40, new int[]{0,0}, "gameover"));
                    ScreenPrint[1].draw(this);
                    countDelay += 1;
                }
                if(countDelay == 120){
                    NextLevel = false;
                    countDelay = 0;
		    level ++;
                }

            }
            setup();
        }

    }
    @Override
    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);
        int keyCode = event.getKeyCode();
        // when the space button been pressed, tank will shoot
        if (keyCode == 32) {
            if (keycount[0] == 1) {
                if (Tank.get(0).getX() >= 180) {
                    Tank.get(0).setVelocity(new int[]{-1, 0});
                } else {
                    Tank.get(0).setVelocity(new int[]{0, 0});
                }
            }
            if (keycount[1] == 1) {
                if (Tank.get(0).getX() <= 460) {
                    Tank.get(0).setVelocity(new int[]{1, 0});
                } else {
                    Tank.get(0).setVelocity(new int[]{0, 0});
                }
            }
            projectile.add(new Projectile(loadImage("projectile.png"), Tank.get(0).getX() + 11, Tank.get(0).getY(), 1, 3, new int[]{0, -1},"Tank"));
        }
        /////////////////////////////////////////
        ///////The tank will go left or right////
        /////////////////////////////////////////
        if (keyCode == LEFT) {
            if (Tank.get(0).getX() >= 180) {
                keycount[0] = 1;
                Tank.get(0).setVelocity(new int[]{-1, 0});
            } else {
                Tank.get(0).setVelocity(new int[]{0, 0});
            }
        }else if (keyCode == RIGHT) {
            if (Tank.get(0).getX() <= 460) {
                keycount[1] = 1;
                Tank.get(0).setVelocity(new int[]{1, 0});
            } else {
                Tank.get(0).setVelocity(new int[]{0, 0});
            }
        }
        //////////////////////////////////////////////////////////////////////////////
        // when press BackSpace, everything will stop, except tank and it's projectile
        //////////////////////////////////////////////////////////////////////////////
        if (keyCode == 8) {
            ScreenPrint[3] = (new ScreenPrint(loadImage("clean.png"), 100, 80, 450, 280, new int[]{0, -1}, "clean"));
            //removeInvader.addAll(invader);
        }
        //when press shift ,random kills one invader
        if (keyCode == 16) {
            ScreenPrint[2] = (new ScreenPrint(loadImage("Skill.png"), 150, 40, 380, 400, new int[]{0, -1}, "Skill"));
        }
        //when press control, give tank 3 crash.
        if (keyCode ==17){
            Tank.get(0).BeenCrash();
            Tank.get(0).BeenCrash();
            Tank.get(0).BeenCrash();
        }
        //get close: when press Tab, invaders get closer
        if(keyCode == 9){
            for(quantum s: invader){
                for(int i = 0; i < 170; i ++){
                    s.plusY();
                }
            }
        }
    }

    @Override
    //////////////////////////////////////////////////////////////////////////////
    // when left and right button been released, tank will stop
    //////////////////////////////////////////////////////////////////////////////
    public void keyReleased(KeyEvent event) {
        super.keyPressed(event);
        int keyCode = event.getKeyCode();
        if(keyCode==LEFT){
            keycount[0]=0;
            Tank.get(0).setVelocity(new int[]{0,0});
        }
        else if(keyCode==RIGHT){
            keycount[1]=0;
            Tank.get(0).setVelocity(new int[]{0,0});
        }

    }
    /////////////////////////
    // Check Collision method
    /////////////////////////
    public boolean check_collision(quantum a, quantum b){
        if((a.getX() < (b.getX() + b.getWidth())) && (a.getX() + a.getWidth() > b.getX()) && (a.getY() < (b.getY() + b.getHeight()) ) && ((a.getHeight() + a.getY()) > b.getY())){
            return true;
        }else{
            return false;
        }
    }


    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }

}
