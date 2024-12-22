package World;

import Handlers.ImageHandler;
import Entities.Players.Player;
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
    private FileReader reader;

    private int mapWidth, mapHeight;
    public Tile[][] baseLayerTiles;
    public Tile[][] spawnLayerTiles;

    private int numTilesHeight, numTilesWidth;

    public ArrayList<Integer> nonWalkableValues = new ArrayList<Integer>();

    /**
     * Constructor for Map - sets map directory and sets all the non-walkable values
     * @param mapDirectory the directory of the map file
     */
    Map(String mapDirectory, String tileSetDirectory, int numTilesHeight, int numTilesWidth) {
        this.mapDirectory = mapDirectory;

        this.numTilesHeight = numTilesHeight;
        this.numTilesWidth = numTilesWidth;

        tileSetImage = ImageHandler.loadImage(tileSetDirectory);
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

                    boolean walkable = !nonWalkableValues.contains(Integer.parseInt(dataArray.get(j).toString()));

                    // Create a new tile object and add it to the appropriate layer
                    if (i == 0) {
                        baseLayerTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()), walkable, numTilesHeight, numTilesWidth);
                    } else if (i == 1) {
                        spawnLayerTiles[m][n] = new Tile(m, n, Integer.parseInt(dataArray.get(j).toString()), walkable, numTilesHeight, numTilesWidth);
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

                g2.drawImage(tileSetImage, (int) baseLayerTiles[i][j].getScreenXPos(), (int) baseLayerTiles[i][j].getScreenYPos(), (int) baseLayerTiles[i][j].getScreenXPos() + Tile.tileSize, (int) baseLayerTiles[i][j].getScreenYPos() + Tile.tileSize, baseLayerTiles[i][j].getImageCol()*Tile.normalTileSize, baseLayerTiles[i][j].getImageRow()*Tile.normalTileSize, baseLayerTiles[i][j].getImageCol()*Tile.normalTileSize+Tile.normalTileSize, baseLayerTiles[i][j].getImageRow()*Tile.normalTileSize + Tile.normalTileSize, null);
                
                if(spawnLayerTiles[i][j].getValue() > 0) {
                    g2.setColor(Color.ORANGE);
                    g2.fillRect((int)spawnLayerTiles[i][j].getScreenXPos(), (int)spawnLayerTiles[i][j].getScreenYPos(), Tile.tileSize, Tile.tileSize);

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
