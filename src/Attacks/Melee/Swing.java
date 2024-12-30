package Attacks.Melee;

import Entities.Entity;
import Handlers.ImageHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Swing extends Melee {

    BufferedImage swing = ImageHandler.loadImage("Assets/Projectiles/Swing.png");

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
    public Swing(int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        super(1.0, range, width, direction, entity, xOffset, yOffset, duration);
    }

    @Override
    public void draw(Graphics2D g2) {

        switch (entity.animationState) {
            case 0:
                if (angle == 0) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getRange()), (int) (getScreenY() + getWidth()), 28, 121, 48, 148, null);
                } else if (angle == Math.PI/2) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getWidth()), (int) (getScreenY() + getRange()), 11, 168, 35, 195, null);
                } else if (angle == Math.PI) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getRange()), (int) (getScreenY() + getWidth()), 0, 65, 19, 91, null);
                } else {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getWidth()), (int) (getScreenY() + getRange()), 11, 17, 32, 45, null);
                }
                break;
            case 1:
                if (angle == 0) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getRange()), (int) (getScreenY() + getWidth()), 28 + 48, 121, 48 + 48, 148, null);
                } else if (angle == Math.PI/2) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getWidth()), (int) (getScreenY() + getRange()), 11 + 48, 168, 35 + 48, 195, null);
                } else if (angle == Math.PI) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getRange()), (int) (getScreenY() + getWidth()), 55, 65, 19 + 55, 91, null);
                } else {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getWidth()), (int) (getScreenY() + getRange()), 11 + 55, 17, 32 + 55, 45, null);
                }
                break;
            case 2:
                if (angle == 0) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getRange()), (int) (getScreenY() + getWidth()), 28 + 48 + 48, 121, 48 + 48 + 48, 148, null);
                } else if (angle == Math.PI/2) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getWidth()), (int) (getScreenY() + getRange()), 11 + 48 + 48, 168, 35 + 48 + 48, 195, null);
                } else if (angle == Math.PI) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getRange()), (int) (getScreenY() + getWidth()), 55 + 55, 65, 19 + 55 + 55, 91, null);
                } else {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getWidth()), (int) (getScreenY() + getRange()), 11 + 55 + 55, 17, 32 + 55 + 55, 45, null);
                }
                break;
            case 3:
                if (angle == 0) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getRange()), (int) (getScreenY() + getWidth()), 28 + 48 + 48 + 48, 121, 48 + 48 + 48 + 48, 148, null);
                } else if (angle == Math.PI/2) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getWidth()), (int) (getScreenY() + getRange()), 11 + 48 + 48 + 48, 168, 35 + 48 + 48 + 48, 195, null);
                } else if (angle == Math.PI) {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getRange()), (int) (getScreenY() + getWidth()), 55 + 55 + 55, 65, 19 + 55 + 55 + 55, 91, null);
                } else {
                    g2.drawImage(swing, (int) getScreenX(), (int) getScreenY(), (int) (getScreenX() + getWidth()), (int) (getScreenY() + getRange()), 11 + 55 + 55 + 49, 17, 32 + 55 + 55 + 49, 45, null);
                }
                break;
        }

    }

}


