package Entities;
import Handlers.KeyHandler;
import System.GamePanel;

import java.awt.*;

public class Player extends Entity {
    Boolean isColliding, interact, attack;
    public KeyHandler keyHandler;

    public Player(int health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, KeyHandler keyHandler) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset);

        setScreenPosition();

        isColliding = false;

        this.keyHandler = keyHandler;

    }

    void setScreenPosition() {
        this.screenX = (int) (GamePanel.screenWidth / 2) - (double) this.getWidth() / 2;
        this.screenY = (int) (GamePanel.screenHeight / 2) - (double) this.getHeight() / 2;
    }

    // ResourceBar healthBar;

    public void update() {
        if (!isColliding) move();
    }

    private void move() {
        if (keyHandler.upPress) {
            this.worldY -= getSpeed();
            direction = 'u';
        } else if (keyHandler.downPress) {
            worldY += getSpeed();
            direction = 'd';
        }
        if (keyHandler.leftPress) {
            worldX -= getSpeed();
            direction = 'l';
        } else if (keyHandler.rightPress) {
            worldX += getSpeed();
            direction = 'r';
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.fillRect((int) screenX, (int) screenY, this.getWidth(), this.getHeight());
        g2.setColor(Color.RED);
        g2.drawRect((int) screenX, (int) screenY, this.getWidth(), this.getHeight());
    }
}
