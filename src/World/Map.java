package World;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Map {

    private String mapDirectory;
    JSONParser parser;
    FileReader reader;

    private int mapWidth, mapHeight;
    Tile[][] baseMapTiles;
    Tile[][] obstacleMapTiles;


    Map(String mapDirectory) {
        this.mapDirectory = mapDirectory;
    }

    void getMap() throws IOException, ParseException {
        try {
            parser = new JSONParser();
            reader = new FileReader(mapDirectory);
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }

        // Parse the JSON file
        JSONObject jsonMapObject = (JSONObject) parser.parse(reader);

        mapWidth = Integer.parseInt(jsonMapObject.get("mapWidth").toString());
        mapHeight = Integer.parseInt(jsonMapObject.get("mapHeight").toString());

        baseMapTiles = new Tile[mapWidth][mapHeight];
        obstacleMapTiles = new Tile[mapWidth][mapHeight];

        JSONArray layers = (JSONArray) jsonMapObject.get("layers");

        for(int i = 0; i < layers.size(); i++) {
            JSONObject currentLayer = (JSONObject) layers.get(i);
            JSONArray dataArray = (JSONArray) currentLayer.get("data");

            for(int j = 0; j < dataArray.size(); j++) {

                for(int m = 0; m < mapHeight; m++) {
                    for(int n = 0; n < mapWidth; n++) {
                        if(i == 0) {
                            baseMapTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()));
                        } else if(i == 1) {
                            obstacleMapTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()));
                        }
                    }
                }

            }
        }

    }
}
