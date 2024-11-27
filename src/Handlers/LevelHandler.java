package Handlers;

import Entities.Enemy;
import Entities.Player;
import World.Level;

public class LevelHandler {

    private Level[] levels;
    private Level currentLevel;
    int currentLevelIndex;

    int numLevels;

    public LevelHandler(int numLevels) {
        this.numLevels = numLevels;
        levels = new Level[numLevels];

        addLevels();

        currentLevelIndex = 0;
        currentLevel = levels[currentLevelIndex];

    }

    public void goToNextLevel() {
        if(currentLevelIndex < numLevels - 1 ) {
            currentLevelIndex++;
        }

    }

    public void goToPreviousLevel() {
        if(currentLevelIndex > 0) {
            currentLevelIndex--;
        }
    }

    public void update(CollisionHandler collisionHandler, Player player) {

        for (Enemy enemy : currentLevel.enemies) {
            enemy.update();
        }

        player.isColliding = collisionHandler.playerWithTileCollision(player, currentLevel.getMap().baseLayerTiles);


    }


    public Level getCurrentLevel() {
        return currentLevel;
    }

    private void addLevels() {
        levels[0] = new Level("src/Assets/Maps/Levels/Level1Map.json");
        //ADD MORE LEVELS LATER
    }




}
