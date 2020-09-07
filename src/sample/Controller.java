package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller {
    //logika
    protected static CopyOnWriteArrayList<GameObject> object = new CopyOnWriteArrayList<GameObject>();
    protected ArrayList<GameObject> odchozi = new ArrayList<>();
    private ArrayList<GameObject> recievedO = new ArrayList<>();

    private boolean up = false,down = false,left = false,right = false;

    public void tick(){
        for (var obj : object) {
            obj.tick();
            if(obj.id != ID.Block && !odchozi.contains(obj)) {
                odchozi.add(obj);
            }
        }

        if (recievedO == null || recievedO.size() == 0)
            return;
        for (var obj : recievedO) {
            if(obj instanceof Bullet)
                obj.tick();

        }
    }
    public void render(Graphics g){
        for (var obj : object) {
            obj.render(g);
        }

        //odesilani-prijimani dat
        if(odchozi.size()>0) {
            recievedO = Network.update(odchozi);
        }
        if (recievedO == null || recievedO.size() == 0)
            return;

        for (var str : recievedO) {
                if(str == null)
                    continue;
                str.render(g);
            }
    }

    public void addObject(GameObject obj){
        object.add(obj);
    }
    public static void remove(GameObject obj){
        try{
            object.remove(obj);
        }catch (Exception e){}
    }


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

}
