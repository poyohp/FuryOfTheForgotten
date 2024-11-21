package Attacks;

import Entities.Entity;
import Entities.Hitbox;

import java.awt.*;

public class Ranged extends Attack{

    int speed;

    Ranged(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int speed, int duration) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        this.speed = speed;
    }

    @Override
    void setInitialHitbox(char[] direction) {
        if (direction[0] == 'u') {
            if (direction[1] == 'r') {
                setX(entity.worldX + entity.getWidth());
                setY((int)(entity.worldY - getRange()*Math.sin(45.0)));
            } else if (direction[1] == 'l') {
                setX((int)(entity.worldX - getRange()*Math.cos(45.0)));
                setY((int)(entity.worldY - getRange()*Math.sin(45.0)));
            } else {
                setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
                setY(entity.worldY - getRange());
            }
        } else if (direction[0] == 'r') {
            if (direction[1] == 'u') {
                setX(entity.worldX + entity.getWidth());
                setY((int)(entity.worldY - getRange()*Math.sin(45.0)));
            } else if (direction[1] == 'd') {
                setX(entity.worldX + entity.getWidth());
                setY(entity.worldY + entity.getHeight());
            } else {
                setX(entity.worldX + entity.getWidth() + getRange());
                setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
            }
        } else if (direction[0] == 'd') {
            if (direction[1] == 'r') {
                setX(entity.worldX + entity.getWidth());
                setY(entity.worldY + entity.getHeight());
            } else if (direction[1] == 'l') {
                setX((int)(entity.worldX - getRange()*Math.cos(45.0)));
                setY((int)(entity.worldY + entity.getHeight() + getRange()*Math.sin(45)));
            } else {
                setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
                setY(entity.worldY + getRange());
            }
        } else {
            if (direction[1] == 'd') {
                setX((int)(entity.worldX - getRange()*Math.cos(45.0)));
                setY((int)(entity.worldY + entity.getHeight() + getRange()*Math.sin(45.0)));
            } else if (direction[1] == 'u') {
                setX((int)(entity.worldX - getRange()*Math.cos(45.0)));
                setY((int)(entity.worldY - getRange()*Math.sin(45.0)));
            } else {
                setX(entity.worldX - getRange());
                setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getRange() / 2);
            }
        }

        if (direction[0] == 'u' || direction[0] == 'd') {
            if (direction[1] == 'r' || direction[1] == 'l') {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), (int)(getRange()*Math.cos(45.0)), (int)(getWidth()*Math.cos(45.0)));
            } else {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getWidth(), getRange());
            }
        } else {
            if (direction[1] == 'u' || direction[1] == 'd') {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), (int)(getRange()*Math.cos(45.0)), (int)(getWidth()*Math.cos(45.0)));
            } else {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getRange(), getWidth());
            }
        }
    }

    @Override
    void draw(Graphics2D g2) {

    }

}
