package World;

import Entities.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Map {

    private String mapDirectory;
    JSONParser parser;
    FileReader reader;

    private int mapWidth, mapHeight;
    public Tile[][] baseLayerTiles;
    public Tile[][] spawnLayerTiles;


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

        mapWidth = Integer.parseInt(jsonMapObject.get("width").toString());
        mapHeight = Integer.parseInt(jsonMapObject.get("height").toString());

        baseLayerTiles = new Tile[mapWidth][mapHeight];
        spawnLayerTiles = new Tile[mapWidth][mapHeight];

        JSONArray layers = (JSONArray) jsonMapObject.get("layers");


        for(int i = 0; i < layers.size(); i++) {
            JSONObject currentLayer = (JSONObject) layers.get(i);
            JSONArray dataArray = (JSONArray) currentLayer.get("data");

            for(int j = 0; j < dataArray.size(); j++) {

                int m = j / mapWidth;
                int n = j % mapWidth;

                baseLayerTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()));
                spawnLayerTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()));

            }
        }
    }

    public void drawMap(Graphics2D g2, Player player) {
        for(int i = 0; i < mapHeight; i++) {
            for(int j = 0; j < mapWidth; j++) {
                setScreenPositions(i, j, player);
                g2.setColor(Color.BLUE);
                g2.fillRect(baseLayerTiles[i][j].getScreenXPos(), baseLayerTiles[i][j].getScreenYPos(), Tile.tileSize, Tile.tileSize);

                if(spawnLayerTiles[i][j].getValue() > 0) {
                    g2.setColor(Color.ORANGE);
                    g2.fillRect(spawnLayerTiles[i][j].getScreenXPos(), spawnLayerTiles[i][j].getScreenYPos(), Tile.tileSize, Tile.tileSize);

                }
            }
        }
    }

    private void setScreenPositions(int x, int y, Player player) {
        baseLayerTiles[x][y].setScreenXPos((int)(baseLayerTiles[x][y].getWorldXPos() - player.worldX + player.screenX));
        spawnLayerTiles[x][y].setScreenXPos((int)(spawnLayerTiles[x][y].getWorldXPos() - player.worldX + player.screenX));

        baseLayerTiles[x][y].setScreenYPos((int)(baseLayerTiles[x][y].getWorldYPos() - player.worldY + player.screenY));
        spawnLayerTiles[x][y].setScreenYPos((int)(spawnLayerTiles[x][y].getWorldYPos() - player.worldY + player.screenY));

    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

}
