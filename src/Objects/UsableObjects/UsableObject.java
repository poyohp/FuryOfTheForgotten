package Objects.UsableObjects;

import Entities.Players.Player;
import Objects.Object;
import System.Panels.GamePanel;
import World.Level;
import World.Tile;

import java.awt.*;

public abstract class UsableObject extends Object {

    public UsableObject(String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
    }

    @Override
    public void isPickedUp(Player player, Level level) {
        isPickedUp = true;
        level.objectsToRemove.add(this);
    }

    @Override
    public void isDropped(Player player, Level level) {

        isPickedUp = false;

        //GIVE VELOCITY
        vy = Tile.tileRatio*0.25;
        int direction = GamePanel.random.nextInt(-1, 2);
        switch(direction) {
            case -1:
                vx = -Tile.tileRatio*0.25;
                break;
            case 1:
                vx = Tile.tileRatio*0.25;
                break;
            default:
                vx = 0;
                break;
        }

        worldX = player.worldX;
        worldY = player.worldY;

        System.out.println("Dropped: " + name);
        level.objects.add(this);

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, (int) screenX, (int) screenY, (int) (screenX+width), (int) (screenY+height), imageX, imageY, imageX+imageWidth, imageY+imageWidth, null);
    }

    @Override
    public void drawHUD(Graphics2D g2, int x, int y, int size) {
        g2.drawImage(image, x, y, x + size, y + size, imageX, imageY, imageX+imageWidth, imageY+imageWidth, null);
    }

}
