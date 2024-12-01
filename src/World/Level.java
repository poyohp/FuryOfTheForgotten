package World;

import Attacks.Attack;
import Entities.Enemy;
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
    public Level(String mapDirectory) {

        map = new Map(mapDirectory);

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
