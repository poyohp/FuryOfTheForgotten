package Objects.UsableObjects;

import Entities.Players.Player;
import Handlers.CollisionHandler;
import Handlers.ImageHandler;
import World.Level;
import World.Tile;

public class Key extends UsableObject {

    private final double distanceToUnlock = Tile.tileSize * 1.5;

    public Key(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/chests&keys.png");

    }

    @Override
    public void getImageCoords() {
        imageX = 16*5;
        imageY = 16*5;
    }

    /**
     * Unlocks the current level's door if key is used and door is unlockable
     * @param player
     */
    @Override
    public void isUsed(Player player) {
        if(CollisionHandler.getDistance(player, player.currentLevel.getMap().door) <= distanceToUnlock && player.currentLevel.doorUnlockable) {
            player.currentLevel.doorUnlocked = true;
        }
    }

}
