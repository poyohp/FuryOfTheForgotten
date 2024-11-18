package Handlers;

public class Hitbox {

    private int xOffset, yOffset;
    private int width, height;
    private int worldXPos, worldYPos;

    Hitbox(int xOffset, int yOffset, int worldXPos, int worldYPos) {
        this.worldXPos = worldXPos;
        this.worldYPos = worldYPos;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    void setHitbox() {
        worldXPos += xOffset;
        worldYPos += yOffset;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWorldXPos() {
        return worldXPos;
    }

    public void setWorldXPos(int xWorldPos) {
        this.worldXPos = xWorldPos;
    }

    public int getWorldYPos() {
        return this.worldYPos;
    }

    public void setWorldYPos(int yWorldPos) {
        this.worldYPos = yWorldPos;
    }
}
