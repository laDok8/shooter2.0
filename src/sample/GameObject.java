package sample;

import com.sun.javafx.collections.MappingChange;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Map.*;

public abstract class GameObject {
    int x,y;
    float vecX,vecY;
    Color color;
    ID id;

    public GameObject(int x, int y, ID id,Color color){
        this.x = x;
        this.y = y;
        this.color = color;
        this.id = id;

    }
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    @Override
    public String toString() {
        return "GameObject{" +
                ", x=" + x +
                ", y=" + y +
                ", vecX=" + vecX +
                ", vecY=" + vecY +
                ", color=" + color +
                ", id=" + id +
                "}";
    }

    public static @NotNull GameObject toObject(String str){

        Scanner car = new Scanner(str);
        car.useDelimiter(",");
        //1. skip
        car.next();

        int mx = 0,my = 0;
        float mvecx=0,mvecy=0;
        //Color mcolor;
        String mname = "";

        while(car.hasNext()){

            Scanner rov = new Scanner(car.next());
            rov.useDelimiter("=");
            while (rov.hasNext()){
                String left = rov.next();
                left = left.trim();

                //konec
                if(left.equals("}"))
                    break;

                String right = rov.next();

                switch(left){
                    case "x":mx = Integer.parseInt(right);break;
                    case "y":my = Integer.parseInt(right);break;
                    case "vecX":mvecx = Float.parseFloat(right);break;
                    case "vecY":mvecy = Float.parseFloat(right);break;
                    case "name":mname = right;break;

                }

                //pokud neco zbylo
                while (rov.hasNext()) {
                    var dummy = rov.next();
                }

            }

        }
        //TODO barva
        if(str.contains("Player")){
              return new Player(mx,my,mname,Color.red,null);
        }
        else{
             return new Bullet(mx,my,null, mvecx, mvecy);
        }
    }
}
