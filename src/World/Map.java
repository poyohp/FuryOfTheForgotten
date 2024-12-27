package World;

import Handlers.ImageHandler;
import Entities.Players.Player;
//import Objects.UnusableObjects.Chest;
import Objects.UnusableObjects.Chest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Map {

    private BufferedImage tileSetImage;

    private String mapDirectory;
    private JSONParser parser;

    private int mapWidth, mapHeight;
    public Tile[][] baseLayerTiles;
    public Tile[][] spawnLayerTiles;

    private int tileSetTileSize;
    private int numTilesHeight, numTilesWidth;

    Level level;

    public ArrayList<Integer> nonWalkableValues = new ArrayList<Integer>();

    /**
     * Constructor for Map - sets map directory and sets all the non-walkable values
     * @param mapDirectory the directory of the map file
     */
    Map(String mapDirectory, String tileSetDirectory, int tileSetTileSize, Level level) {
        this.mapDirectory = mapDirectory;

        this.tileSetTileSize = tileSetTileSize;

        this.level = level;

        tileSetImage = ImageHandler.loadImage(tileSetDirectory);

        getHeightandWidthInTiles();

    }

    private void getHeightandWidthInTiles() {
        this.numTilesHeight = tileSetImage.getWidth()/tileSetTileSize;
        this.numTilesWidth = tileSetImage.getWidth()/tileSetTileSize;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Get the map from the JSON file using JSON parser and JSON array
     * @throws IOException
     * @throws ParseException
     */
    void getMap() throws IOException, ParseException {
        // Create a JSON parser and reader
        try (InputStream is = Map.class.getClassLoader().getResourceAsStream(mapDirectory);
             InputStreamReader isr = new InputStreamReader(is)) {

            parser = new JSONParser();

            if (is == null) {
                throw new FileNotFoundException("Map file not found: " + mapDirectory);
            }

            JSONObject jsonMapObject = (JSONObject) parser.parse(isr);

            mapWidth = Integer.parseInt(jsonMapObject.get("width").toString());
            mapHeight = Integer.parseInt(jsonMapObject.get("height").toString());

            baseLayerTiles = new Tile[mapWidth][mapHeight];
            spawnLayerTiles = new Tile[mapWidth][mapHeight];

            // Get layers and read data
            JSONArray layers = (JSONArray) jsonMapObject.get("layers");
            for (int i = 0; i < layers.size(); i++) {
                JSONObject currentLayer = (JSONObject) layers.get(i);
                JSONArray dataArray = (JSONArray) currentLayer.get("data");
                for (int j = 0; j < dataArray.size(); j++) {

                    int m = j / mapWidth;
                    int n = j % mapWidth;

                    // Create a new tile object and add it to the appropriate layer
                    if (i == 0) {
                        boolean walkable = !nonWalkableValues.contains(Integer.parseInt(dataArray.get(j).toString()));
                        baseLayerTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()), walkable, numTilesHeight, numTilesWidth);
                    } else if (i == 1) {
                        boolean walkable = Integer.parseInt(dataArray.get(j).toString()) > 0;
                        spawnLayerTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()), walkable, numTilesHeight, numTilesWidth);
                    } else if (i == 2) {
                        if(Integer.parseInt(dataArray.get(j).toString()) > 0) {
                            level.chests.add(new Chest("Chest", Tile.tileSize, Tile.tileSize, n*Tile.tileSize, m*Tile.tileSize, 0, 0));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Map file not found: " + mapDirectory);
            throw e;
        }
    }

    /**
     * Draws the current level map on the screen
     * @param g2 The 2D graphics object to draw the map with
     * @param player The player  to base the position off of
     */
    public void drawMap(Graphics2D g2, Player player) {
        for(int i = 0; i < mapHeight; i++) {
            for(int j = 0; j < mapWidth; j++) {
                setScreenPositions(i, j, player);

                int tileWorldX = (int)baseLayerTiles[i][j].getWorldXPos();
                int tileWorldY = (int)baseLayerTiles[i][j].getWorldYPos();

                if(tileWorldX + Tile.tileSize > player.worldX - player.screenX && tileWorldX - Tile.tileSize < player.worldX + player.screenX &&
                        tileWorldY + Tile.tileSize > player.worldY - player.screenY && tileWorldY - Tile.tileSize < player.worldY + player.screenY) {
                    g2.drawImage(tileSetImage, (int) baseLayerTiles[i][j].getScreenXPos(), (int) baseLayerTiles[i][j].getScreenYPos(), (int) baseLayerTiles[i][j].getScreenXPos() + Tile.tileSize, (int) baseLayerTiles[i][j].getScreenYPos() + Tile.tileSize, baseLayerTiles[i][j].getImageCol()*Tile.normalTileSize, baseLayerTiles[i][j].getImageRow()*Tile.normalTileSize, baseLayerTiles[i][j].getImageCol()*Tile.normalTileSize+Tile.normalTileSize, baseLayerTiles[i][j].getImageRow()*Tile.normalTileSize + Tile.normalTileSize, null);

                }
            }
        }
    }

    /**
     * Set the screen positions of the tiles based on player position
     * @param x The x value of the tile
     * @param y The y value of the tile
     * @param player The player to base the screen positions off of
     */
    private void setScreenPositions(int x, int y, Player player) {
        baseLayerTiles[x][y].setScreenXPos((int)(baseLayerTiles[x][y].getWorldXPos() - player.worldX + player.screenX));
        spawnLayerTiles[x][y].setScreenXPos((int)(spawnLayerTiles[x][y].getWorldXPos() - player.worldX + player.screenX));

        baseLayerTiles[x][y].setScreenYPos((int)(baseLayerTiles[x][y].getWorldYPos() - player.worldY + player.screenY));
        spawnLayerTiles[x][y].setScreenYPos((int)(spawnLayerTiles[x][y].getWorldYPos() - player.worldY + player.screenY));

    }

}
