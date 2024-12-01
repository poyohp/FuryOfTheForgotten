package Entities;

import Attacks.Attack;

import java.awt.*;

public class Hitbox extends Rectangle{

    private int xOffset, yOffset;
    private double worldXPos, worldYPos;

    public Hitbox(int worldXPos, int worldYPos, int xOffset, int yOffset, int width, int height) {
        super(worldXPos, worldYPos, width, height);
        this.worldXPos = worldXPos;
        this.worldYPos = worldYPos;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void drawHitbox(Graphics2D g2, Player player) {
        g2.setColor(Color.BLACK);
        g2.fillRect((int)(this.worldXPos - player.worldX + player.screenX), (int)(this.worldYPos - player.worldY + player.screenY), width, height);
    }

    public void update(Entity entity) {
        worldXPos = entity.worldX+xOffset;
        worldYPos = entity.worldY+yOffset;
        x = (int)(entity.worldX+xOffset);
        y = (int)(entity.worldY+yOffset);
    }

    public void update(Attack attack) {
        worldXPos = attack.getX()+xOffset;
        worldYPos = attack.getY()+yOffset;
        x = (int)(attack.getX()+xOffset);
        y = (int)(attack.getY()+yOffset);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getWorldXPos() {
        return worldXPos;
    }

    public double getWorldYPos() {
        return this.worldYPos;
    }

}
