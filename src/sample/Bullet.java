package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.Vector;

public class Bullet extends Rectangle {
    static private final int BULLETWIDTH = 5,BULLETHEIGHT = 2;
    private int x,y;
    private int vectx,vecty;
    private int SPEED = 10;
    public Bullet(int vectx, int vecty,int x, int y){
        super(BULLETWIDTH,BULLETHEIGHT, Color.BLACK);
        this.x = x;
        this.y = y;
        this.vectx = vectx;
        this.vecty = vecty;
        update();
    }

    public void update(){
        x+=vectx;
        y+=vecty;
        //this.setRotate(0.8);
        setTranslateX(x);
        setTranslateY(y);
    }
}
