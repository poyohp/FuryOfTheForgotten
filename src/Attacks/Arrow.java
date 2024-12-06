package Attacks;

import Entities.Entity;
import Entities.Hitbox;
import Entities.Player;
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
        spawnDistance = 80;
        setInitialHitbox();

    }

    @Override
    public void setInitialHitbox() {
        int centerWorldX = (int)(entity.worldX + ((double) entity.getWidth() /2));
        int centerWorldY = (int)(entity.worldY + ((double) entity.getHeight() /2));
        int centerScreenX = (int)(entity.screenX + ((double) entity.getWidth() /2));
        int centerScreenY = (int)(entity.screenY + ((double) entity.getHeight() /2));
        int hitBoxWidth, hitBoxHeight, hitBoxScreenX, hitBoxScreenY, hitBoxWorldX, hitBoxWorldY;

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

        // LIKELY PROBLEM HERE
        hitBoxScreenX = (int)(centerScreenX + spawnDistance*Math.cos(angle) - (double) hitBoxWidth / 2);
        hitBoxScreenY = (int)(centerScreenY +  (spawnDistance*Math.sin(angle))*-1 - (double) hitBoxHeight / 2);
        hitBoxWorldX = (int)(centerWorldX + spawnDistance*Math.cos(angle) - (double) hitBoxWidth / 2);
        hitBoxWorldY = (int)(centerWorldY +  (spawnDistance*Math.sin(angle))*-1 - (double) hitBoxHeight / 2);


        hitbox = new Hitbox(hitBoxWorldX, hitBoxWorldY, hitBoxScreenX, hitBoxScreenY, hitBoxWidth, hitBoxHeight, 0, 0);
        setWorldX(hitBoxWorldX);
        setWorldY(hitBoxWorldY);
    }

    @Override
    public void draw(Graphics2D g2) {

        int centerX = (int) getScreenX() + (getRange() / 2);
        int centerY = (int) getScreenY() + (getWidth() / 2);

        ImageHandler.drawRotatedImage(g2, centerX, centerY, -angle, arrow, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getRange(), (int) getScreenY() + getWidth(), 3, 38, 12, 42);

        hitbox.draw(g2);
    }

    @Override
    public void update(Player player) {
        //System.out.println((int)determineXVelocity(angle, getSpeed()) + " " + (int)determineYVelocity(angle, getSpeed()));
        move((int)determineXVelocity(angle, getSpeed()), (int)determineYVelocity(angle, getSpeed()));
        setScreenPosition(player);
        hitbox.update(this);
    }

}
