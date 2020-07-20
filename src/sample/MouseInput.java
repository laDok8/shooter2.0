package sample;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private Controller controller;
    private Camera camera;

    public MouseInput(Controller controller, Camera camera) {
        this.controller = controller;
        this.camera = camera;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX() + camera.getX();
        int my = e.getY() + camera.getY();

        //potreba zmenit pri vice hracich
        for (var obj : controller.object) {
            if (obj.id == ID.Player) {
                controller.addObject(new Bullet(obj.x + 16, obj.y + 16, ID.Bullet, controller, Color.RED, mx, my));
            }
        }
    }
}
