package sample;


import java.awt.*;


// TODO: move everything to double
public class Bullet extends GameObject {
    static private final int BULLETWIDTH = 5,BULLETHEIGHT = 2;

    public Bullet(double vectx, double vecty,int x, int y){
        super(BULLETWIDTH,BULLETHEIGHT,ID.Bullet, Color.BLACK);
    }

    @Override
    public void tick() {
        x+= vecX;
        y+= vecY;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x,y,BULLETWIDTH,BULLETHEIGHT);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
