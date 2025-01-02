package Objects.UsableObjects.Food;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;

public class Cherry extends UsableObject {

    public Cherry(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/food.png");
    }

    @Override
    public void getImageCoords() {
        imageX = 16;
        imageY = 16;
    }

    @Override
    public void isUsed(Player player) {
        player.healthHandler.maxHearts += 1;
    }
}