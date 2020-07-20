package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller {
    //logika
    protected CopyOnWriteArrayList<GameObject> object = new CopyOnWriteArrayList<GameObject>();
    protected ArrayList<String> odchozi = new ArrayList<String>();
    private Network network;

    private boolean up = false,down = false,left = false,right = false;

    public void tick(){
        /*for (int i=0;i<object.size();i++)
            object.get(i).tick();*/
        for (var obj : object) {
            obj.tick();
            if(obj.id != ID.Block && !odchozi.contains(obj.toString())) {

                odchozi.add(obj.toString());
            }
        }
    }
    public void render(Graphics g){
        /*for (int i=0;i<object.size();i++)
            object.get(i).render(g);*/
        for (var obj : object) {
            obj.render(g);
        }
        if(odchozi.size()>0) {
            ArrayList<String> x = network.update(odchozi);
            odchozi.clear();
            if (x == null || x.size() == 0)
                return;
            //string z listu
            for (var str : x) {
                if( str == null)
                    return;
                int mx = Integer.parseInt(str.substring(13,17));
                int my = Integer.parseInt(str.substring(21,25));
                if(str.contains("Player")){
                    new Player(mx,my,"adolf",Color.red, ID.Player,null).render(g);
                }
                else{
                    new Bullet(mx,my, ID.Bullet,this,Color.black,1,1).render(g);
                }
            }
            //network.client.list.clear();
            //odchozi.clear();
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

    public void attachConnection(Network network){
        this.network = network;
    }

}
