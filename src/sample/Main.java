package sample;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class Game extends Canvas {


    final int BLOCKWIDTH = 32, STRIDE = 5;
    public static final int WINDOWWIDTH = 1000, WINDOWHEIGHT = 563;
    Thread thread;
    boolean running;
    Controller controller;
    Camera camera;
    private BufferedImage map = null;
    Network network;

    //init
    public Game(){
        controller = new Controller();
        this.addKeyListener(new KeyInput(controller));

        camera = new Camera(0,0);
        this.addMouseListener(new MouseInput(controller, camera));

        Map mapcl = new Map();
        map = mapcl.loadImage("/mirage1.png");
        loadLevel(map);

        //prvni spusteni vytvori servr i klienta
        network = new Network();
        controller.attachConnection(network);
        //spinlock
        //TODO: odstrani az pojede
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    private void loadLevel(BufferedImage image){
        boolean nacten = false;
        int w = image.getWidth(),h = image.getHeight();
        for (int x = 0; x < w;++x){
            for (int y = 0; y < h; ++y){
                int pixel = image.getRGB(x,y);
                //maska jen RGB
                pixel&= 0xffffff;
                /*int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;*/
                //black
                if( pixel == 0x000000)
                    controller.addObject(new Block(x*BLOCKWIDTH,y*BLOCKWIDTH,ID.Block,Color.BLACK));
                //green
                if( pixel == 0x00ff00 && !nacten){
                    controller.addObject(new Player(x*BLOCKWIDTH,y*BLOCKWIDTH,"lado",Color.blue, ID.Player,controller));
                    //controller.addObject(new Player(x*BLOCKWIDTH+64,y*BLOCKWIDTH+64,"lado",Color.red, ID.Player,controller));
                    nacten = true;
                }

            }
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
        boolean hotovo = false;
        for (var obj: controller.object) {
            //tick jen na 1. hrace (vzdy ja)
            if( obj.id == ID.Player && ! hotovo) {
                camera.tick(obj);
                hotovo = true;
            }
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

        g.setColor(Color.WHITE);
        g.fillRect(0,0,1920,1080);

        g2d.translate(-camera.getX(),-camera.getY());

        controller.render(g);


        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}
