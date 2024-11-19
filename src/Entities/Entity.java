package Entities;

import java.awt.*;

public abstract class Entity {
    private int health, speed, attackCooldown;
    private int width, height;
    private int defenseReduction;

    String name;

    public int worldX, worldY, screenX, screenY;
    public char direction;

    public int entityLeft;
    public int entityRight;
    public int entityTop;
    public int entityBottom;

    //public Hitbox hitbox;

    abstract void draw(Graphics2D g2);

    abstract void update();

    Entity (int health, int speed, int width, int height, String name) {
        this.health = health;
        this.speed = speed;
        this.width = width;
        this.height = height;

        this.name = name;
        this.direction = 'r';

        int entityLeft = this.worldX;
        int entityRight = this.worldX + this.width;
        int entityTop = this.worldY;
        int entityBottom = this.worldY + this.height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDefenseReduction() {
        return defenseReduction;
    }

    public void setDefenseReduction(int defenseReduction) {
        this.defenseReduction = defenseReduction;
    }

}
