package Entities;

import Attacks.Attack;

import java.awt.*;

public class Hitbox extends Rectangle {

    private int xOffset, yOffset; // Offset from positions
    public double worldX, worldY;
    public double screenX, screenY;

    /**
     * Hitbox constructor
     * @param worldXPos world x position
     * @param worldYPos world y position
     * @param width width
     * @param height height
     */
    public Hitbox(int worldXPos, int worldYPos, int screenX, int screenY, int width, int height, int xOffset, int yOffset) {
        super(worldXPos, worldYPos, width, height);
        this.worldX = worldXPos;
        this.worldY = worldYPos;

        this.x = worldXPos;
        this.y = worldYPos;

        this.xOffset = xOffset;
        this.yOffset = yOffset;

        this.screenX = screenX;
        this.screenY = screenY;

    }

    /**
     * Updates hitbox values based on entity movement and offset
     * @param entity to update
     */
    public void update(Entity entity) {
        worldX = entity.worldX+xOffset;
        worldY = entity.worldY+yOffset;
        x = (int)(entity.worldX+xOffset);
        y = (int)(entity.worldY+yOffset);
        screenX = entity.screenX+xOffset;
        screenY = entity.screenY+yOffset;
    }

    /**
     * Updates hitbox values based on attack movement and offset
     * @param attack attack to update
     */
    public void update(Attack attack) {
        worldX = attack.getX()+xOffset;
        worldY = attack.getY()+yOffset;
        x = (int)(attack.getX()+xOffset);
        y = (int)(attack.getY() +yOffset);
        screenX = attack.getScreenX()+xOffset;
        screenY = attack.getScreenY()+yOffset;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect((int)screenX, (int)screenY, width, height);
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
    public double getWorldX() {
        return worldX;
    }

    /**
     * Gets world y position
     * @return world y position
     */
    public double getWorldY() {
        return this.worldY;
    }

}
