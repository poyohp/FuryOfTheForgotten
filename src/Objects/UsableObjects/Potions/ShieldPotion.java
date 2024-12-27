package Objects.UsableObjects.Potions;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;
import System.Panels.GamePanel;

public class ShieldPotion extends UsableObject {

    public ShieldPotion(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/potions.png");
    }

    @Override
    public void getImageCoords() {
        imageX = 16*5;
        imageY = 32*GamePanel.random.nextInt(7);
    }

    @Override
    public void isUsed(Player player) {
        //TO BE CODED!
    }
}
