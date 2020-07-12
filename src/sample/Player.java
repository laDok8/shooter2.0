package sample;

import java.awt.*;


public class Player extends GameObject {
    private int health;
    private String name;
    private Map map;
    private int CIRCLEWIDTH = 10;
    private Controller controller;

    public Player(int width, int x, int y, String name, Color color, Map map, ID id, Controller controller){
        super(x,y,ID.Player,color);
        this.map = map;
        this.name = name;
        health = 100;
        this.id = id;
        this.controller = controller;
    }

    @Override
    public void tick() {
        //if(map.IsBorder((int)(x+vecX),(int)(y+vecY))){
            x+= vecX;
            y+= vecY;
        //}

        //move
        if(controller.isUp()) vecY = -5;
        else if(!controller.isDown()) vecY = 0;

        if(controller.isDown()) vecY = 5;
        else if(!controller.isUp()) vecY = 0;

        if(controller.isLeft()) vecX = -5;
        else if(!controller.isRight()) vecX = 0;

        if(controller.isRight()) vecX = 5;
        else if(!controller.isLeft()) vecX = 0;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y,CIRCLEWIDTH,CIRCLEWIDTH);

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
