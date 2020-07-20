package sample;

import java.awt.*;

public class Block extends GameObject{

    final int BLOCKWIDTH = 32;
    public Block(int x,int y,ID id,Color color){
        super(x,y,id,color);
    }
    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x,y,BLOCKWIDTH,BLOCKWIDTH);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,BLOCKWIDTH,BLOCKWIDTH);
    }
}
