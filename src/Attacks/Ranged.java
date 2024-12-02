package Attacks;
import Entities.Entity;
import Entities.Hitbox;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Handlers.ImageHandler;
import System.GamePanel;
import System.Main;

public class Ranged extends Attack{
    static BufferedImage arrow = ImageHandler.loadImage("src/Assets/Projectiles/Arrow.png");

    /**
     * Create ranged attack
     * @param damage attack damage
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity Entity attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     * @param speed attack speed
     */
    public Ranged(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        setInitialHitbox(getDirection());
        setSpeed(speed);
    }

    /**
     * Sets initial hitbox of ranged attack either on axis or 45 degrees away
     * @param direction directions of entity
     */
    @Override
    public void setInitialHitbox(char[] direction) {
        // Set position of hitbox
        // Entity looks up
        if (direction[0] == 'u') {
            // Entity second direction input is right
            if (direction[1] == 'r') {
                setX(entity.entityRight);
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            // Entity second direction input is left
            } else if (direction[1] == 'l') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            // Entity second direction input is on the same axis as it faces
            } else {
                setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
                setY(entity.worldY - getRange());
            }
        // Entity looks right
        } else if (direction[0] == 'r') {
            // Entity second direction input is up
            if (direction[1] == 'u') {
                setX(entity.entityRight);
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            // Entity second direction input is down
            } else if (direction[1] == 'd') {
                setX(entity.entityRight);
                setY(entity.entityBottom);
            // Entity second direction input is on the same axis as it faces
            } else {
                setX(entity.entityRight);
                setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
            }
        // Entity looks down
        } else if (direction[0] == 'd') {
            // Entity second direction input is right
            if (direction[1] == 'r') {
                setX(entity.entityRight);
                setY(entity.entityBottom);
            // Entity second direction input is left
            } else if (direction[1] == 'l') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY + entity.getHeight()));
            // Entity second direction input is on the same axis as it faces
            } else {
                setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
                setY(entity.entityBottom);
            }
        // Entity looks left
        } else {
            // Entity second direction input is down
            if (direction[1] == 'd') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY + entity.getHeight()));
            // Entity second direction input is up
            } else if (direction[1] == 'u') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            // Entity second direction input is on the same axis as it faces
            } else {
                setX(entity.worldX - getRange());
                setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
            }
        }

        // Set hitbox of attack
        if (direction[0] == 'u' || direction[0] == 'd') {
            if (direction[1] == 'r' || direction[1] == 'l') {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), (int)(getRange()*Math.cos(Math.PI/4)), (int)(getWidth()*Math.cos(Math.PI/4)));
            } else {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getWidth(), getRange());
            }
        } else {
            if (direction[1] == 'u' || direction[1] == 'd') {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), (int)(getRange()*Math.cos(Math.PI/4)), (int)(getWidth()*Math.cos(Math.PI/4)));
            } else {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getRange(), getWidth());
            }
        }
    }


    /**
     * Calculates attack angle from right horizontal
     * @param attack hitbox of attack
     * @param entity hitbox of entity
     * @return integer value angle of attack angle in relation to right horizontal
     */
    public double calculateAttackAngle(Hitbox attack, Hitbox entity) {
        double attackX = attack.getWorldXPos() + (double) attack.getWidth()/2;
        double attackY = attack.getWorldYPos() + (double) attack.getHeight()/2;
        double entityX = entity.getWorldXPos() + (double) entity.getWidth()/2;
        double entityY = entity.getWorldYPos() + (double) entity.getHeight()/2;


        if (entityX > attackX && entityY > attackY) {
            angle = (2*Math.PI) - Math.atan(((entityY - attackY) / (entityX - attackX)));
        } else if (entityX > attackX && entityY < attackY) {
            angle = Math.atan((attackY - entityY) / (entityX - attackX));
        } else if (entityX < attackX && entityY > attackY) {
            angle = Math.PI + Math.atan(((entityY - attackY) / (attackX - entityX)));
        } else {
            angle = Math.PI - Math.atan(((attackY - entityY) / (attackX - entityX)));
        }

        return angle;
    }

    /**
     * Determines horizontal velocity of projectile
     * @param angle angle from right perpendicular
     * @param speed speed of projectile
     * @return  double value horizontal velocity of attack
     */
    @Override
    public double determineXVelocity(double angle, int speed) {
        return speed * Math.cos(angle);
    }

    /**
     * Determines vertical velocity of projectile
     * @param angle angle from right perpendicular
     * @param speed speed of projectile
     * @return  double value vertical velocity of attack
     */
    @Override
    public double determineYVelocity(double angle, int speed) {
        return -speed * Math.sin(angle);
    }


    /**
     * Moves ranged attack
     * @param xSpeed horizontal speed
     * @param ySpeed vertical speed
     */
    @Override
    public void move(int xSpeed, int ySpeed) {
        setX(getX() + xSpeed);
        setY(getY() + ySpeed);
        hitbox.update(this);
    }


    /**
     * Sets the screen position of the ranged attack based on the world position
     */
    @Override
    public void setScreenPosition() {
        int screenTop = (int)(entity.worldY + entity.getHeight()/2 - GamePanel.screenHeight/2);
        int screenRight = (int)(entity.worldX + entity.getWidth()/2 + GamePanel.screenWidth/2);
        int screenBottom = (int)(entity.worldY + entity.getHeight()/2 + GamePanel.screenHeight/2);
        int screenLeft = (int)(entity.worldX + entity.getWidth()/2 - GamePanel.screenWidth/2);
        int top = (int)(getY());
        int left = (int)(getX());
        int bottom = (int)(getY() + getRange());
        int right = (int)(getX() + getWidth());

        if ((bottom > screenTop && right > screenLeft) || (bottom > screenTop && left < screenRight) || (top < screenBottom && right > screenLeft) || (top < screenBottom && left > screenRight)) {
            setScreenX(getX() - screenLeft);
            setScreenY(getY() - screenTop);
        }

    }

    /**
     * Draws the projectile
     * @param g2 Graphics2D object for drawing
     */
    @Override
    public void draw(Graphics2D g2) {
        setScreenPosition();

        AffineTransform originalTransform = g2.getTransform();

        int centerX = (int) getScreenX() + getWidth() / 2;
        int centerY = (int) getScreenY() + getRange() / 2;

        if (getDirection()[0] == 'u') {
            // ROTATING IMAGE 270 DEGREES (UP)
            this.angle = -Math.PI/2;
            if (getDirection()[1] == 'u' || getDirection()[1] == 'd') {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, angle, arrow, (int) getScreenX() + 20, (int) getScreenY(), (int) getScreenX() + getWidth() + 20, (int) getScreenY() + getRange(), 0, 32, 16, 48);
            } else {;
                ImageHandler.drawRotatedImage(g2, centerX, centerY, angle, arrow, (int) getScreenX() + 15, (int) getScreenY() - 20, (int) getScreenX() + getWidth() + 15, (int) getScreenY() + getRange() - 20, 0, 32, 16, 48);
            }
        } else if (getDirection()[0] == 'r') {
            // NO NEED TO ROTATE IMAGE HERE!
            if (getDirection()[1] == 'r' || getDirection()[1] == 'l') {
                g2.drawImage(arrow, (int) getScreenX(), (int) getScreenY() + 26, (int) getScreenX() + getWidth(), (int) getScreenY() + getRange() + 26, 0, 32, 16, 48, null);
            } else {
                g2.drawImage(arrow, (int) getScreenX(), (int) getScreenY() + 22, (int) getScreenX() + getWidth(), (int) getScreenY() + getRange() + 22, 0, 32, 16, 48, null);
            }
        } else if (getDirection()[0] == 'l') {
            // ROTATING IMAGE 180 DEGREES (LEFT)
            this.angle = Math.PI;
            if (getDirection()[1] == 'r' || getDirection()[1] == 'l') {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, angle, arrow, (int) getScreenX() - 20, (int) getScreenY() - 20, (int) getScreenX() + getWidth() - 20, (int) getScreenY() + getRange() - 20, 0, 32, 16, 48);
            } else {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, angle, arrow, (int) getScreenX() - 20, (int) getScreenY() - 25, (int) getScreenX() + getWidth() - 20, (int) getScreenY() + getRange() - 25, 0, 32, 16, 48);
            }
        } else {
            // ROTATING IMAGE 90 DEGREES (DOWN)
            this.angle = Math.PI/2;
            if (getDirection()[1] == 'u' || getDirection()[1] == 'd') {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, angle, arrow, (int) getScreenX() - 25, (int) getScreenY() + 10, (int) getScreenX() + getWidth() - 25, (int) getScreenY() + getRange() + 10, 0, 32, 16, 48);
            } else {
                ImageHandler.drawRotatedImage(g2, centerX, centerY, angle, arrow, (int) getScreenX() - 25, (int) getScreenY() + 10, (int) getScreenX() + getWidth() - 25, (int) getScreenY() + getRange() + 10, 0, 32, 16, 48);
            }
        }

        g2.setTransform(originalTransform);

    }

}