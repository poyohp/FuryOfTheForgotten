package Objects.UsableObjects.Food;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;
import System.Panels.GamePanel;

public class Strawberry extends UsableObject {

    public static int timesUsed = 0;

    public Strawberry(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/food.png");

        value = 5;

    }

    @Override
    public void getImageCoords() {
        imageX = 16*6;
        imageY = 16;
    }

    @Override
    public void isUsed(Player player) {
        Strawberry.timesUsed++;
        player.healthHandler.heal(0.5);
        if(GamePanel.random.nextInt(100) == 1) {
            player.speedBoostUsed();
            player.damageBoostUsed();
        }
    }
}