package Attacks;

import Entities.Entity;
import Entities.Hitbox;

import java.awt.*;

public class Melee extends Attack{

    Hitbox hitbox;

    Melee(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);

        setInitialHitbox(this.getDirection());
    }

    @Override
    public void setInitialHitbox(char[] direction) {
        if (direction[0] == 'u') {
            setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setY(entity.worldY - getRange());
        } else if (direction[0] == 'r') {
            setX(entity.worldX + entity.getWidth() + getRange());
            setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        } else if (direction[0] == 'd') {
            setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setY(entity.worldY + getRange());
        } else {
            setX(entity.worldX - getRange());
            setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getRange() / 2);
        }

        if (direction[0] == 'u' || direction[0] == 'd') {
            hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getWidth(), getRange());
        } else {
            hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getRange(), getWidth());
        }
    }

    @Override
    void draw(Graphics2D g2) {

    }

}
