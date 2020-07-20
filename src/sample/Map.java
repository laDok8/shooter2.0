package sample;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Map {
    private BufferedImage image;

    public BufferedImage loadImage(String nazev){
        try {
            image = ImageIO.read(getClass().getResource(nazev));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
