package Attacks.Melee;

import Attacks.Attack;
import Entities.Entity;
import Entities.Hitbox;
import Entities.Players.Player;
import Handlers.ImageHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Melee extends Attack {


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
    public Melee(double damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        setSpeed(0);
        setInitialHitbox();
        type = 'm';
        canHitTile = true;
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
        hitbox.update(this);
        setScreenPosition(player);
    }

    /**
     * Set intial screen postition of melee attack
     */
    @Override
    public void setScreenPosition(Player player) {
        // If direction is up
        if (player.direction == 'u') {
            angle = Math.PI/2;
            setScreenX(entity.screenX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setScreenY(entity.screenY - getRange());
        // If direction is right
        } else if (player.direction == 'r') {
            angle = 0;
            setScreenX(entity.screenX + entity.getWidth());
            setScreenY(entity.screenY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        // If direction is down
        } else if (player.direction == 'd') {
            angle = 3*Math.PI/2;
            setScreenX(entity.screenX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setScreenY(entity.screenY + entity.getHeight());
        // If direction is left
        } else {
            angle = Math.PI;
            setScreenX(entity.screenX - getRange());
            setScreenY(entity.screenY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        }
    }

    /**
     * Set initial hitbox based on associated entity
     */
    @Override
    public void setInitialHitbox() {

        // Entity looks up
        if (getDirection()[0] == 'u') {
            setWorldX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setWorldY(entity.worldY - getRange());
        // Entity looks right
        } else if (getDirection()[0] == 'r') {
            setWorldX(entity.worldX + entity.getWidth());
            setWorldY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        // Entity looks down
        } else if (getDirection()[0] == 'd') {
            setWorldX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
            setWorldY(entity.worldY + entity.getHeight());
        // Entity looks left
        } else {
            setWorldX(entity.worldX - getRange());
            setWorldY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
        }

        // Determine if the range of the attack is in vertical or horizontal, and create the hitbox accordingly
        if (getDirection()[0] == 'u' || getDirection()[0] == 'd') {
            hitbox = new Hitbox((int) getWorldX(), (int) getWorldY(), (int)getScreenX(), (int)getScreenY(), getWidth(), getRange(), 0, 0);
        } else {
            hitbox = new Hitbox((int) getWorldX(), (int) getWorldY(), (int)getScreenX(), (int)getScreenY(), getRange(), getWidth(), 0, 0);
        }

        hitbox.update(this);
    }

    /**
     * Draw the melee attack
     * @param g2 Graphics2D object for drawing
     */
    @Override
    public void draw(Graphics2D g2) {
        // NO BASE MELEE DRAWING
    }
}