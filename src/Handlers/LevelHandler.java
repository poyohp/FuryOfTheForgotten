package Handlers;

import Entities.Enemy;
import Entities.Player;
import Handlers.Spawners.SpawnHandler;
import World.Level;
import Attacks.DamageDealer;

import java.awt.*;

public class LevelHandler {

    private Level[] levels;
    private Level currentLevel;
    int currentLevelIndex;

    DamageDealer damageDealer = new DamageDealer();

    int numLevels;

    public LevelHandler(int numLevels, SpawnHandler spawnHandler, Player player) {
        this.numLevels = numLevels;
        levels = new Level[numLevels];

        addLevels();

        currentLevelIndex = 0;
        currentLevel = levels[currentLevelIndex];

        spawnHandler.levelChanged(player, currentLevel);

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

        for (Enemy enemy : currentLevel.enemies) {
            enemy.update();
            damageDealer.dealDamageToPlayer(enemy, player);
            System.out.println(CollisionHandler.enemyPlayerCollision(enemy, player));
        }

    }

    public void draw(Graphics2D g2, Player player) {
        this.getCurrentLevel().getMap().drawMap(g2, player);

        for (Enemy enemy : currentLevel.enemies) {
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
