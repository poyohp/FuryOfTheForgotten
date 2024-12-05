package Entities;

import Attacks.Attack;

import java.awt.*;

public class Hitbox extends Rectangle {

    private int xOffset, yOffset; // Offset from positions
    public double worldXPos, worldYPos;

    /**
     * Hitbox constructor
     * @param worldXPos world x position
     * @param worldYPos world y position
     * @param width width
     * @param height height
     */
    public Hitbox(int worldXPos, int worldYPos, int screenX, int screenY, int width, int height) {
        super(screenX, screenY, width, height);
        this.worldXPos = worldXPos;
        this.worldYPos = worldYPos;

    }

    /**
     * Updates hitbox values based on entity movement and offset
     * @param entity to update
     */
    public void update(Entity entity) {
        worldXPos = entity.worldX+xOffset;
        worldYPos = entity.worldY+yOffset;
        x = (int)(entity.screenX+xOffset);
        y = (int)(entity.screenY+yOffset);
    }

    /**
     * Updates hitbox values based on attack movement and offset
     * @param attack attack to update
     */
    public void update(Attack attack) {
        worldXPos = attack.getX()+xOffset;
        worldYPos = attack.getY()+yOffset;
        x = (int)(attack.getScreenX()+xOffset);
        y = (int)(attack.getScreenY() +yOffset);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);
    }

    public void draw2(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(x, y, width, height);
    }

    /**
     * Gets width
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets height
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets world x position
     * @return world x position
     */
    public double getWorldXPos() {
        return worldXPos;
    }

    /**
     * Gets world y position
     * @return world y position
     */
    public double getWorldYPos() {
        return this.worldYPos;
    }

}
