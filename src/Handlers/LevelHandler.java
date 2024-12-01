package Handlers;

import Entities.Enemy;
import Entities.Player;
import Handlers.Spawners.SpawnHandler;
import Handlers.Spawners.SpawnPoint;
import World.Level;
import Attacks.DamageDealer;
import System.Main;

import java.awt.*;
import java.util.ArrayList;

public class LevelHandler {

    private Level[] levels;
    private Level currentLevel;
    int currentLevelIndex;

    DamageDealer damageDealer = new DamageDealer();

    int numLevels;

    public ArrayList<Enemy> enemies;

    public LevelHandler(int numLevels, SpawnHandler spawnHandler, Player player) {
        this.numLevels = numLevels;
        levels = new Level[numLevels];

        addLevels();

        currentLevelIndex = 0;
        currentLevel = levels[currentLevelIndex];

        spawnHandler.levelChanged(player, currentLevel);

        enemies = currentLevel.enemies;
    }

    public void goToNextLevel(SpawnHandler spawnHandler, Player player) {
        if(currentLevelIndex < numLevels - 1 ) {
            spawnHandler.levelChanged(player, currentLevel);
            currentLevelIndex++;
        }
    }

    public void goToPreviousLevel() {
        if(currentLevelIndex > 0) {
            currentLevelIndex--;
        }
    }

    public void update(Player player, SpawnHandler spawnHandler) {

        spawnHandler.update(player, currentLevel);

        for (Enemy enemy : enemies) {
            enemy.update();
            damageDealer.dealDamageToPlayer(enemy, player);
            if (player.getHealth() <= 0) Main.updateGameState(3);
        }

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
            if (!spawnPointsActive) Main.updateGameState(4);
        }
    }

    public void draw(Graphics2D g2, Player player) {
        this.getCurrentLevel().getMap().drawMap(g2, player);

        for (Enemy enemy : enemies) {
            enemy.draw(g2);
        }

    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    private void addLevels() {
        levels[0] = new Level("src/Assets/Maps/Levels/Level1Map.json");
        //ADD MORE LEVELS LATER
    }




}
