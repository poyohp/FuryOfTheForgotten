package World;

import Entities.Enemies.Enemy;
import Entities.Players.Player;
import Objects.Chest;
import Objects.Object;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private Map map;

    // Holds all enemy data in the current level
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
    public ArrayList<Chest> chests = new ArrayList<>();

    public ArrayList<Object> objects = new ArrayList<>();
    public ArrayList<Object> objectsToRemove = new ArrayList<>();

    /**
     * Constructor for Level (generates map)
     * @param mapDirectory directory of map file
     */
    public Level(String mapDirectory, String tileSetDirectory, ArrayList<Integer> nonWalkableValues, int tileSetTileSize) {

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

        for(Chest chest: chests) {
            chest.draw(g2);
        }
        for(Object object: objects) {
            object.draw(g2);
        }
    }

}
