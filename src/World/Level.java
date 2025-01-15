package World;

import Entities.Enemies.Enemy;
import Entities.Players.Player;
import Objects.UnusableObjects.Chest;
import Objects.Object;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private final Map map;

    // Holds all enemy data in the current level
    public ArrayList<Enemy> contactEnemies = new ArrayList<>();
    public ArrayList<Enemy> unkillableEnemies = new ArrayList<>();
    public ArrayList<Enemy> archerEnemies = new ArrayList<>();
    public ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
    public ArrayList<Enemy> archerEnemiesToRemove = new ArrayList<>();
    public ArrayList<Chest> chests = new ArrayList<>();

    public ArrayList<Object> objects = new ArrayList<>();
    public ArrayList<Object> objectsToRemove = new ArrayList<>();

    public int levelNum;

    public boolean doorUnlockable;
    public boolean doorUnlocked;
    public boolean lastLevel;

    /**
     * Constructor for Level (generates map)
     * @param mapDirectory directory of map file
     */
    public Level(String mapDirectory, String tileSetDirectory, ArrayList<Integer> nonWalkableValues, int tileSetTileSize, int levelNum) {

        doorUnlockable = false;
        doorUnlocked = false;

        this.levelNum = levelNum;

        map = new Map(mapDirectory, tileSetDirectory, tileSetTileSize, this);
        map.nonWalkableValues = nonWalkableValues;

        try {
            map.getMap();
        } catch (IOException | ParseException e) {
            System.out.println("Error reading map file");
        }

        lastLevel = false;
    }

    /**
     * Update chests objects
     * Removes objects if needed
     * @param player the game's player
     */
    public void update(Player player) {
        for(Chest chest: chests) {
            chest.update(player);
        }

        for(Object object: objects) {
            object.update(player);
        }

        objects.removeAll(objectsToRemove);
        objectsToRemove.clear();
    }

    /**
     * Get map
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    public void drawLevel(Graphics2D g2, Player player) {
        map.drawMap(g2, player);
    }

    /**
     * Draw all items stored in the level
     * @param g2
     */
    public void drawItems(Graphics2D g2) {
        for(Chest chest: chests) {
            chest.draw(g2);
        }
        for(Object object: objects) {
            object.draw(g2);
        }
    }

}
