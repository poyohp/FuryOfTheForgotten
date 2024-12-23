package World;

import Entities.Enemies.Enemy;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private Map map;

    // Holds all enemy data in the current level
    public ArrayList<Enemy> enemies = new ArrayList<>();
    public ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

    /**
     * Constructor for Level (generates map)
     * @param mapDirectory directory of map file
     */
    public Level(String mapDirectory, String tileSetDirectory, ArrayList<Integer> nonWalkableValues, int numTilesHeight, int numTilesWidth) {

        map = new Map(mapDirectory, tileSetDirectory, numTilesHeight, numTilesWidth);
        map.nonWalkableValues = nonWalkableValues;

        try {
            map.getMap();
        } catch (IOException | ParseException e) {
            System.out.println("Error reading map file");
        }
    }

    /**
     * Get map
     * @return the map
     */
    public Map getMap() {
        return map;
    }

}
