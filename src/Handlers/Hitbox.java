package Handlers;

public class Hitbox {

    private int xOffset, yOffset;
    private int width, height;
    private int xWorldPos, yWorldPos;

    Hitbox(int xOffset, int yOffset, int xworldPos, int yWorldPos) {
        this.xWorldPos = xworldPos;
        this.yWorldPos = yWorldPos;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    void setHitbox() {
        xWorldPos += xOffset;
        yWorldPos += yOffset;
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

    public int getxWorldPos() {
        return xWorldPos;
    }

    public void setxWorldPos(int xWorldPos) {
        this.xWorldPos = xWorldPos;
    }

    public int getyWorldPos() {
        return yWorldPos;
    }

    public void setyWorldPos(int yWorldPos) {
        this.yWorldPos = yWorldPos;
    }
}
