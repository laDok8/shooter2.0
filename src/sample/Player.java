package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Player extends Circle {
    private int x,y, health;
    private String name;
    private Map map;
    private int CIRCLEWIDTH = 10;

    public Player(int width, int x, int y, String name, Color color, Map map){
        super(width,color);
        this.name = name;
        this.x = x;
        this.y = y;
        this.map = map;
        health = 100;
        update();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveX(int stride){
        boolean clear = true;
        int offset = stride > 0 ? CIRCLEWIDTH : 0;
        // zkontroluje CIRCLEWIDTH sousednich pixelu ve smeru pohybu
        for (int i = x;i< x+CIRCLEWIDTH;i++)
            if(map.IsBorder(x+stride+offset,y+i)) {
                clear = false;
                System.out.println(x+"x:" + y);
            }

        if (!map.IsBorder(x+stride,y))
        //if(clear)
            x += stride;
        update();
    }
    public void moveY(int stride){
        boolean clear = true;
        int offset = stride > 0 ? CIRCLEWIDTH : 0;
        // zkontroluje CIRCLEWIDTH sousednich pixelu ve smeru pohybu
        for (int i = y;i< y+CIRCLEWIDTH;i++)
            if(map.IsBorder(x+i,y+stride+offset)){
                clear = false;
                System.out.println(x +"y:" + y);}


        //if(clear)
        if(!map.IsBorder(x,y+stride))
            y += stride;
        update();
    }
    private void update(){
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
    public void hit(int dmg){
        health-=dmg;
    }
    public boolean isAlive(){
        return (health > 0);
    }

}
