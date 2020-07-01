package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.util.List;



public class Player extends Circle {
    private int x,y;
    private String name;
    public Player(int width, int x, int y, String name, Color color){
        super(width,color);
        this.name = name;
        this.x = x;
        this.y = y;
        update();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void movex(int x){
        this.x += x;
        update();
    }
    public void movey(int y){
        this.y += y;
        update();
    }
    private void update(){
        setTranslateX(x);
        setTranslateY(y);
    }
}
