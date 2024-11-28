package World;

import Handlers.ImageHandler;
import System.Main;
import Entities.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {

    private final BufferedImage tileSetImage = ImageHandler.loadImage("src/Assets/Tilesets/universalTileset.png");

    private String mapDirectory;
    private JSONParser parser;
    private FileReader reader;

    private int mapWidth, mapHeight;
    public Tile[][] baseLayerTiles;
    public Tile[][] spawnLayerTiles;

    ArrayList<Integer> nonWalkableValues = new ArrayList<Integer>();

    Map(String mapDirectory) {
        this.mapDirectory = mapDirectory;
        addAllNonWalkableValues();
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
                boolean walkable;

                walkable = !nonWalkableValues.contains(Integer.parseInt(dataArray.get(j).toString()));

                if(i == 0) {
                    baseLayerTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()), walkable);
                }
                if(i == 1) {
                    spawnLayerTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()), walkable);
                }
            }
        }
    }

    public void drawMap(Graphics2D g2, Player player) {
        for(int i = 0; i < mapHeight; i++) {
            for(int j = 0; j < mapWidth; j++) {
                setScreenPositions(i, j, player);

                g2.drawImage(tileSetImage, (int) baseLayerTiles[i][j].getScreenXPos(), (int) baseLayerTiles[i][j].getScreenYPos(), (int) baseLayerTiles[i][j].getScreenXPos() + Tile.tileSize, (int) baseLayerTiles[i][j].getScreenYPos() + Tile.tileSize, baseLayerTiles[i][j].getImageCol()*Tile.normalTileSize, baseLayerTiles[i][j].getImageRow()*Tile.normalTileSize, baseLayerTiles[i][j].getImageCol()*Tile.normalTileSize+Tile.normalTileSize, baseLayerTiles[i][j].getImageRow()*Tile.normalTileSize + Tile.normalTileSize, null);
                
                if(spawnLayerTiles[i][j].getValue() > 0) {
                    g2.setColor(Color.ORANGE);
                    g2.fillRect((int)spawnLayerTiles[i][j].getScreenXPos(), (int)spawnLayerTiles[i][j].getScreenYPos(), Tile.tileSize, Tile.tileSize);

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

    private void addAllNonWalkableValues() {
        this.nonWalkableValues.add(201);
        this.nonWalkableValues.add(202);
        this.nonWalkableValues.add(203);
        this.nonWalkableValues.add(229);
        this.nonWalkableValues.add(230);
        this.nonWalkableValues.add(232);
        this.nonWalkableValues.add(258);
        this.nonWalkableValues.add(260);
        this.nonWalkableValues.add(286);
        this.nonWalkableValues.add(287);
    }

}
