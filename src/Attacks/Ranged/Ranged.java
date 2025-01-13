package Attacks.Ranged;
import Attacks.Attack;
import Entities.Entity;
import Entities.Hitbox;
import java.awt.*;

import Entities.Players.Player;

public class Ranged extends Attack {

    int spawnDistance;

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
    public Ranged(double damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        setSpeed(speed);
        type = 'r';
    }

    /**
     * Sets initial hitbox of ranged attack either on axis or 45 degrees away
     */
    @Override
    public void setInitialHitbox() {
    }


    /**
     * Calculates attack angle from right horizontal
     * @param attack hitbox of attack
     * @param entity hitbox of entity
     * @return integer value angle of attack angle in relation to right horizontal
     */
    public double calculateAttackAngle(Hitbox attack, Hitbox entity) {
        double attackX = attack.getWorldX() + (double) attack.getWidth()/2;
        double attackY = attack.getWorldY() + (double) attack.getHeight()/2;
        double entityX = entity.getWorldX() + (double) entity.getWidth()/2;
        double entityY = entity.getWorldY() + (double) entity.getHeight()/2;


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
        setWorldX(getWorldX() + xSpeed);
        setWorldY(getWorldY() + ySpeed);
    }

    @Override
    public void update(Player player) {
        move((int)determineXVelocity(angle, getSpeed()), (int)determineYVelocity(angle, getSpeed()));
    }


    /**
     * Sets the screen position of the ranged attack based on the world position
     */
    @Override
    public void setScreenPosition(Player player) {
        setScreenX(getWorldX() - player.worldX + player.screenX);
        setScreenY(getWorldY() - player.worldY + player.screenY);
    }

    /**
     * Draws the projectile
     * @param g2 Graphics2D object for drawing
     */
    @Override
    public void draw(Graphics2D g2) {
    }

}