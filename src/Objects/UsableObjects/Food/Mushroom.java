package Objects.UsableObjects.Food;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UsableObjects.UsableObject;
import System.Panels.GamePanel;

public class Mushroom extends UsableObject {

    public Mushroom(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/food.png");

        value = 15;

    }

    @Override
    public void getImageCoords() {
        imageX = 16*5;
        imageY = 16*4;
    }

    @Override
    public void isUsed(Player player) {
        if (GamePanel.random.nextInt(2) == 0) {
            //50 percent chance to activate poison and nothing else!
            player.healthHandler.activatedPoison();
        } else {
            int num = GamePanel.random.nextInt(3);
            int goodEffect = GamePanel.random.nextInt(3);
            int badEffect = GamePanel.random.nextInt(2);
            if(num == 0) {
                //17 percent chance of 2 good effects!
                if(goodEffect > 0) {
                    player.speedBoostUsed();
                    player.healthHandler.activatedShields(player.healthHandler.maxShields/2.0);
                } else {
                    player.healthHandler.activatedShields(player.healthHandler.maxShields/2.0);
                    player.healthHandler.heal(2);
                }
            } else if(num == 1){
                //17 percent chance of just 1 good effect
                switch(goodEffect) {
                    case 0:
                        player.speedBoostUsed();
                        break;
                    case 1:
                        player.damageBoostUsed();
                        break;
                    case 2:
                        player.healthHandler.activatedShields(player.healthHandler.maxShields/2.0);
                        break;
                    default:
                        break;
                }
            } else {
                //17 percent chance of 1 good effect + 1 bad effect
                switch(goodEffect) {
                    case 0:
                        player.damageBoostUsed();
                        break;
                    case 1:
                        player.speedBoostUsed();
                        break;
                    case 2:
                        player.healthHandler.activatedShields(player.healthHandler.maxShields/2.0);
                        break;
                    default:
                        break;
                }

                switch(badEffect) {
                    case 0:
                        player.healthHandler.activatedPoison();
                        break;
                    case 1:
                        player.healthHandler.isHit(2, false);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}