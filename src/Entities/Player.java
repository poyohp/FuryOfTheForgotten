package Entities;
import Handlers.KeyHandler;
import System.GamePanel;

import java.awt.*;

public class Player extends Entity {
    Boolean isColliding, interact, attack;
    KeyHandler keyhandler;

    Player(int health, int speed, int width, int height, String name) {
        super(health, speed, width, height, name);

        setScreenPosition();

        keyhandler = new KeyHandler();
    }

    void setScreenPosition() {
        this.screenX = (int) GamePanel.screenHeight / 2 - this.getWidth() / 2;
        this.screenY = (int) GamePanel.screenWidth / 2 - this.getHeight() / 2;
    }

    // ResourceBar healthBar;

    void update() {
        if (!isColliding) move();
    }

    void move() {
        if (keyhandler.upPress) {
            worldY -= getSpeed();
            direction = 'u';
        } else if (keyhandler.downPress) {
            worldY += getSpeed();
            direction = 'd';
        }
        if (keyhandler.leftPress) {
            worldX -= getSpeed();
            direction = 'l';
        } else if (keyhandler.rightPress) {
            worldX += getSpeed();
            direction = 'r';
        }

    }

    @Override
    void draw(Graphics2D g) {

    }
}
