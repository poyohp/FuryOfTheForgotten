package Attacks;

import Entities.Entity;
import Entities.Hitbox;

public class Arrow extends Ranged{

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
    public Arrow(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration, speed);
        spawnDistance = 100;
    }

    @Override
    public void setInitialHitbox() {
        int centerWorldX = (int)(entity.worldX + (double) entity.getWidth() /2);
        int centerWorldY = (int)(entity.worldY + (double) entity.getHeight() /2);
        int centerScreenX = (int)(entity.screenX + (double) entity.getWidth() /2);
        int centerScreenY = (int)(entity.screenY + (double) entity.getHeight() /2);

        if (angle == 0 || angle == Math.PI) {
            hitbox.width = getRange();
            hitbox.height = getWidth();
        } else if (angle == Math.PI/2 || angle == 3*Math.PI/2) {
            hitbox.width = getWidth();
            hitbox.height = getRange();
        } else {
            hitbox.width = (int)(getRange() * Math.cos(angle));
            hitbox.height = (int)(getRange() * Math.sin(angle));
        }

        hitbox.x = (int)(centerScreenX + spawnDistance*Math.cos(angle) - hitbox.getWidth()/2);
        hitbox.y = -1 * (int)(centerScreenY + spawnDistance*Math.sin(angle) - hitbox.getHeight()/2);
        hitbox.worldXPos = (int)(centerWorldX + spawnDistance*Math.cos(angle) - hitbox.getWidth()/2);
        hitbox.worldYPos = -1 * (int)(centerWorldY + spawnDistance*Math.sin(angle) - hitbox.getHeight()/2);
    }

}
