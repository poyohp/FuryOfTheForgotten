package Objects;

import Entities.Hitbox;
import Entities.Players.Player;
import World.Level;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Object {

    public BufferedImage image;

    public String name;

    // equippable meaning you can have it in your INVENTORY
    // pickedUp meaning you have picked it up (and its IN your inventory)
    public boolean isEquippable, isPickedUp;

    public Hitbox hitbox;

    public final int imageWidth = 16;
    public int imageX, imageY;
    public double origWidth, origHeight;
    public double width, height;
    public double worldX, worldY, screenX, screenY;

    public double vx, vy;
    public final double friction = 0.98;

    public Object(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = screenX;
        this.screenY = screenY;

        this.origHeight = height;
        this.origWidth = width;

        this.vx = vx;
        this.vy = vy;

        isEquippable = true;
        isPickedUp = false;

        hitbox = new Hitbox((int)worldX, (int)worldY, (int)screenX, (int) screenY, (int)width, (int)height, 0, 0);

    }

    public void update(Player player) {

        if(!isPickedUp) {
            width = origWidth*0.75;
            height = origHeight*0.75;
        } else {
            width = origWidth;
            height = origHeight;
        }

        this.worldX += vx;
        this.worldY += vy;
        this.vx *= friction;
        this.vy *= friction;

        if(Math.abs(vx) < 0.001) vx = 0;
        if(Math.abs(vy) < 0.001) vy = 0;

        setScreenPosition(player);

        hitbox.update(this);

    }

    public void setScreenPosition(Player player) {
        this.screenX = worldX - player.worldX + player.screenX;
        this.screenY = worldY - player.worldY + player.screenY;
    }

    public abstract void getImageCoords();
    public abstract void isPickedUp(Player player, Level level);
    public abstract void isUsed(Player player);
    public abstract void isDropped(Player player, Level level);
    public abstract void draw(Graphics2D g2);
    public abstract void drawHUD(Graphics2D g2, int x, int y, int size);

}
