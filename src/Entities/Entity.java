package Entities;

import Handlers.Hitbox;
import java.awt.*;

public abstract class Entity {
    private int health, attackCooldown;
    private double speed;
    private int width, height;
    private int defenseReduction;

    String name;

    public double worldX, worldY;
    public double screenX, screenY;
    public char direction;

    public double entityLeft, entityRight, entityTop, entityBottom;

    public Hitbox hitbox;

    abstract void draw(Graphics2D g2);

    abstract void update();

    public Entity (int health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset) {
        this.health = health;
        this.speed = speed;
        this.width = width;
        this.height = height;

        this.name = name;
        this.direction = 'r';

        this.worldX = worldX;
        this.worldY = worldY;
        hitbox = new Hitbox((int)worldX, (int)worldY, xOffset, yOffset, width, height);
        hitbox.setHitbox();

        entityLeft = hitbox.getWorldXPos();
        entityRight = entityLeft + (double) hitbox.getWidth();
        entityTop = hitbox.getWorldYPos();
        entityBottom = entityTop + (double) hitbox.getHeight();
    }

    public int getHealth() {
        return health;
    }

    public double getSpeed() {
        return speed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
