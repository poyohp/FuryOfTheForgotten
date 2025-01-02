package Objects.UsableObjects;

import Entities.Players.Player;
import Handlers.ImageHandler;

public class Key extends UsableObject {

    public Key(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/chests&keys.png");

    }

    @Override
    public void getImageCoords() {
        imageX = 16*4;
        imageY = 16*4;
    }

    @Override
    public void isUsed(Player player) {
        //TO BE CODED!
    }

}
