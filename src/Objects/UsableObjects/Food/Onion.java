package Objects.UsableObjects.Food;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;

public class Onion extends UsableObject {

    public static int timesUsed = 0;

    public Onion(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/food.png");

        value = 20;

    }

    @Override
    public void getImageCoords() {
        imageX = 16*3;
        imageY = 16*2;
    }

    @Override
    public void isUsed(Player player) {
        Onion.timesUsed++;
        player.healthHandler.isHit(1, true);
        player.speedBoostUsed();
    }
}