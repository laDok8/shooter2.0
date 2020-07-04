package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Map {
    String name;
    private Image img;

    public void set(Pane root, String s) {
        name = s;
        img = new Image(name,2500,2500,false,false);
        BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(bi));
    }


    public boolean IsBorder(int x, int y){
        Color clr = img.getPixelReader().getColor(x,y);
        return clr.equals(Color.BLACK);
    }
}
