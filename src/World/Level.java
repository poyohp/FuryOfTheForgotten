package World;

import Attacks.Attack;
import Entities.Enemy;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private Map map;

    public ArrayList<Enemy> enemies = new ArrayList<>();

    public Level(String mapDirectory) {

        map = new Map(mapDirectory);

        try {
            map.getMap();
        } catch (IOException | ParseException e) {
            System.out.println("Error reading map file");
        }
    }

    public Map getMap() {
        return map;
    }

}
