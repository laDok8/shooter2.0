package sample;


import java.awt.*;


// TODO: move everything to double
public class Bullet extends GameObject {
    static private final int BULLETWIDTH = 5,DAMAGE = 20;
    //Controller controller;

    //new Bullet(obj.x + 16, obj.y + 16,ID.Bullet,controller, Color.darkGray,x,y));
    public Bullet(int x, int y, Controller controller, float vecx, float vecy){

        super(x,y,ID.Bullet, Color.BLACK);
        this.vecX = (vecx-x)/10;
        this.vecY = (vecy-y)/10;
        //this.controller = controller;
    }

    @Override
    public void tick() {
        x+= vecX;
        y+= vecY;

        for (var obj: Controller.object) {
            if( obj == this)
                continue;
            if(getBounds().intersects(obj.getBounds())){
                Controller.remove(this);
                if(obj instanceof Player){
                    ((Player) obj).getDmg(DAMAGE);
                }
                break;
            }

        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y,BULLETWIDTH,BULLETWIDTH);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,BULLETWIDTH,BULLETWIDTH);
    }
}
