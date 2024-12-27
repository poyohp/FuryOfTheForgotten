package Handlers;

import Entities.Players.Player;
import Handlers.HUD.InventoryHandler;
import Objects.Object;
import Objects.UnusableObjects.Chest;
import Objects.UnusableObjects.Coin;
import Objects.UsableObjects.Potions.DamagePotion;
import Objects.UsableObjects.Potions.HealthPotion;
import Objects.UsableObjects.Potions.ShieldPotion;
import Objects.UsableObjects.Potions.SpeedPotion;
import System.Panels.GamePanel;
import World.Level;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ObjectHandler {

    static ArrayList<String> allGameItems = new ArrayList<>();

    Object closestMarked;

    //FOR COIN DRAWING
    BufferedImage coinImage = ImageHandler.loadImage("Assets/Objects/coins.png");
    private final int coinDrawSize = Tile.tileSize*Tile.tileMultipler/4;

    public ObjectHandler() {
        addAllObjects();

        closestMarked = null;

    }

    private void addAllObjects() {
        allGameItems.add("DamagePotion");
        allGameItems.add("HealthPotion");
        allGameItems.add("SpeedPotion");
        allGameItems.add("ShieldPotion");
    }

    public void update(CollisionHandler collisionHandler, Player player, InventoryHandler inventoryHandler, Level level) {
        markClosestObject(collisionHandler, level, player);
        playerChestCollision(collisionHandler, player, level);
        playerObjectInteract(collisionHandler, player, inventoryHandler, level);
    }

    public void markClosestObject(CollisionHandler collisionHandler, Level level, Player player) {
        if(level.objects.isEmpty()) {
            closestMarked = null;
            return;
        }

        closestMarked = level.objects.getFirst();
        for(int i = 1; i < level.objects.size(); i++) {
            if (collisionHandler.getDistance(player, level.objects.get(i)) < collisionHandler.getDistance(player, closestMarked)) {
                closestMarked = level.objects.get(i);
            }
        }
    }

    //checks for chest openings, and also for object collisions (if player picks up or not)
    public void playerObjectInteract(CollisionHandler collisionHandler, Player player, InventoryHandler inventoryHandler, Level level) {
        for(Chest chest: level.chests) {
            if(collisionHandler.checkPlayerWithObjectCollision(player, chest)) {
                if(!chest.isOpen) {
                    if(player.keyHandler.choicePress) {
                        player.keyHandler.choicePress = false;
                        //PLAYER INTERACTS WITH CHEST (not picked up)
                        chest.isPickedUp(player, level);
                    }
                }
            }
        }

        boolean replaceObject = false;
        Object objectToReplace = null;
        for(Objects.Object object : level.objects) {
            if(collisionHandler.checkPlayerWithObjectCollision(player, object) && object == closestMarked) {
                if(!object.isPickedUp) {
                    if(player.keyHandler.choicePress) {
                        player.keyHandler.choicePress = false;
                        object.isPickedUp(player, level);
                        if(object.isEquippable) {
                            if(inventoryHandler.indexFree >= 0) {
                                // PUT ITEM INTO FREE SLOT
                                inventoryHandler.inventory[inventoryHandler.indexFree] = object;
                            }  else {
                                //REPLACE OBJECT
                                replaceObject = true;
                                objectToReplace = object;
                            }
                        }
                    }
                }
            }
        }

        if(replaceObject) {
            inventoryHandler.inventory[0].isDropped(player, level);
            inventoryHandler.inventory[0] = objectToReplace;
        }

    }

    //player-chest collision
    public void playerChestCollision(CollisionHandler collisionHandler, Player player, Level level) {
        boolean collisionOccurs = false;
        for(Chest chest : level.chests) {
            if(collisionHandler.checkChestWithEntityCollision(player, chest)) collisionOccurs = true;
        }
        player.collisionWithChest = collisionOccurs;
    }

    public static Object getRandomObject(double width, double height, double worldX, double worldY, double screenX, double screenY, double vx) {
        int index = GamePanel.random.nextInt(allGameItems.size());
        double vy = Tile.tileRatio*0.25;
        String name = allGameItems.get(index);
        //thank u intellij for return switch
        return switch (name) {
            case "DamagePotion" ->
                    new DamagePotion(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "HealthPotion" ->
                    new HealthPotion(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "SpeedPotion" ->
                    new SpeedPotion(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "ShieldPotion" ->
                    new ShieldPotion(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            default -> new Coin(0, "Coin", Tile.tileSize, Tile.tileSize, worldX, worldY, screenX, screenY, vx, vy);
        };
    }

    public void draw(Graphics2D g2, Player player) {
        //COIN DRAWING
        g2.drawImage(coinImage, Tile.tileSize/2, Tile.tileSize/2, Tile.tileSize/2 + coinDrawSize, Tile.tileSize/2 + coinDrawSize, 16*6, 16, 16*7, 32, null);
        g2.setColor(Color.MAGENTA);
        g2.drawString(String.valueOf(player.coinValue), Tile.tileSize/2 + coinDrawSize, coinDrawSize);
    }

}