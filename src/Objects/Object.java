package Objects;

import World.Tile;

public class Object {

    String name;

    boolean isEquippable;
    boolean isPickedUp;

    private final int objectSize = Tile.normalTileSize;
    public final int HUDWidth = Tile.tileMultipler*objectSize;
    public final int HUDHeight = Tile.tileMultipler*objectSize;

    public int worldX, worldY, screenX, screenY;

    public Object(String name, int screenX, int screenY) {
        this.name = name;

        isEquippable = false;
        isPickedUp = false;
    }

}
