package sample;

import java.awt.*;


public class Player extends GameObject {
    private int health;
    private String name;
    private int CIRCLEWIDTH = 32;
    private Controller controller;

    public Player(int x, int y, String name, Color color, ID id, Controller controller){
        super(x,y,ID.Player,color);
        this.name = name;
        health = 100;
        this.id = id;
        this.controller = controller;
    }

    @Override
    public void tick() {
            x+= vecX;
            y+= vecY;

            collision();

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

    private void collision() {
        for (var obj: controller.object) {
            //skipnu sebe
            if(obj == this)
                continue;
            if(getBounds().intersects(obj.getBounds())){
                x-=vecX;
                y-=vecY;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y,CIRCLEWIDTH,CIRCLEWIDTH);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,CIRCLEWIDTH,CIRCLEWIDTH);
    }
}
