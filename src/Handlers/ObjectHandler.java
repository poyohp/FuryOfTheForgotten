package Handlers;

import Entities.Players.Player;
import Handlers.HUD.InventoryHandler;
import Objects.Object;
import Objects.UnusableObjects.*;
import Objects.UsableObjects.Potions.*;
import Objects.UsableObjects.Medicine.*;
import Objects.UsableObjects.Food.*;
import System.Panels.GamePanel;
import World.Level;
import World.Tile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Timer;

public class ObjectHandler {

    static ArrayList<String> allGameItems = new ArrayList<>();
    Object closestMarked;

    Timer timer;
    private final double secondsBetweenPickup = 0.70;
    private boolean canPickUp;

    //FOR COIN DRAWING
    BufferedImage coinImage = ImageHandler.loadImage("Assets/Objects/coins.png");
    private final int coinDrawSize = (int) (Tile.tileSize*Tile.tileMultipler/4.0);
    Font coinFont = new Font("Arial", Font.PLAIN, 50);

    public ObjectHandler() {
        addAllObjects();

        closestMarked = null;

        canPickUp = true;

        //PAUSE BETWEEN PICKING UP DIFFERENT ITEMS
        //AVOID PLAYER MOVING ITEMS AS THEY MOVE AND PICK ITEMS UP
        timer = new Timer((int) (1000.0*secondsBetweenPickup), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canPickUp = true;
                timer.stop();
            }
        });

    }

    /**
     * ALL DROPPABLE ITEMS
     * DOESN'T INCLUDE KEYS OR CHERRY
     */
    private void addAllObjects() {
        //POTIONS
        allGameItems.add("DamagePotion");
        allGameItems.add("HealthPotion");
        allGameItems.add("SpeedPotion");
        allGameItems.add("ShieldPotion");

        //MEDICINE
        allGameItems.add("Bandage");
        allGameItems.add("Pills");

        //FOOD
        allGameItems.add("Cheese");
        allGameItems.add("Cherry");
        allGameItems.add("Garlic");
        allGameItems.add("Milk");
        allGameItems.add("Mushroom");
        allGameItems.add("Onion");
        allGameItems.add("Strawberry");

    }

    public void update(CollisionHandler collisionHandler, Player player, InventoryHandler inventoryHandler, Level level) {
        markClosestObject(collisionHandler, level, player);
        playerChestCollision(collisionHandler, player, level);
        playerObjectInteract(collisionHandler, player, inventoryHandler, level);
    }

    /**
     * Marks the closest object to the player
     * @param collisionHandler the collision handler for the game
     * @param level the level the player is in
     * @param player the player
     */
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

    //Checks for chest openings, and also for object collisions (if player picks up or not)
    public void playerObjectInteract(CollisionHandler collisionHandler, Player player, InventoryHandler inventoryHandler, Level level) {
        for(Chest chest: level.chests) {
            if(!player.keyHandler.toggleInventory && collisionHandler.checkPlayerWithObjectCollision(player, chest)) {
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
            if(!player.keyHandler.toggleInventory && canPickUp && collisionHandler.checkPlayerWithObjectCollision(player, object) && object == closestMarked) {
                if(!object.isPickedUp) {
                    if(player.keyHandler.choicePress) {
                        player.keyHandler.choicePress = false;
                        canPickUp = false;
                        if (!timer.isRunning()) timer.start();
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

        //Replace object if player needs to replcae
        if(replaceObject) {
            inventoryHandler.inventory[inventoryHandler.indexSelected].isDropped(player, level);
            inventoryHandler.inventory[inventoryHandler.indexSelected] = objectToReplace;
        }

    }

    //Player-chest collision
    public void playerChestCollision(CollisionHandler collisionHandler, Player player, Level level) {
        boolean collisionOccurs = false;
        for(Chest chest : level.chests) {
            if(collisionHandler.checkChestWithEntityCollision(player, chest)) collisionOccurs = true;
        }
        player.collisionWithChest = collisionOccurs;
    }

    /**
     * Get a random object
     * @param width the width
     * @param height the height
     * @param worldX x value in the map
     * @param worldY y value in the map
     * @param screenX screen x position
     * @param screenY screen y position
     * @param vx velocity in X direction
     * @param forShop determine if it's an object for shop (then we can also return cherry)
     * @return a random object
     */
    public static Object getRandomObject(double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, boolean forShop) {
        int index = GamePanel.random.nextInt(allGameItems.size());
        double vy;
        if(forShop) vy = 0;
        else vy = Tile.tileRatio*0.25;
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
            case "Bandage" ->
                    new Bandage(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "Pills" ->
                    new Pills(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "Cheese" ->
                    new Cheese(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "Garlic" ->
                    new Garlic(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "Milk" ->
                    new Milk(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "Mushroom" ->
                    new Mushroom(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "Onion" ->
                    new Onion(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "Strawberry" ->
                    new Strawberry(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
            case "Cherry" -> {
                if(forShop) yield new Cherry(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
                else {
                    int num = GamePanel.random.nextInt(3);
                    if(num == 0) {
                        yield new Garlic(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
                    } else if (num == 1) {
                        yield new ShieldPotion(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
                    } else {
                        yield new Onion(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
                    }
                }
            }
            default -> new Coin(0, "Coin", Tile.tileSize, Tile.tileSize, worldX, worldY, screenX, screenY, vx, vy);
        };
    }

    /**
     * Determines the most used item in the game
     * @return the most used item's name as a String
     */
    public static String determineMostUsed() {
        String mostUsedItem = null;
        int maxUsage = -1;

        for (String item : allGameItems) {
            int timesUsed = getItemUsage(item);

            if (timesUsed > maxUsage) {
                maxUsage = timesUsed;
                mostUsedItem = item + " | Usage: " + timesUsed;
            }
        }

        if(maxUsage == 0) {
            return "NO ITEMS WERE USED";
        }

        return mostUsedItem;
    }

    /**
     * Determines the number of usages of a certain item
     * @param itemName the item's name
     * @return the number of usages, if valid. otherwise 0
     */
    private static int getItemUsage(String itemName) {
        return switch (itemName) {
            case "DamagePotion" -> DamagePotion.timesUsed;
            case "HealthPotion" -> HealthPotion.timesUsed;
            case "SpeedPotion" -> SpeedPotion.timesUsed;
            case "ShieldPotion" -> ShieldPotion.timesUsed;
            case "Bandage" -> Bandage.timesUsed;
            case "Pills" -> Pills.timesUsed;
            case "Cheese" -> Cheese.timesUsed;
            case "Cherry" -> Cherry.timesUsed;
            case "Garlic" -> Garlic.timesUsed;
            case "Milk" -> Milk.timesUsed;
            case "Mushroom" -> Mushroom.timesUsed;
            case "Onion" -> Onion.timesUsed;
            case "Strawberry" -> Strawberry.timesUsed;
            default -> 0;
        };
    }

    /**
     * Draws
     * @param g2 the graphics component
     * @param player the player
     * @param keyHandler the keyhandler
     */
    public void draw(Graphics2D g2, Player player, KeyHandler keyHandler) {
        //COIN DRAWING (if in inventory)
        if(keyHandler.toggleInventory) {
            drawCoin(g2, player);
        }
    }

    /**
     * Draws the player's money value (+coin)
     * @param g2 the graphics component
     * @param player the player
     */
    public void drawCoin(Graphics2D g2, Player player) {
        //COIN DRAWING
        g2.setFont(coinFont);
        g2.drawImage(coinImage, Tile.tileSize/3, Tile.tileSize/3, Tile.tileSize/3 + coinDrawSize, Tile.tileSize/3 + (int) coinDrawSize, 16*6, 16, 16*7, 32, null);
        g2.setColor(Color.MAGENTA);
        g2.drawString(String.valueOf(player.coinValue), Tile.tileSize/3 + coinDrawSize, coinDrawSize - 20);
    }

}
