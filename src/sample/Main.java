package sample;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

class Game extends Canvas {


    final int CIRCLEWIDTH = 10, STRIDE = 5;
    public static final int WINDOWWIDTH = 1000, WINDOWHEIGHT = 563;
    Map map;
    Thread thread;
    boolean running;
    Controller controller;
    Image img;
    Camera camera;

    //init
    public Game(){
        controller = new Controller();
        camera = new Camera(0,0);
        img = Toolkit.getDefaultToolkit().createImage("mirage.png");
        img = img.getScaledInstance(WINDOWWIDTH*4, WINDOWHEIGHT*4, Image.SCALE_DEFAULT);
        this.addKeyListener(new KeyInput(controller));
        map = new Map(WINDOWWIDTH,WINDOWHEIGHT,"file:mirage.png");

        controller.addObject(new Player(CIRCLEWIDTH,100,100,"lado",Color.blue,null, ID.Player,controller));

        new Window(WINDOWWIDTH, WINDOWHEIGHT, "  Terorist Strike: Lado Offensive", this);
    }

    public synchronized void start() {
        thread = new Thread();
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    //zkopirovana game loop
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        for (var obj: controller.object) {
            if( obj.id == ID.Player)
                camera.tick(obj);
        }

        controller.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(-camera.getX(),-camera.getY());

        g.drawImage(img,0,0,this);

        controller.render(g);


        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}
