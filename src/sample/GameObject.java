package sample;

import java.awt.*;

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
                "x=" + x +
                ", y=" + y +
                ", vecX=" + vecX +
                ", vecY=" + vecY +
                ", color=" + color +
                ", id=" + id +
                '}';
    }
}
