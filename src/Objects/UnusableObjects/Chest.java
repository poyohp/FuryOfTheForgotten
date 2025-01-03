package Objects.UnusableObjects;

import Handlers.ObjectHandler;
import Objects.Object;
import Objects.UsableObjects.Key;
import Objects.UsableObjects.Potions.DamagePotion;
import System.Panels.GamePanel;
import Entities.Players.Player;
import Handlers.ImageHandler;
import World.Level;
import World.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Chest extends Objects.Object {

    //Sizes (1 - big or 0 - small)
    public int chestSize;

    //Rarity (0-3) ... 4 total types, 0 is least rare, 3 is most rare
    public int rarity;

    public boolean isOpen = false;

    public ArrayList<Object> itemsContained = new ArrayList<>();

    public Chest(String name, int width, int height, double worldX, double worldY, double screenX, double screenY) {
        super(name, width, height, worldX, worldY, screenX, screenY, 0, 0);
        determineSizeRarity();
        getImageCoords();
        image = ImageHandler.loadImage("Assets/Objects/chests&keys.png");

        fillItemsContained();

        isEquippable = false;

    }

    @Override
    public void isPickedUp(Player player, Level level) {
        isOpen = true;
        imageX += imageWidth;

        //DROP DROP DROP DROP DPOP STUFF!
        level.objects.addAll(itemsContained);
    }

    @Override
    public void isUsed(Player player) {
        //DO NOTHING
        //CHEST CANNOT BE USED
    }

    @Override
    public void isDropped(Player player, Level level) {
        //DO NOTHING
        //CHEST CANNOT BE DROPPED!
    }

    public void addKeyToChest() {
        double centerX = (worldX + width/2) - ((double) Tile.tileSize /2)*0.75;
        double centerY = worldY + height/2;
        if(chestSize == 0) {
            itemsContained.set(0, new Key("Key", Tile.tileSize, Tile.tileSize, centerX, centerY, screenX, screenY, 0, Tile.tileRatio*0.25));
        } else {
            itemsContained.set(2, new Key("Key", Tile.tileSize, Tile.tileSize, centerX, centerY, screenX, screenY, -Tile.tileRatio*0.25, Tile.tileRatio*0.25));
        }
    }

    private void fillItemsContained() {
        double centerX = (worldX + width/2) - ((double) Tile.tileSize /2)*0.75;
        double centerY = worldY + height/2;
        itemsContained.add(new Coin(this.rarity, "Coin", Tile.tileSize, Tile.tileSize, centerX, centerY, screenX, screenY, 0, Tile.tileRatio*0.25));
        if(chestSize == 1) {
            itemsContained.add(ObjectHandler.getRandomObject(Tile.tileSize, Tile.tileSize, centerX, centerY, screenX, screenY, Tile.tileRatio*0.25));
            itemsContained.add(ObjectHandler.getRandomObject(Tile.tileSize, Tile.tileSize, centerX, centerY, screenX, screenY, -Tile.tileRatio*0.25));
        }
    }

    private void determineSizeRarity() {
        if(Math.random() < 0.75) {
            chestSize = 1;
        } else chestSize = 0;
        int rarityGen = GamePanel.random.nextInt(10);
        if(rarityGen < 4) {
            rarity = 0;
        } else if (rarityGen < 7) {
            rarity = 1;
        } else if (rarityGen < 9) {
            rarity = 2;
        } else rarity = 3;
    }

    @Override
    public void getImageCoords() {
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
                imageX = imageWidth*4;
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
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image, (int) screenX, (int) screenY, (int) (screenX+origWidth), (int) (screenY+origHeight), imageX, imageY, imageX+imageWidth, imageY+imageWidth, null);
    }

    @Override
    public void drawHUD(Graphics2D g2, int x, int y, int size) {
        //NEVER DRAW COIN HERE
    }

}
