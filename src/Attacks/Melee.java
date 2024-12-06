package Attacks;

import Entities.Entity;
import Entities.Hitbox;
import Entities.Player;

import java.awt.*;

public class Melee extends Attack{

    /**
     * Create melee attack
     * @param damage attack damage
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity Entity attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     */
    public Melee(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        setSpeed(0);
        setInitialHitbox();
    }

    /**
     * Filler method
     * @param angle angle from right perpendicular
     * @param speed speed of projectile
     * @return
     */
    @Override
    public double determineXVelocity(double angle, int speed) {
        return 0;
    }

    /**
     * Filler method
     * @param angle angle from right perpendicular
     * @param speed speed of projectile
     * @return
     */
    @Override
    public double determineYVelocity(double angle, int speed) {
        return 0;
    }

    /**
     * Filler method
     * @param xSpeed horizontal speed
     * @param ySpeed vertical speed
     */
    @Override
    public void move(int xSpeed, int ySpeed) {

    }

    @Override
    public void update(Player player) {
        setScreenPosition(player);
    }


    /**
     * Set intial screen postition of melee attack
     */
    @Override
    public void setScreenPosition(Player player ) {
        // If direction is up
        if (getDirection()[0] == 'u') {
            setScreenX(entity.screenX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setScreenY(entity.screenY - getRange());
        // If direction is right
        } else if (getDirection()[0] == 'r') {
            setScreenX(entity.screenX + entity.getWidth());
            setScreenY(entity.screenY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        // If direction is down
        } else if (getDirection()[0] == 'd') {
            setScreenX(entity.screenX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setScreenY(entity.screenY + entity.getHeight());
        // If direction is left
        } else {
            setScreenX(entity.screenX - getRange());
            setScreenY(entity.screenY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        }
    }

    /**
     * Set initial hitbox based on associated entity
     */
    @Override
    public void setInitialHitbox() {
        /*
        // Entity looks up
        if (direction[0] == 'u') {
            setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setY(entity.worldY - getRange());
        // Entity looks right
        } else if (direction[0] == 'r') {
            setX(entity.worldX + entity.getWidth());
            setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        // Entity looks down
        } else if (direction[0] == 'd') {
            setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setY(entity.worldY + entity.getHeight());
        // Entity looks left
        } else {
            setX(entity.worldX - getRange());
            setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        }

        // Determine if the range of the attack is in vertical or horizontal, and create the hitbox accordingly
        if (direction[0] == 'u' || direction[0] == 'd') {
            hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getWidth(), getRange());
        } else {
            hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getRange(), getWidth());
        }

         */
    }

    /**
     * Draw the melee attack
     * @param g2 Graphics2D object for drawing
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        if (getDirection()[0] == 'u' || getDirection()[0] == 'd') {
            g2.fillRect((int) getScreenX(), (int) getScreenY(), getWidth(), getRange());
        } else {
            g2.fillRect((int) getScreenX(), (int) getScreenY(), getRange(), getWidth());
        }
    }

}