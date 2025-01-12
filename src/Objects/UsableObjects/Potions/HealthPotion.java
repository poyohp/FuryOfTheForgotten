package Objects.UsableObjects.Potions;

import Entities.Players.Player;
import Handlers.HUD.HealthHandler;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;
import System.Panels.GamePanel;

public class HealthPotion extends UsableObject {

    public static int timesUsed = 0;

    public HealthPotion(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/potions.png");
        value = 25;

    }

    @Override
    public void getImageCoords() {
        imageX = 16;
        imageY = 32*GamePanel.random.nextInt(7);
    }

    @Override
    public void isUsed(Player player) {
        HealthPotion.timesUsed++;
        player.healthHandler.activatedEnhanced();
    }
}
