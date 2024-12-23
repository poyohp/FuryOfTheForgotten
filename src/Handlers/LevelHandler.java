package Handlers;

import Entities.Enemies.Enemy;
import Entities.Players.Player;
import Handlers.Attacks.AttackHandler;
import Handlers.Spawners.SpawnHandler;
import Handlers.Spawners.SpawnPoint;
import World.Level;
import Handlers.Attacks.DamageDealer;
import System.Main;

import java.awt.*;
import java.util.ArrayList;

public class LevelHandler {

    // Holds all levels and level data
    private Level[] levels;
    private Level currentLevel;
    int currentLevelIndex;

    int numLevels;


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
    public void goToNextLevel(SpawnHandler spawnHandler, Player player) {
        if(currentLevelIndex < numLevels - 1 ) {
            spawnHandler.levelChanged(player, currentLevel);
            currentLevelIndex++;
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
     * @param attackHandler
     * @param damageDealer
     */
    public void update(Player player, SpawnHandler spawnHandler, AttackHandler attackHandler, DamageDealer damageDealer) {

        // ENEMY HANDLING
        updateEnemies(player, damageDealer);
        removeEnemies();
        currentLevel.enemies.removeAll(currentLevel.enemiesToRemove);

        // SPAWN HANDLING
        spawnHandler.update(player, currentLevel);
        handleSpawns(spawnHandler);

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
            if (player.getHealth() <= 0) Main.updateGameState(3);
        }
    }

    /**
     * Handle the spawns in the current level
     * Checks to determine initial number of active spawns as well
     * @param spawnHandler to handle the spawns
     */
    public void handleSpawns(SpawnHandler spawnHandler) {
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
            if (!spawnPointsActive && currentLevel.enemies.isEmpty()) Main.updateGameState(4);
        }
    }

    /**
     * Remove enemies from the current level if they die (health < 0)
     */
    public void removeEnemies() {

        // Removes enemies if they die
        for (Enemy enemy: currentLevel.enemies) {
            if (enemy.getHealth() == 0) currentLevel.enemiesToRemove.add(enemy);
        }
        currentLevel.enemies.removeAll(currentLevel.enemiesToRemove);
    }

    /**
     * Draw the current level, and all enemies in the level
     * WILL DRAW MORE OBJECTS LATER
     * @param g2
     * @param player
     */
    public void draw(Graphics2D g2, Player player) {
        this.getCurrentLevel().getMap().drawMap(g2, player);

        for (Enemy enemy : currentLevel.enemies) {
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
        levels[0] = new Level("Assets/Maps/Level1Map.json", "Assets/Tilesets/universalTileset.png", nonWalkableValues1(), 28, 28);
        //ADD MORE LEVELS LATER
    }

    /**
     * Add all non-walkable tile values to the nonWalkableValues ArrayList
     */
    private ArrayList<Integer> nonWalkableValues1() {
        ArrayList<Integer> nonWalkableValues = new ArrayList<>();
        nonWalkableValues.add(201);
        nonWalkableValues.add(202);
        nonWalkableValues.add(203);
        nonWalkableValues.add(229);
        nonWalkableValues.add(230);
        nonWalkableValues.add(232);
        nonWalkableValues.add(257);
        nonWalkableValues.add(258);
        nonWalkableValues.add(260);
        nonWalkableValues.add(285);
        nonWalkableValues.add(286);
        nonWalkableValues.add(287);
        nonWalkableValues.add(288);
        return nonWalkableValues;
    }


}
