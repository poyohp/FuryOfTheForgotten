package Entities;

import Attacks.Attack;
import Objects.Object;

import java.awt.*;

public class Hitbox extends Rectangle {

    public int xOffset;
    public int yOffset; // Offset from positions
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
        x = (int)worldX;
        y = (int)worldY;
        screenX = entity.screenX+xOffset;
        screenY = entity.screenY+yOffset;
    }

    /**
     * Updates hitbox values based on attack movement and offset
     * @param attack attack to update
     */
    public void update(Attack attack) {
        worldX = attack.getWorldX()+xOffset;
        worldY = attack.getWorldY()+yOffset;
        x = (int)worldX;
        y = (int)worldY;
        screenX = attack.getScreenX()+xOffset;
        screenY = attack.getScreenY()+yOffset;
    }

    public void update(Object object) {
        worldX = object.worldX+xOffset;
        worldY = object.worldY+yOffset;
        x = (int)worldX;
        y = (int)worldY;
        screenX = object.screenX+xOffset;
        screenY = object.screenY+yOffset;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawRect((int)screenX, (int)screenY, width, height);
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
