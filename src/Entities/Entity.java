package Entities;

import java.awt.*;

public abstract class Entity {
    private double health;
    private double speed;
    private int width, height;
    private int defenseReduction;

    String name;

    public double worldX, worldY;
    public double screenX, screenY;
    public char direction;
    public boolean attacking = false;
    public int attackCooldown = 120;

    public double entityLeft, entityRight, entityTop, entityBottom;

    public Hitbox hitbox;

    abstract void draw(Graphics2D g2);


    public Entity (int health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight) {
        this.health = health;
        this.speed = speed;
        this.width = width;
        this.height = height;

        this.name = name;
        this.direction = 'r';

        this.worldX = worldX;
        this.worldY = worldY;
        hitbox = new Hitbox((int)worldX, (int)worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);
        hitbox.update(this);

        entityLeft = hitbox.getWorldXPos();
        entityRight = entityLeft + (double) hitbox.getWidth();
        entityTop = hitbox.getWorldYPos();
        entityBottom = entityTop + (double) hitbox.getHeight();
    }

    public void updateEntityPosition() {
        hitbox.update(this);
        entityLeft = hitbox.getWorldXPos();
        entityRight = entityLeft + (double) hitbox.getWidth();
        entityTop = hitbox.getWorldYPos();
        entityBottom = entityTop + (double) hitbox.getHeight();
    }

    public void drawHealth(Graphics2D g2) {
        g2.drawString(String.format("%.2f", this.health), (int)this.screenX, (int)(this.screenY - 20));
    }

    public double getHealth() {
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

    public void setHealth(double newHealth) {
        health = newHealth;
        if (newHealth < 0) health = 0;
    }

}
