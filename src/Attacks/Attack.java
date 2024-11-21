package Attacks;

import Entities.Entity;
import Handlers.Hitbox;

import java.awt.*;

public abstract class Attack {

    private int damage, range, width, xOffset, yOffset, duration;
    private double worldX, worldY;
    private char[] direction = new char[2];
    public Hitbox hitbox;
    Entity entity;

    Attack (int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        this.damage = damage;
        this.range = range;
        this.width = width;
        this.direction[0] = entity.direction;
        this.direction[1] = direction;
        this.entity = entity;
        this.duration = duration;
    }

    abstract void draw(Graphics2D g2);

    abstract void setInitialHitbox(char[] direction);

    public int getDamage() { return damage; }

    public int getRange() {
        return range;
    }

    public int getWidth() {
        return width;
    }

    public char[] getDirection() {
        return direction;
    }

    public double getX() { return worldX; }

    public void setX(double x) { this.worldX = x; }

    public double getY() { return worldY; }

    public void setY(double y) { this.worldY = y; }

    public int getXOffset() { return xOffset; }

    public int getYOffset() { return yOffset; }
}
