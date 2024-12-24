package Objects;

import System.Panels.GamePanel;
import Entities.Hitbox;
import Entities.Players.Player;
import Handlers.ImageHandler;
import World.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Chest extends Object {

    private final int imageWidth = 16;

    //Sizes (1 - big or 0 - small)
    public int chestSize;

    //Rarity (0-3) ... 4 total types, 0 is least rare, 3 is most rare
    public int rarity;

    public boolean isOpen = false;

    public ArrayList<Object> itemsContained = new ArrayList<>();

    public Chest(String name, int width, int height, double worldX, double worldY, double screenX, double screenY) {
        super(name, width, height, worldX, worldY, screenX, screenY);
        determineSizeRarity();
        determineImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/chests&keys.png");

        fillItemsContained();

    }

    public void update(Player player) {
        setScreenPosition(player);
    }

    public void chestOpened() {
        isOpen = true;
        imageX += imageWidth;

        //DROP DROP DROP DROP DPOP STUFF!

    }

    private void fillItemsContained() {
        //FILL ITEMS CONTAINED!
    }

    private void determineSizeRarity() {
        chestSize = GamePanel.random.nextInt(2);
        int rarityGen = GamePanel.random.nextInt(10);
        if(rarityGen < 4) {
            rarity = 0;
        } else if (rarityGen < 7) {
            rarity = 1;
        } else if (rarityGen < 9) {
            rarity = 2;
        } else rarity = 3;
    }

    private void determineImageCoords() {
        switch(rarity) {
            case 0:
                //lowest rarity (brown)
                imageX = imageWidth*4;
                imageY = 0;
                break;
            case 1:
                //second lower (blue)
                imageX = imageWidth*6;
                imageY = imageWidth;
                break;
            case 2:
                // mid (purple)
                imageX = imageWidth*2;
                imageY = imageWidth;
                break;
            case 3:
                // high (pink)
                imageX = imageWidth*2;
                imageY = imageWidth;
                break;
            default:
                imageX = 0;
                imageY = 0;
                break;
        }

        if(chestSize == 0) {
            imageY += imageWidth*2;
        }
        System.out.println(imageX + " " + imageY);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, (int) screenX, (int) screenY, (int) screenX+width, (int) screenY+height, imageX, imageY, imageX+imageWidth, imageY+imageWidth, null);
    }

}
