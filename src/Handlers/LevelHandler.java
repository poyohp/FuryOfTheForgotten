package Handlers;

import Entities.Enemies.Enemy;
import Entities.Enemies.EternalSnail;
import Entities.Enemies.InstantKill;
import Entities.Players.Player;
import Handlers.Attacks.AttackHandler;
import Handlers.HUD.InventoryHandler;
import Handlers.Spawners.SpawnHandler;
import Handlers.Spawners.SpawnPoint;
import System.Panels.GamePanel;
import World.Level;
import Handlers.Attacks.DamageDealer;
import System.Main;

import java.awt.*;
import java.util.ArrayList;

public class LevelHandler {

    // Holds all levels and level data
    private final Level[] levels;
    private Level currentLevel;
    int currentLevelIndex;

    public boolean levelComplete = false;

    int numLevels;

    Font spawnsRemaining = new Font("Arial", Font.PLAIN, 20);

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
    public void goToNextLevel(SpawnHandler spawnHandler, Player player, AttackHandler attackHandler, InventoryHandler inventoryHandler) {
        if(currentLevelIndex < numLevels - 1 ) {

            //CLEAR OUT UNKILLABLE ENEMIES
            this.currentLevel.unkillableEnemies.clear();

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
    public void update(Player player, SpawnHandler spawnHandler, DamageDealer damageDealer, CollisionHandler collisionHandler, AttackHandler attackHandler, InstantKill ghost, EternalSnail snail) {

        //CHECKING LEVEL
        currentLevel = levels[currentLevelIndex];

        //SNAIL UPDATING
        checkForDeath(player, collisionHandler, ghost, snail);

        //LEVEL UPDATING
        currentLevel.update(player);

        // ENEMY HANDLING
        updateEnemies(player, damageDealer);
        removeEnemies();
        currentLevel.contactEnemies.removeAll(currentLevel.enemiesToRemove);
        currentLevel.archerEnemies.removeAll(currentLevel.archerEnemiesToRemove);

        // SPAWN HANDLING
        spawnHandler.update(player, currentLevel);
        handleSpawns(spawnHandler, player);

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
        for (Enemy enemy : currentLevel.contactEnemies) {
            enemy.update();
            damageDealer.dealDamageToPlayer(enemy, player);
        }

        for (Enemy enemy: currentLevel.unkillableEnemies) {
            enemy.update();
            damageDealer.dealDamageToPlayer(enemy, player);
        }

        for (Enemy enemy: currentLevel.archerEnemies) enemy.update();
    }

    /**
     * Handle the spawns in the current level
     * Checks to determine initial number of active spawns as well
     * @param spawnHandler to handle the spawns
     */
    public void handleSpawns(SpawnHandler spawnHandler, Player player) {
        if (spawnHandler.started) { // Timer has started
            boolean spawnPointsActive = false; // Checks if there are any active spawn points
            spawnHandler.numActiveSpawns = 0;
            for (SpawnPoint spawnPoint: spawnHandler.enemySpawnPoints) {
                if (spawnPoint.activeSpawn) {
                    spawnPointsActive = true;
                    spawnHandler.numActiveSpawns++;
                }
            }

            if (!spawnPointsActive && currentLevel.contactEnemies.isEmpty() && currentLevel.archerEnemies.isEmpty()) {
                currentLevel.doorUnlockable = true;
                if(!levelComplete && currentLevel.doorUnlocked) {
                    levelComplete = true;
                    Main.gamePanel.pauseGame();
                    Main.updateGameState(7);
                }
            }
        }
    }

    /**
     * Remove enemies from the current level if they die (health < 0)
     */
    public void removeEnemies() {

        // Removes enemies if they die
        for (Enemy enemy: currentLevel.contactEnemies) {
            if (enemy.getHealth() <= 0 && enemy.freezeTimer <= 0) currentLevel.enemiesToRemove.add(enemy);
        }
        currentLevel.contactEnemies.removeAll(currentLevel.enemiesToRemove);

        for (Enemy enemy: currentLevel.archerEnemies) {
            if (enemy.getHealth() <= 0 && enemy.freezeTimer <= 0) currentLevel.archerEnemiesToRemove.add(enemy);
        }
        currentLevel.archerEnemies.removeAll(currentLevel.enemiesToRemove);
    }

    /**
     * Draw the current level, and all enemies in the level
     * WILL DRAW MORE OBJECTS LATER
     * @param g2
     * @param player
     */
    public void draw(Graphics2D g2, Player player, SpawnHandler spawnHandler, KeyHandler keyHandler) {
        this.getCurrentLevel().drawLevel(g2, player);

        g2.setFont(spawnsRemaining);
        g2.setColor(Color.GREEN);
        if(keyHandler.toggleInventory) {
            g2.drawString("Active Spawns Remaining: " + spawnHandler.numActiveSpawns, (int)(GamePanel.screenWidth - 300), (100));
            int totalEnemies = currentLevel.contactEnemies.size() + currentLevel.archerEnemies.size();
            g2.drawString("Enemies Remaining: " + totalEnemies, (int)(GamePanel.screenWidth - 300), (150));
        }

        for (Enemy enemy : currentLevel.contactEnemies) {
            enemy.draw(g2);
        }

        for (Enemy enemy : currentLevel.unkillableEnemies) {
            enemy.draw(g2);
        }

        for (Enemy enemy : currentLevel.archerEnemies) {
            enemy.draw(g2);
        }

    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Used to add ALL levels to the list of levels
     */
    private void addLevels() {
        levels[0] = new Level("Assets/Maps/Level1Map.json", "Assets/Tilesets/universalTileset.png", nonWalkableValues1(), 16, 0);
        levels[1] = new Level("Maps/Level2Map.json", "Assets/Tilesets/dungeonTileset.png", nonWalkableValues2(), 16, 1);
        levels[2] = new Level("Maps/Level3Map.json", "Assets/Tilesets/facilityTileset.png", nonWalkableValues3(), 16, 2);
        levels[3] = new Level("Maps/Level4Map.json", "Assets/Tilesets/catacombTileset.png", nonWalkableValues4(), 16, 3);
        levels[4] = new Level("Maps/Level5Map.json", "Assets/Tilesets/cuteTileset.png", nonWalkableValues5(), 16, 4);
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

    /**
     * Add all non-walkable tile values to the nonWalkableValues ArrayList
     */
    private ArrayList<Integer> nonWalkableValues3() {
        ArrayList<Integer> nonWalkableValues = new ArrayList<>();
        int[] values = {114};

        for (int value: values) {
            nonWalkableValues.add(value);
        }
        return nonWalkableValues;
    }

    /**
     * Add all non-walkable tile values to the nonWalkableValues ArrayList
     */
    private ArrayList<Integer> nonWalkableValues4() {
        ArrayList<Integer> nonWalkableValues = new ArrayList<>();
        int[] values = {454, 518, 56};

        for (int value: values) {
            nonWalkableValues.add(value);
        }
        return nonWalkableValues;
    }

    /**
     * Add all non-walkable tile values to the nonWalkableValues ArrayList
     */
    private ArrayList<Integer> nonWalkableValues5() {
        ArrayList<Integer> nonWalkableValues = new ArrayList<>();
        nonWalkableValues.add(92);
        return nonWalkableValues;
    }





}
