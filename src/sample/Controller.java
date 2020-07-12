package sample;

import java.awt.*;
import java.util.LinkedList;

public class Controller {
    //logika
    protected LinkedList<GameObject> object = new LinkedList<GameObject>();

    private boolean up = false,down = false,left = false,right = false;

    public void tick(){
        for (var obj : object) {
            obj.tick();
        }
    }
    public void render(Graphics g){
        for (var obj : object) {
            obj.render(g);
        }
    }

    public void addObject(GameObject obj){
        object.add(obj);
    }
    public void remove(GameObject obj){
        object.remove(obj);
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
