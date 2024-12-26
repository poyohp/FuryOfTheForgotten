package Handlers;

import Entities.Enemies.Enemy;
import Entities.Enemies.EternalSnail;
import Entities.Enemies.InstantKill;
import Entities.Players.Player;
import Handlers.Attacks.AttackHandler;
import Handlers.HUD.InventoryHandler;
import Handlers.Spawners.SpawnHandler;
import Handlers.Spawners.SpawnPoint;
import Objects.Chest;
import Objects.Object;
import World.Level;
import Handlers.Attacks.DamageDealer;
import System.Main;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelHandler {

    // Holds all levels and level data
    private final Level[] levels;
    private Level currentLevel;
    int currentLevelIndex;

    int numLevels;

    //FOR COIN DRAWING
    BufferedImage coinImage = ImageHandler.loadImage("Assets/Objects/coins.png");
    private final int coinDrawSize = Tile.tileSize*Tile.tileMultipler/4;

    /**
     * Constructor for LevelHandler - initializes all levels and enemies
     * @param numLevels number of levels for the entire game
     * @param spawnHandler handles the initial enemy spawn point creation
     * @param player the game player
     */
    public LevelHandler(int numLevels, SpawnHandler spawnHandler, Player player) {
        this.numLevels = numLevels;
        levels = new Level[numLevels];

        addLevels();

        currentLevelIndex = 0;
        currentLevel = levels[currentLevelIndex];

        spawnHandler.levelChanged(player, currentLevel);

    }

    // THE FOLLOWING TWO METHODS WILL BE USED WHEN MULTIPLE LEVELS ARE IMPLEMENTED

    /**
     * Go to the next level and update accordingly
     * @param spawnHandler to update spawns in the new level
     * @param player the game player
     */
    public void goToNextLevel(SpawnHandler spawnHandler, Player player, AttackHandler attackHandler) {
        if(currentLevelIndex < numLevels - 1 ) {
            currentLevelIndex++;
            currentLevel = levels[currentLevelIndex];
            attackHandler.levelChanged(currentLevel);
            spawnHandler.levelChanged(player, currentLevel);
        }
    }

    /**
     * Go to the previous level and update accordingly
     * @param spawnHandler to update spawns in the level
     * @param player the game player
     */
    public void goToPreviousLevel(SpawnHandler spawnHandler, Player player) {
        if(currentLevelIndex > 0) {
            spawnHandler.levelChanged(player, currentLevel);
            currentLevelIndex--;
        }
    }

    /**
     * Update the current level - spawns and enemies
     * @param player
     * @param spawnHandler
     * @param damageDealer
     */
    public void update(Player player, SpawnHandler spawnHandler, DamageDealer damageDealer, CollisionHandler collisionHandler, InventoryHandler inventoryHandler, AttackHandler attackHandler, InstantKill ghost, EternalSnail snail) {

        //CHECKING LEVEL
        currentLevel = levels[currentLevelIndex];

        //OBJECT HANDLING
        playerChestCollision(collisionHandler, player);
        playerObjectInteract(collisionHandler, player, inventoryHandler);

        //SNAIL UPDATING
        checkForDeath(player, collisionHandler, ghost, snail);

        //LEVEL UPDATING
        currentLevel.update(player);

        // ENEMY HANDLING
        updateEnemies(player, damageDealer);
        removeEnemies();
        currentLevel.enemies.removeAll(currentLevel.enemiesToRemove);

        // SPAWN HANDLING
        spawnHandler.update(player, currentLevel);
        handleSpawns(spawnHandler, player, attackHandler); //THIS ALSO HANDLES ATTACK REMOVAL WHEN LEVEL CHANGED!

    }

    public void checkForDeath(Player player, CollisionHandler collisionHandler, InstantKill ghost, EternalSnail snail) {
        if(player.getHealth() <= 0) {
            Main.updateGameState(3);
        }
        if(collisionHandler.enemyPlayerCollision(ghost, player) || collisionHandler.enemyPlayerCollision(snail, player) ) {
            Main.updateGameState(3);
        }
    }

    /**
     * Update the enemies in the current level - also checks to see if the player has died
     * @param player the game player
     * @param damageDealer to deal damage to the player
     */
    public void updateEnemies(Player player, DamageDealer damageDealer) {
        for (Enemy enemy : currentLevel.enemies) {
            enemy.update();
            damageDealer.dealDamageToPlayer(enemy, player);
        }
    }

    /**
     * Handle the spawns in the current level
     * Checks to determine initial number of active spawns as well
     * @param spawnHandler to handle the spawns
     */
    public void handleSpawns(SpawnHandler spawnHandler, Player player, AttackHandler attackHandler) {
        if (spawnHandler.started) { // Timer has started
            boolean spawnPointsActive = false; // Checks if there are any active spawn points
            spawnHandler.numActiveSpawns = 0;
            for (SpawnPoint spawnPoint: spawnHandler.enemySpawnPoints) {
                if (spawnPoint.activeSpawn) {
                    spawnPointsActive = true;
                    spawnHandler.numActiveSpawns++;
                }
            }

            // If there are no more active spawn points, player has defeated all enemies!
            if (!spawnPointsActive && currentLevel.enemies.isEmpty()) goToNextLevel(spawnHandler, player, attackHandler);
        }
    }

    /**
     * Remove enemies from the current level if they die (health < 0)
     */
    public void removeEnemies() {

        // Removes enemies if they die
        for (Enemy enemy: currentLevel.enemies) {
            if(enemy.getHealth() <= 0) {
                System.out.println("THIS ENEMY!");
                System.out.println(enemy.freezeTimer);
            }
            if (enemy.getHealth() <= 0 && enemy.freezeTimer <= 0) currentLevel.enemiesToRemove.add(enemy);
        }
        currentLevel.enemies.removeAll(currentLevel.enemiesToRemove);
    }

    //checks for chest openings, and also for object collisions (if player picks up or not)
    public void playerObjectInteract(CollisionHandler collisionHandler, Player player, InventoryHandler inventoryHandler) {
        for(Chest chest: currentLevel.chests) {
            if(collisionHandler.checkPlayerWithObjectCollision(player, chest)) {
                if(!chest.isOpen) {
                    if(player.keyHandler.choicePress) {
                        player.keyHandler.choicePress = false;
                        chest.isInteracted(player, currentLevel);
                    }
                }
            }
        }

        for(Object object : currentLevel.objects) {
            if(collisionHandler.checkPlayerWithObjectCollision(player, object)) {
                if(!object.isPickedUp) {
                    if(player.keyHandler.choicePress) {
                        player.keyHandler.choicePress = false;
                        object.isPickedUp = true;
                        object.isInteracted(player, currentLevel);
                        if(object.isEquippable && inventoryHandler.indexFree > 0) {
                            inventoryHandler.inventory[inventoryHandler.indexFree] = object;
                        } else {
                            //REPLACE ITEM IN CURRENT INDEX!
                        }
                    }
                }
            }
        }
    }

    //player-chest collision
    public void playerChestCollision(CollisionHandler collisionHandler, Player player) {
        boolean collisionOccurs = false;
        for(Chest chest : currentLevel.chests) {
            if(collisionHandler.checkChestWithEntityCollision(player, chest)) collisionOccurs = true;
        }
        player.collisionWithChest = collisionOccurs;
    }

    /**
     * Draw the current level, and all enemies in the level
     * WILL DRAW MORE OBJECTS LATER
     * @param g2
     * @param player
     */
    public void draw(Graphics2D g2, Player player) {
        this.getCurrentLevel().drawLevel(g2, player);

        for (Enemy enemy : currentLevel.enemies) {
            enemy.draw(g2);
        }

        //COIN DRAWING
        g2.drawImage(coinImage, Tile.tileSize/2, Tile.tileSize/2, Tile.tileSize/2 + coinDrawSize, Tile.tileSize/2 + coinDrawSize, 16*6, 16, 16*7, 32, null);
        g2.setColor(Color.MAGENTA);
        g2.drawString(String.valueOf(player.coinValue), Tile.tileSize/2 + coinDrawSize, coinDrawSize);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Used to add ALL levels to the list of levels
     */
    private void addLevels() {
        levels[0] = new Level("Assets/Maps/Level1Map.json", "Assets/Tilesets/universalTileset.png", nonWalkableValues1(), 16);
        levels[1] = new Level("Maps/Level2Map.json", "Assets/Tilesets/dungeonTileset.png", nonWalkableValues2(), 16);

    }

    /**
     * Add all non-walkable tile values to the nonWalkableValues ArrayList
     */
    private ArrayList<Integer> nonWalkableValues1() {
        ArrayList<Integer> nonWalkableValues = new ArrayList<>();
        int[] values = {201, 202, 203, 229, 230, 232, 257, 258, 260, 285, 286, 287, 288};

        for (int value: values) {
            nonWalkableValues.add(value);
        }
        return nonWalkableValues;
    }

    /**
     * Add all non-walkable tile values to the nonWalkableValues ArrayList
     */
    private ArrayList<Integer> nonWalkableValues2() {
        ArrayList<Integer> nonWalkableValues = new ArrayList<>();
        int[] values = {30, 31, 32, 33, 34, 35, 58, 60, 82, 83, 84, 85, 108, 109, 110, 134, 158};

        for (int value: values) {
            nonWalkableValues.add(value);
        }
        return nonWalkableValues;
    }
}
