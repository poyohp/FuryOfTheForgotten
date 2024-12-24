package Objects;

import Entities.Hitbox;
import Entities.Players.Player;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Object {

    BufferedImage image;

    String name;

    // equippable meaning you can have it in your INVENTORY
    // pickedUp meaning you have picked it up (and its IN your inventory)
    public boolean isEquippable, isPickedUp;

    public Hitbox hitbox;

    private final int objectSize = Tile.normalTileSize;
    public final int HUDWidth = Tile.tileMultipler*objectSize;
    public final int HUDHeight = Tile.tileMultipler*objectSize;

    public int imageX, imageY;
    public int width, height;
    public double worldX, worldY, screenX, screenY;

    public Object(String name, int width, int height, double worldX, double worldY, double screenX, double screenY) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = screenX;
        this.screenY = screenY;

        isEquippable = false;
        isPickedUp = false;

        hitbox = new Hitbox((int)worldX, (int)worldY, (int)screenX, (int) screenY, width, height, 0, 0);

    }

    public void update(Player player) {
        setScreenPosition(player);
    }


    public void setScreenPosition(Player player) {
        this.screenX = worldX - player.worldX + player.screenX;
        this.screenY = worldY - player.worldY + player.screenY;
    }

    public abstract void draw(Graphics2D g2);

}
