package Attacks.Melee;

import Entities.Entity;
import Handlers.ImageHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Swipe extends Melee {

    /**
     * Create melee attack
     *
     * @param range     attack range
     * @param width     attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity    Entity attack corresponds to
     * @param xOffset   attack x offset
     * @param yOffset   attack y offset
     * @param duration  attack duration
     */
    public Swipe(double damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        type = 's';
    }

    @Override
    public void draw(Graphics2D g2) {
        //NO ACTION NEEDED
        //BOSS SWIPE DRAWN WITH BOSS CHARACTER!
    }
}
