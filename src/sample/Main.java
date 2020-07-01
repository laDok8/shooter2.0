package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    protected List<Bullet> bulletList = new ArrayList<>();
    int CIRCLEWIDTH = 10, STRIDE = 5;
    Player player1 = new Player(CIRCLEWIDTH,20,20,"macho", Color.BLUE);
    Pane root;
    @Override
    //zmena
    public void start(Stage primaryStage) throws Exception{
        root = new Pane();
        primaryStage.setTitle("Hello World");
        Scene mScene = new Scene(root, 600, 500);

        mScene.setOnKeyPressed( e -> {
            switch (e.getCode()) {
                case W -> player1.movey(-STRIDE);
                case S -> player1.movey(STRIDE);
                case A -> player1.movex(-STRIDE);
                case D -> player1.movex(STRIDE);
                case F -> shoot(player1);
            }
        });
        Bullet b = new Bullet(3,2, 500, 50);

        primaryStage.setScene(mScene);
        primaryStage.show();
        root.getChildren().add(player1);
        root.getChildren().add( b );
    }

    public void shoot(Player player) {
        Bullet b = new Bullet(3,2, player.getX(), player1.getY());
        bulletList.add(b);
        root.getChildren().add(b);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
