package sample;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    Controller controller;

    public KeyInput(Controller controller){
        this.controller = controller;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //potreba zmenit pri vice hracich
        for (var obj : controller.object  ) {
            if(obj.id == ID.Player){
                switch (key){
                    case KeyEvent.VK_W -> controller.setUp(true);
                    case KeyEvent.VK_A -> controller.setLeft(true);
                    case KeyEvent.VK_S -> controller.setDown(true);
                    case KeyEvent.VK_D -> controller.setRight(true);
                }
            }
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        //potreba zmenit pri vice hracich
        for (var obj : controller.object) {
            if(obj.id == ID.Player){
                switch (key){
                    case KeyEvent.VK_W -> controller.setUp(false);
                    case KeyEvent.VK_A -> controller.setLeft(false);
                    case KeyEvent.VK_S -> controller.setDown(false);
                    case KeyEvent.VK_D -> controller.setRight(false);
                }
            }
        }
    }
}
