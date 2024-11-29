package Attacks;


import Entities.Entity;
import Entities.Hitbox;
import System.GamePanel;


import java.awt.*;


public abstract class Attack {


    private int damage, range, width, xOffset, yOffset, duration, speed;
    private double worldX, worldY, screenX, screenY;
    public double angle;
    private char[] direction = new char[2];
    public Hitbox hitbox;
    Entity entity;


    public Attack (int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        this.damage = damage;
        this.range = range;
        this.width = width;
        this.direction[0] = entity.direction;
        this.direction[1] = direction;
        this.entity = entity;
        this.duration = duration;
    }

    public abstract double determineXVelocity(double angle, int speed);

    public abstract double determineYVelocity(double angle, int speed);

    public abstract void move(int xSpeed, int ySpeed);

    public abstract void setScreenPosition();


    public abstract void draw(Graphics2D g2);


    public abstract void setInitialHitbox(char[] direction);


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


    public int getDuration() { return duration; }


    public void setDuration(int duration) { this.duration = duration; }


    public double getScreenX() {
        return screenX;
    }


    public double getScreenY() {
        return screenY;
    }


    public void setScreenX(double screenX) {
        this.screenX = screenX;
    }


    public void setScreenY(double screenY) {
        this.screenY = screenY;
    }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

}
