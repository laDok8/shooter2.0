package sample;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Map {
    String name;
    private Image img;
    Background b;

    public void set(Pane root, String s) {
        name = s;
        img = new Image(name,2500,2500,true,true,true);
        BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        b = new Background(bi);
        root.setBackground(b);
    }


    public boolean IsBorder(int x, int y) {
        Color clr = img.getPixelReader().getColor(x, y);
        //return clr.equals(Color.BLACK);
        return !clr.equals(Color.WHITE);
    }
}
