package Entities;

public class Hitbox {

    private int xOffset, yOffset;
    private int width, height;
    private double worldXPos, worldYPos;

    public Hitbox(int worldXPos, int worldYPos, int xOffset, int yOffset, int width, int height) {
        this.worldXPos = worldXPos;
        this.worldYPos = worldYPos;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
    }

    public void setHitbox() {
        worldXPos += xOffset;
        worldYPos += yOffset;
        width -= 2*xOffset;
        height -= 2*yOffset;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getWorldXPos() {
        return worldXPos;
    }

    public double getWorldYPos() {
        return this.worldYPos;
    }

}
