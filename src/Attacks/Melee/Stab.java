package Attacks.Melee;

import Entities.Entity;
import Handlers.ImageHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Stab extends Melee {

    BufferedImage stab1 = ImageHandler.loadImage("src/Assets/Projectiles/Stab1.png");
    BufferedImage stab2 = ImageHandler.loadImage("src/Assets/Projectiles/Stab2.png");
    BufferedImage stab3 = ImageHandler.loadImage("src/Assets/Projectiles/Stab3.png");
    BufferedImage stab1Up = ImageHandler.loadImage("src/Assets/Projectiles/Stab1Up.png");
    BufferedImage stab2Up = ImageHandler.loadImage("src/Assets/Projectiles/Stab2Up.png");
    BufferedImage stab3Up = ImageHandler.loadImage("src/Assets/Projectiles/Stab3Up.png");

    /**
     * Create melee attack
     *
     * @param damage    attack damage
     * @param range     attack range
     * @param width     attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity    Entity attack corresponds to
     * @param xOffset   attack x offset
     * @param yOffset   attack y offset
     * @param duration  attack duration
     */
    public Stab(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        //System.out.println(getScreenX());
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.setColor(Color.BLACK);

        int centerX, centerY;

        switch (entity.animationState) {
            case 0, 3:
                if (angle == Math.PI || angle == 0) {
                    centerX = (int) getScreenX() + (getRange() / 2);
                    centerY = (int) getScreenY() + (getWidth() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY, angle + Math.PI, stab1, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getRange(), (int) getScreenY() + getWidth(), 0, 0, 1000, 500);
                } else if (angle == Math.PI / 2) {
                    centerX = (int) getScreenX() + (getWidth() / 2);
                    centerY = (int) getScreenY() + (getRange() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY, Math.PI, stab1Up, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + getRange(), 0, 0, 500, 1000);
                } else {
                    centerX = (int) getScreenX() + (getWidth() / 2);
                    centerY = (int) getScreenY() + (getRange() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY, 2*Math.PI, stab1Up, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + getRange(), 0, 0, 500, 1000);
                }
                break;
            case 1:
                if (angle == Math.PI || angle == 0) {
                    centerX = (int) getScreenX() + (getRange() / 2);
                    centerY = (int) getScreenY() + (getWidth() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY, angle + Math.PI, stab3, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getRange(), (int) getScreenY() + getWidth(), 0, 0, 1000, 500);
                } else if (angle == Math.PI / 2) {
                    centerX = (int) getScreenX() + (getWidth() / 2);
                    centerY = (int) getScreenY() + (getRange() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY,  Math.PI, stab3Up, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + getRange(), 0, 0, 500, 1000);
                } else {
                    centerX = (int) getScreenX() + (getWidth() / 2);
                    centerY = (int) getScreenY() + (getRange() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY, 2*Math.PI, stab3Up, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + getRange(), 0, 0, 500, 1000);
                }
                break;
            case 2:
                if (angle == Math.PI || angle == 0) {
                    centerX = (int) getScreenX() + (getRange() / 2);
                    centerY = (int) getScreenY() + (getWidth() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY, angle + Math.PI, stab2, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getRange(), (int) getScreenY() + getWidth(), 0, 0, 1000, 500);
                } else if (angle == Math.PI / 2) {
                    centerX = (int) getScreenX() + (getWidth() / 2);
                    centerY = (int) getScreenY() + (getRange() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY, Math.PI, stab2Up, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + getRange(), 0, 0, 500, 1000);
                } else {
                    centerX = (int) getScreenX() + (getWidth() / 2);
                    centerY = (int) getScreenY() + (getRange() / 2);
                    ImageHandler.drawRotatedImage(g2, centerX, centerY, 2*Math.PI, stab2Up, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + getRange(), 0, 0, 500, 1000);
                }
                break;
        }
        //hitbox.draw(g2);
    }
}
