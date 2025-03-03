package Objects.UsableObjects.Medicine;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;

public class Bandage extends UsableObject {

    public static int timesUsed = 0;

    public Bandage(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/flowers&medicine.png");

        value = 5;

    }

    @Override
    public void getImageCoords() {
        imageX = 4*16;
        imageY = 16;
    }

    @Override
    public void isUsed(Player player) {
        Bandage.timesUsed++;
        player.healthHandler.heal(1);
    }
}
