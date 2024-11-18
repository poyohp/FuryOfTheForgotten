package World;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public class Level {

    private String mapDirectory;
    private Map map;


    public Level(String mapDirectory) {
        this.mapDirectory = mapDirectory;

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
