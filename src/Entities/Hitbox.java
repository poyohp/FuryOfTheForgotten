package Entities;

import Attacks.Attack;

import java.awt.*;

public class Hitbox extends Rectangle {

    private int xOffset, yOffset; // Offset from positions
    private double worldXPos, worldYPos;

    public Hitbox(int worldXPos, int worldYPos, int xOffset, int yOffset, int width, int height) {
        super(worldXPos, worldYPos, width, height);
        this.worldXPos = worldXPos;
        this.worldYPos = worldYPos;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Updates hitbox values based on entity movement and offset
     * @param entity to update
     */
    public void update(Entity entity) {
        worldXPos = entity.worldX+xOffset;
        worldYPos = entity.worldY+yOffset;
        x = (int)(entity.worldX+xOffset);
        y = (int)(entity.worldY+yOffset);
    }

    /**
     * Updates hitbox values based on attack movement and offset
     * @param attack attack to update
     */
    public void update(Attack attack) {
        worldXPos = attack.getX()+xOffset;
        worldYPos = attack.getY()+yOffset;
        x = (int)(attack.getX()+xOffset);
        y = (int)(attack.getY()+yOffset);
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
