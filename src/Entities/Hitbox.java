package Entities;

import Attacks.Attack;

import java.awt.*;

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

    public void drawHitbox(Graphics2D g2, Player player) {
        g2.setColor(Color.BLACK);
        g2.fillRect((int)(this.worldXPos - player.worldX + player.screenX), (int)(this.worldYPos - player.worldY + player.screenY), width, height);
    }

    public void update(Entity entity) {
        worldXPos = entity.worldX+xOffset;
        worldYPos = entity.worldY+yOffset;
    }

    public void update(Attack attack) {
        worldXPos = attack.getX()+xOffset;
        worldYPos = attack.getY()+yOffset;
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
