package Entities;
import Handlers.KeyHandler;

public class Player extends Entity {
    Boolean isColliding, interact, attack;
    KeyHandler keyhandler;

    Player(int health, int speed, int width, int height, String name) {
        super(health, speed, width, height, name);
        keyhandler = new KeyHandler();
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

    void interact () {
        if (interact) {
            //
        }
    }

    void defend () {
    }

    void attack () {

    }



}
