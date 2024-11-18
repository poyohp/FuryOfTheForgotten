package Handlers;

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

    public Level getCurrentLevel() {
        return currentLevel;
    }

    private void addLevels() {
        levels[0] = new Level("src/Assets/Maps/Levels/Level1.json");
        //ADD MORE LEVELS LATER
    }




}
