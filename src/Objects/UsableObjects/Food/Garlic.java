package Objects.UsableObjects.Food;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;

public class Garlic extends UsableObject {

    public Garlic(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/food.png");
    }

    @Override
    public void getImageCoords() {
        imageX = 16*3;
        imageY = 16*2;
    }

    @Override
    public void isUsed(Player player) {
        player.healthHandler.isHit(1);
        player.damageBoostUsed();

        if(player.getName().equalsIgnoreCase("Vampire")) {
            //KILL PLAYER IMMEDIATELY!
            player.healthHandler.currentHearts = -10;
        }
    }
}