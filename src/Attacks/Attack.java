package Attacks;


import Entities.Entity;
import Entities.Hitbox;
import Entities.Player;
import System.GamePanel;
import World.Tile;


import java.awt.*;


public abstract class Attack {

    // Attack attributes
    private int damage, range, width, duration, speed;
    private double worldX, worldY, screenX, screenY;
    public double angle;
    private char[] direction = new char[2];
    public Hitbox hitbox;
    Entity entity;


    /**
     * Create attack
     * @param damage attack damage
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity Entity attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     */
    public Attack (int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        this.damage = damage;
        this.range = range;
        this.width = width;
        this.direction[0] = entity.direction;
        this.direction[1] = direction;
        this.entity = entity;
        this.duration = duration;
    }

    /**
     * Get horizontal speed
     * @param angle angle from right perpendicular
     * @param speed speed of projectile
     * @return
     */
    public abstract double determineXVelocity(double angle, int speed);

    /**
     * Get vertical speed
     * @param angle angle from right perpendicular
     * @param speed speed of projectile
     * @return
     */
    public abstract double determineYVelocity(double angle, int speed);

    /**
     * Move projectile
     * @param xSpeed horizontal speed
     * @param ySpeed vertical speed
     */
    public abstract void move(int xSpeed, int ySpeed);

    public abstract void update(Player player);

    /**
     * Determine screen position based on world position
     */
    public abstract void setScreenPosition(Player player);

    /**
     * Draw attack
     * @param g2 Graphics2D object for drawing
     */
    public abstract void draw(Graphics2D g2);

    /**
     * Set the initial hitbox of the attack
     */
    public abstract void setInitialHitbox();

    /**
     * Get attack damage
     * @return attack damage
     */
    public int getDamage() { return damage; }

    /**
     * Get attack range
     * @return attack range
     */
    public int getRange() {
        return range;
    }

    /**
     * Get attack width
     * @return attack width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get attack direction(s)
     * @return attack direction(s)
     */
    public char[] getDirection() {
        return direction;
    }

    /**
     * Get attack duration
     * @return attack duration
     */
    public int getDuration() { return duration; }

    /**
     * Set attack duration
     * @param duration new attack duration
     */
    public void setDuration(int duration) { this.duration = duration; }

    /**
     * Get horizontal screen position
     * @return horizontal screen position
     */
    public double getScreenX() {
        return screenX;
    }

    /**
     * Get vertical screen position
     * @return vertical screen position
     */
    public double getScreenY() {
        return screenY;
    }

    /**
     * Set horizontal screen position
     * @param screenX new horizontal screen position
     */
    public void setScreenX(double screenX) {
        this.screenX = screenX;
    }

    /**
     * Set vertical screen position
     * @param screenY new vertical screen position
     */
    public void setScreenY(double screenY) {
        this.screenY = screenY;
    }

    /**
     * Get attack speed
     * @return attack speed
     */
    public int getSpeed() { return speed; }

    /**
     * Set attack speed
     * @param speed new attack speed
     */
    public void setSpeed(int speed) { this.speed = speed; }

    public void setWorldX(double worldX) { this.worldX = worldX; }
    public void setWorldY(double worldY) { this.worldY = worldY; }

    /**
     * Gets world x
     * @return world x
     */
    public double getWorldX() { return worldX; }

    /**
     * Gets world y
     * @return world y
     */
    public double getWorldY() { return worldY; }

}
