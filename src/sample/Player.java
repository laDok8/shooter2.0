package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Player extends Circle {
    private int x,y, health;
    private String name;
    private Map map;

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
        // 10 sirka
        for (int i = x;i< x+10;i++)
            if(map.IsBorder(i,y))
                clear = false;

        if(clear)
            x += stride;
        update();
    }
    public void moveY(int stride){
        boolean clear = true;
        for (int i = y;i< y+10;i++)
            if(map.IsBorder(x,i))
                clear = false;


        if(clear)
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
