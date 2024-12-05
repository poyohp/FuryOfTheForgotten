package Attacks;

import Entities.Entity;
import Entities.Hitbox;
import Handlers.ImageHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Arrow extends Ranged{
    static BufferedImage arrow = ImageHandler.loadImage("src/Assets/Projectiles/Arrow.png");
    double drawAngle;

    /**
     * Create ranged attack
     *
     * @param damage    attack damage
     * @param range     attack range
     * @param width     attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity    Entity attack corresponds to
     * @param xOffset   attack x offset
     * @param yOffset   attack y offset
     * @param duration  attack duration
     * @param speed     attack speed
     */
    public Arrow(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed, double angle) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration, speed);
        this.angle = angle;
        spawnDistance = 100;
        setInitialHitbox();

    }

    @Override
    public void setInitialHitbox() {
        int centerWorldX = (int)(entity.worldX + (double) entity.getWidth() /2);
        int centerWorldY = (int)(entity.worldY + (double) entity.getHeight() /2);
        int centerScreenX = (int)(entity.screenX + (double) entity.getWidth() /2);
        int centerScreenY = (int)(entity.screenY + (double) entity.getHeight() /2);
        int hitBoxWidth, hitBoxHeight, hitBoxX, hitBoxY, hitBoxWorldX, hitBoxWorldY;

        if (angle == 0 || angle == Math.PI) {
            hitBoxWidth = getRange();
            hitBoxHeight = getWidth();
        } else if (angle == Math.PI/2 || angle == 3*Math.PI/2) {
            hitBoxWidth = getWidth();
            hitBoxHeight = getRange();
        } else {
            hitBoxWidth = (int)(getRange() * Math.abs(Math.cos(angle)));
            hitBoxHeight = (int)(getRange() * Math.abs(Math.sin(angle)));
        }

        hitBoxX = (int)(centerScreenX + spawnDistance*Math.cos(angle) - hitBoxWidth/2);
        hitBoxY = (int)(centerScreenY + -1 * spawnDistance*Math.sin(angle) - hitBoxHeight/2);
        hitBoxWorldX = (int)(centerWorldX + spawnDistance*Math.cos(angle) - hitBoxWidth/2);
        hitBoxWorldY = (int)(centerWorldY + -1 * spawnDistance*Math.sin(angle) - hitBoxHeight/2);

        /*
        System.out.println(hitBoxX + " " + hitBoxY);
        System.out.println(hitBoxWidth + " " + hitBoxHeight);
        */
        //System.out.println(hitBoxWorldX + " " + hitBoxWorldY);

        hitbox = new Hitbox(hitBoxWorldX, hitBoxWorldY, hitBoxX, hitBoxY, hitBoxWidth, hitBoxHeight, 0, 0);
        setX(hitBoxWorldX);
        setY(hitBoxWorldY);
    }

    @Override
    public void draw(Graphics2D g2) {
        setScreenPosition();

        AffineTransform originalTransform = g2.getTransform();

        int centerX = (int) getScreenX() + getWidth() / 2;
        int centerY = (int) getScreenY() + getRange() / 2;

        if (getDirection()[0] == 'u') {
            // ROTATING IMAGE 270 DEGREES (UP)
            this.drawAngle = -Math.PI/2;
            if (getDirection()[1] == 'u' || getDirection()[1] == 'd') {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX() + 20, (int) getScreenY(), (int) getScreenX() + getWidth() + 20, (int) getScreenY() + getRange(), 0, 32, 16, 48);
            } else {;
                ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX() + 15, (int) getScreenY() - 20, (int) getScreenX() + getWidth() + 15, (int) getScreenY() + getRange() - 20, 0, 32, 16, 48);
            }
        } else if (getDirection()[0] == 'r') {
            // NO NEED TO ROTATE IMAGE HERE!
            if (getDirection()[1] == 'r' || getDirection()[1] == 'l') {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX(), (int) getScreenY() + 26, (int) getScreenX() + getWidth(), (int) getScreenY() + getRange() + 26, 0, 32, 16, 48);
            } else {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX(), (int) getScreenY() + 22, (int) getScreenX() + getWidth(), (int) getScreenY() + getRange() + 22, 0, 32, 16, 48);
            }
        } else if (getDirection()[0] == 'l') {
            // ROTATING IMAGE 180 DEGREES (LEFT)
            this.drawAngle = Math.PI;
            if (getDirection()[1] == 'r' || getDirection()[1] == 'l') {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX() - 20, (int) getScreenY() - 20, (int) getScreenX() + getWidth() - 20, (int) getScreenY() + getRange() - 20, 0, 32, 16, 48);
            } else {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX() - 20, (int) getScreenY() - 25, (int) getScreenX() + getWidth() - 20, (int) getScreenY() + getRange() - 25, 0, 32, 16, 48);
            }
        } else {
            // ROTATING IMAGE 90 DEGREES (DOWN)
            this.drawAngle = Math.PI/2;
            if (getDirection()[1] == 'u' || getDirection()[1] == 'd') {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX() - 25, (int) getScreenY() + 10, (int) getScreenX() + getWidth() - 25, (int) getScreenY() + getRange() + 10, 0, 32, 16, 48);
            } else {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX() - 25, (int) getScreenY() + 10, (int) getScreenX() + getWidth() - 25, (int) getScreenY() + getRange() + 10, 0, 32, 16, 48);
            }
        }

        g2.setTransform(originalTransform);
        hitbox.draw(g2);
    }

    @Override
    public void update() {
        //System.out.println((int)determineXVelocity(angle, getSpeed()) + " " + (int)determineYVelocity(angle, getSpeed()));
        move((int)determineXVelocity(angle, getSpeed()), (int)determineYVelocity(angle, getSpeed()));
        hitbox.update(this);
    }

}
