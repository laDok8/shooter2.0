package sample;

import javafx.scene.input.KeyCode;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static java.net.InetAddress.*;

class Game extends Canvas {


    final int BLOCKWIDTH = 32, STRIDE = 5;
    public static final int WINDOWWIDTH = 1000, WINDOWHEIGHT = 563;
    Thread thread;
    boolean running,dead,pripojit,jmeno;
    Controller controller;
    Camera camera;
    private BufferedImage map;
    Network network;
    private StringBuilder Input;
    private String menuString;
    private String Name;

    //init
    public Game(){
        controller = new Controller();
        //this.addKeyListener(new KeyInput(controller));

        camera = new Camera(0,0);
        this.addMouseListener(new MouseInput(controller, camera));

        new Window(WINDOWWIDTH, WINDOWHEIGHT, "  Terorist Strike: Lado Offensive", this);

        /*Map mapcl = new Map();
        map = mapcl.loadImage("/mirage1.png");
        loadLevel(map);*/

        //prvni spusteni vytvori servr i klienta
        //network = new Network();
        //controller.attachConnection(network);

        start();
    }

    public synchronized void start() {

        running = false;
        pripojit = false;
        jmeno = true;
        Name = "";
        thread = new Thread(() -> run());
        thread.start();
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
                //black
                if( pixel == 0x000000)
                    controller.addObject(new Block(x*BLOCKWIDTH,y*BLOCKWIDTH,ID.Block,Color.BLACK));
                //green
                if( pixel == 0x00ff00 && !nacten){
                    controller.addObject(new Player(x*BLOCKWIDTH,y*BLOCKWIDTH, Name,Color.blue,controller));
                    nacten = true;
                }

            }
        }
    }




    //zkopirovana game loop
    public void run() {
        Input = new StringBuilder();
        this.requestFocus();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(pripojit || jmeno) {
                    char c = e.getKeyChar();
                    if(c == '\n'){
                        if(jmeno){
                            jmeno = false;
                            Name = Input.toString();
                            Input.delete(0,Input.length());
                        }
                        else if(pripojit){
                            network = new Network(Input.toString());
                            controller.attachConnection(network);
                            running = true;
                        }
                        return;
                    }
                    Input.append(e.getKeyChar());
                    return;

                }
                else if(jmeno){
                    char c = e.getKeyChar();
                    if(c == '\n'){
                        jmeno = false;
                        return;
                    }
                    Name.concat(String.valueOf(c));

                }
                switch (e.getKeyCode()){
                    case KeyEvent.VK_1:pripojit = true;break;
                    case KeyEvent.VK_2:
                        try {
                            final DatagramSocket socket = new DatagramSocket();
                            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                            String ip = socket.getLocalAddress().getHostAddress();
                            network = new Network(ip);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        controller.attachConnection(network);
                        running = true;
                        break;
                }
            }
        });
        while(!running){
            renderMenu();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Map mapcl = new Map();
        map = mapcl.loadImage("/mirage1.png");
        loadLevel(map);

        this.removeKeyListener(this.getKeyListeners()[0]);
        this.addKeyListener(new KeyInput(controller));

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
                render();
                tick();
                delta--;
            }
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
        //pohyb kamery
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
        g.setColor(Color.black);
        //muze byt vic IP, vytvorim spojeni a zjistim, kterou IP vyuzivam
        try {
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ip = socket.getLocalAddress().getHostAddress();
            g.drawString(ip,0,10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        g2d.translate(-camera.getX(),-camera.getY());

        controller.render(g);


        g.dispose();
        bs.show();
    }

    private void renderMenu() {
        menuString = (!pripojit?"1. PRIPOJIT SE \n 2. ZALOZIT":"IP SERVERU (entr)");
        //TODO prepisu
        if(jmeno)
            menuString = "ZADEJ JMENO";
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0,1920,1080);
        g.setColor(Color.black);
        g.setFont(Font.getFont("Sans Serif 64"));
        drawString(g,menuString,200,100);
        g.drawString(Input.toString(),400,400);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }

    void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
}
