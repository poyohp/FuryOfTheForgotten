package Attacks;

import Entities.Entity;
import Entities.Hitbox;

import java.awt.*;

public class Melee extends Attack{

    Hitbox hitbox;

    public Melee(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        setSpeed(0);
        setInitialHitbox(this.getDirection());
    }

    @Override
    public double determineXVelocity(double angle, int speed) {
        return 0;
    }

    @Override
    public double determineYVelocity(double angle, int speed) {
        return 0;
    }

    @Override
    public void move(int xSpeed, int ySpeed) {

    }

    @Override
    public void setScreenPosition() {
        if (getDirection()[0] == 'u') {
            setScreenX(entity.screenX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setScreenY(entity.screenY - getRange());
        } else if (getDirection()[0] == 'r') {
            setScreenX(entity.screenX + entity.getWidth());
            setScreenY(entity.screenY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        } else if (getDirection()[0] == 'd') {
            setScreenX(entity.screenX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setScreenY(entity.screenY + entity.getHeight());
        } else {
            setScreenX(entity.screenX - getRange());
            setScreenY(entity.screenY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        }
    }

    @Override
    public void setInitialHitbox(char[] direction) {
        if (direction[0] == 'u') {
            setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setY(entity.worldY - getRange());
        } else if (direction[0] == 'r') {
            setX(entity.worldX + entity.getWidth());
            setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        } else if (direction[0] == 'd') {
            setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setY(entity.worldY + entity.getHeight());
        } else {
            setX(entity.worldX - getRange());
            setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        }

        if (direction[0] == 'u' || direction[0] == 'd') {
            hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getWidth(), getRange());
        } else {
            hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getRange(), getWidth());
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        setScreenPosition();
        g2.setColor(Color.BLACK);
        if (getDirection()[0] == 'u' || getDirection()[0] == 'd') {
            g2.fillRect((int) getScreenX(), (int) getScreenY(), getWidth(), getRange());
        } else {
            g2.fillRect((int) getScreenX(), (int) getScreenY(), getRange(), getWidth());
        }
    }

}