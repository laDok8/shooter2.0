package sample;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.List;


// TODO: move everything to double
public class Bullet extends Rectangle {
    static private final int BULLETWIDTH = 5,BULLETHEIGHT = 2;
    public int x,y;
    private double vectx,vecty;
    private int SPEED = 10, i = 0;
    Rotate angle;
    public Bullet(double vectx, double vecty,int x, int y){
        super(BULLETWIDTH,BULLETHEIGHT, Color.BLACK);
        this.x = x;
        this.y = y;
        this.vectx = vectx*SPEED;
        this.vecty = vecty*SPEED;
        update();
        angle = new Rotate(45, 0, 0);
    }

    public void update(){
        x+=vectx;
        y+=vecty;
        setTranslateX(x);
        setTranslateY(y);
        if(angle != null && i == 0 ) {
            this.getTransforms().add(angle);
            i++;
        }
    }

    public boolean checkColision(List<Player> playerList) {
        for (var player : playerList ) {
            Bounds pl = player.getBoundsInParent();
            if(pl.intersects(this.getBoundsInParent())){
                player.hit(20);
                return true;
            }
        }

        return false;
    }
}
