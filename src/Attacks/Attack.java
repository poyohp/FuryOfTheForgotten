package Attacks;

import Entities.Entity;
import Handlers.Hitbox;

public abstract class Attack {

    private int damage, range, width, x, y, xOffset, yOffset, duration;
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



    abstract void draw();

    abstract void setInitialHitbox(char[] direction);

    public int getDamage() { return damage; }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public char getDirection(int index) {
        return direction[index];
    }

    public void setDirection(char[] direction) { this.direction = direction; }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public int getXOffset() { return xOffset; }

    public int getYOffset() { return yOffset; }
}
