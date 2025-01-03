package World;

import Entities.Enemies.Enemy;
import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.UnusableObjects.Chest;
import Objects.Object;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private final Map map;

    // Holds all enemy data in the current level
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
    public ArrayList<Chest> chests = new ArrayList<>();

    public ArrayList<Object> objects = new ArrayList<>();
    public ArrayList<Object> objectsToRemove = new ArrayList<>();

    public int levelNum;

    public boolean doorUnlockable;
    public boolean doorUnlocked;

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
    }

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

    public void drawItems(Graphics2D g2) {
        for(Chest chest: chests) {
            chest.draw(g2);
        }
        for(Object object: objects) {
            object.draw(g2);
        }
    }

}
