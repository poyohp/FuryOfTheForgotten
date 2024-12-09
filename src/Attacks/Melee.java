package Attacks;

import Entities.Entity;
import Entities.Hitbox;
import Entities.Players.Player;
import Handlers.ImageHandler;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Melee extends Attack{

    BufferedImage stab1 = ImageHandler.loadImage("src/Assets/Projectiles/Stab1.png");
    BufferedImage stab2 = ImageHandler.loadImage("src/Assets/Projectiles/Stab2.png");
    BufferedImage stab3 = ImageHandler.loadImage("src/Assets/Projectiles/Stab3.png");

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
            hitbox = new Hitbox((int) getWorldX(), (int) getWorldY(), (int)getScreenX(), (int)getScreenY(), getRange(), getWidth()                                                                                                                                                                                                                                                                                                                                                                                                                           , 0, 0);
        }


    }

    /**
     * Draw the melee attack
     * @param g2 Graphics2D object for drawing
     */
    @Override
    public void draw(Graphics2D g2) {

        if (entity.direction == 'u') {
            switch (entity.animationState) {
                case 0, 3:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getWidth()/2), (int)(getScreenY() + getRange()/2), -Math.PI/2, stab1, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getWidth()), (int)(getScreenY() + getRange()), 0, 0, 16, 16);
                    break;
                case 1:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getWidth()/2), (int)(getScreenY() + getRange()/2), -Math.PI/2, stab3, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getWidth()), (int)(getScreenY() + getRange()), 0, 0, 16, 16);
                    break;
                case 2:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getWidth()/2), (int)(getScreenY() + getRange()/2), -Math.PI/2, stab2, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getWidth()), (int)(getScreenY() + getRange()), 0, 0, 16, 16);
                    break;
            }
        } else if (entity.direction == 'l') {
            switch (entity.animationState) {
                case 0, 3:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getRange()/2), (int)(getScreenY() + getWidth()/2), Math.PI, stab1, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getRange()), (int)(getScreenY() + getWidth()), 0, 0, 16, 16);
                    break;
                case 1:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getRange()/2), (int)(getScreenY() + getWidth()/2), Math.PI, stab3, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getRange()), (int)(getScreenY() + getWidth()), 0, 0, 16, 16);
                    break;
                case 2:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getRange()/2), (int)(getScreenY() + getWidth()/2), Math.PI, stab2, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getRange()), (int)(getScreenY() + getWidth()), 0, 0, 16, 16);
                    break;
            }
        } else if (entity.direction == 'd') {
            switch (entity.animationState) {
                case 0, 3:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getWidth()/2), (int)(getScreenY() + getRange()/2), -3*Math.PI/2, stab1, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getWidth()), (int)(getScreenY() + getRange()), 0, 0, 16, 16);
                    break;
                case 1:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getWidth()/2), (int)(getScreenY() + getRange()/2), -3*Math.PI/2, stab3, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getWidth()), (int)(getScreenY() + getRange()), 0, 0, 16, 16);
                    break;
                case 2:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getWidth()/2), (int)(getScreenY() + getRange()/2), -3*Math.PI/2, stab2, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getWidth()), (int)(getScreenY() + getRange()), 0, 0, 16, 16);
                    break;
            }
        } else {
            switch (entity.animationState) {
                case 0, 3:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getRange()/2), (int)(getScreenY() + getWidth()/2), 0, stab1, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getRange()), (int)(getScreenY() + getWidth()), 0, 0, 16, 16);
                    break;
                case 1:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getRange()/2), (int)(getScreenY() + getWidth()/2), 0, stab3, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getRange()), (int)(getScreenY() + getWidth()), 0, 0, 16, 16);
                    break;
                case 2:
                    ImageHandler.drawRotatedImage(g2, (int)(getScreenX() + getRange()/2), (int)(getScreenY() + getWidth()/2), 0, stab2, (int)getScreenX(), (int)getScreenY(), (int)(getScreenX() + getRange()), (int)(getScreenY() + getWidth()), 0, 0, 16, 16);
                    break;
            }
        }
    }
}