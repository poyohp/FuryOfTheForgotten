package Attacks;

import Entities.Entity;
import java.awt.*;

public abstract class Attack {

    private int damage, range, width, x, y;
    private char direction;
    Entity entity;

    Attack (int damage, int range, int width, char direction, Entity entity) {
        this.damage = damage;
        this.range = range;
        this.width = width;
        this.direction = direction;
        this.entity = entity;
    }

    abstract void draw();

    public int getDamage() {
        return damage;
    }

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

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
