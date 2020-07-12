package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Map {
    private Image img;

    public Map(int sirka, int vyska, String nazev) {

        Image image = new Image(nazev);
        img = new Image(nazev,sirka*4,vyska*4,true,true,true);
    }
    public Image getMap(){
        return img;
    }

    public boolean IsBorder(int x, int y) {
        Color clr = img.getPixelReader().getColor(x, y);
        //return clr.equals(Color.BLACK);
        return !clr.equals(Color.WHITE);
    }
}
