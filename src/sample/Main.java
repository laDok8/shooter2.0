package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    protected List<Bullet> bulletList = new ArrayList<>();
    protected List<Player> playerList = new ArrayList<>();
    int CIRCLEWIDTH = 10, STRIDE = 5;
    int WINDOWWIDTH = 600, WINDOWHEIGHT = 500;
    Map map;
    Player player1;
    Player dumym1;
    Player dummy2;
    Pane root;


    //  change/check every frame
    AnimationTimer anim = new AnimationTimer() {
        @Override
        public void handle(long l) {

            List<Bullet> bulToRemove = new ArrayList<>();
            List<Player> plToRemove = new ArrayList<>();
            for (var bul: bulletList ) {
                bul.update();
                if( bul.checkColision(playerList) )
                    bulToRemove.add(bul);
            }
            for (var bul: bulToRemove ) {
                bulletList.remove(bul);
                root.getChildren().remove(bul);
            }

            //odstraneni primo -> strelba do nohy
            for (var player: playerList ) {
                if(!player.isAlive()){
                    plToRemove.add(player);
                }
            }
            for (var player: plToRemove ) {
                root.getChildren().remove(player);
                playerList.remove(player);
            }
            
            bulToRemove.clear();
            plToRemove.clear();


            root.relocate(-(player1.getX()-CIRCLEWIDTH/2-WINDOWWIDTH/2),-(player1.getY()-CIRCLEWIDTH/2-WINDOWWIDTH/2));
        }


    };

    @Override
     /* canvas - neumoznuje kreslit objekty
      scene - nutnost odstranovat/pridavat vsechny objekty
     */
    public void start(Stage primaryStage){
        root = new Pane();
        primaryStage.setTitle("Hello World");
        Scene mScene = new Scene(root, WINDOWWIDTH, WINDOWHEIGHT);



        //nastavi pozadi
        map = new Map();
        map.set(root,"file:mirage.png");

        player1 = new Player(CIRCLEWIDTH,350,150,"macho", Color.BLUE, map);
        dumym1 = new Player(CIRCLEWIDTH,200,200,"Deviant", Color.RED, map);
        dummy2 = new Player(CIRCLEWIDTH,400,250,"Jericho", Color.GREEN, map);


        //keyListener
        mScene.setOnKeyPressed( e -> {
            switch (e.getCode()) {
                case W -> player1.moveY(-STRIDE);
                case S -> player1.moveY(STRIDE);
                case A -> player1.moveX(-STRIDE);
                case D -> player1.moveX(STRIDE);
                case F -> shoot(player1,3, 2);
            }
        });



        //mouseListener
        mScene.setOnMouseClicked( e -> {
            //relative to window
            //System.out.println("x: "+ String.valueOf(e.getX()-WINDOWHEIGHT) + " y: "+ e.getY()-WINDOWHEIGHT);
            shoot(player1,e.getX()-WINDOWWIDTH,e.getY()-WINDOWHEIGHT);
        });

        primaryStage.setScene(mScene);
        primaryStage.show();


        playerList.add(player1);
        playerList.add(dumym1);
        playerList.add(dummy2);

        for (var player: playerList ) {
            root.getChildren().add(player);
        }

        anim.start();
    }

    public void shoot(Player player,double x, double y) {

        //normalizovany vektor
        double vecL = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        x/=vecL;
        y/=vecL;

        Bullet b = new Bullet( x, y, player.getX(), player1.getY());
        bulletList.add(b);
        root.getChildren().add(b);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
