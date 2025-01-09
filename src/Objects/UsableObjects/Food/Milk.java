package Objects.UsableObjects.Food;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;

public class Milk extends UsableObject {

    public Milk(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/food.png");

        value = 20;

    }

    @Override
    public void getImageCoords() {
        imageX = 16*5;
        imageY = 16*3;
    }

    @Override
    public void isUsed(Player player) {
        if(player.getName().equalsIgnoreCase("Skeleton")) {
            player.healthHandler.activatedShields(player.healthHandler.maxShields);
        } else {
            player.healthHandler.activatedShields(player.healthHandler.maxShields/2.0);
        }
    }
}