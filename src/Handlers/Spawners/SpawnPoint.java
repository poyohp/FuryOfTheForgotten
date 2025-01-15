package Handlers.Spawners;


import Entities.Enemies.Rabbit;
import Entities.Enemies.Slime;
import Handlers.ImageHandler;
import System.Panels.GamePanel;
import Entities.Enemies.Enemy;
import Entities.Players.Player;
import World.Level;
import World.Tile;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class SpawnPoint {


    private Random random = new Random();

    // HANDLING ALL FINAL SPAWN POINT VALUES
    public final int MIN_ENEMIES = 3;
    public final int MAX_ENEMIES = 6;
    private final int secondsBetweenSpawn = random.nextInt(2, 5);
    private final int framesBetweenSpawn = secondsBetweenSpawn * (int) GamePanel.FPS;

    final int enemyHealth = 2;
    final int enemySpeed = Tile.tileSize/40;
    final int enemySize = Tile.tileSize;

    public int numEnemies;
    public int framesSinceLastSpawn;

    public boolean activeSpawn;
    boolean spawnEnemy;

    public double worldX, worldY;
    public double screenX, screenY;

    boolean playerWithinRange;
    final int range = Tile.tileSize * 3;

    BufferedImage image;
    private final int imageSize = 16;
    private final int size = Tile.tileSize;

    /**
     * Constructor for SpawnPoint - initializes all spawn point values
     * @param worldX x position of the spawn point
     * @param worldY y position of the spawn point
     */
    public SpawnPoint(double worldX, double worldY, int level) {
        this.worldX = worldX;
        this.worldY = worldY;

        activeSpawn = true;
        spawnEnemy = false;
        playerWithinRange = false;
        framesSinceLastSpawn = 0;

        setNumEnemies();

        if (level != 2 && level != 3 && level != 4) this.image = ImageHandler.loadImage("Assets/Objects/ores&ingots&gems.png");

    }


    /**
     * Set the number of enemies to spawn based on the min and max values (random value)
     */
    private void setNumEnemies() {
        numEnemies = random.nextInt(MIN_ENEMIES, MAX_ENEMIES+1);
    }


    /**
     * Spawns an enemy at the spawn point
     * @param player the game player
     * @param level the current level
     * @return
     */
    public Enemy spawnEnemy(Player player, Level level) {
        numEnemies--;
        return new Slime(enemyHealth, enemySpeed, enemySize, enemySize, "Enemy", (int)worldX, (int)worldY, 3*Tile.tileSize/Tile.normalTileSize, 4*Tile.tileSize/Tile.normalTileSize, 11*Tile.tileSize/Tile.normalTileSize, 11*Tile.tileSize/Tile.normalTileSize, player, level.getMap().baseLayerTiles, true);
    }


    /**
     * Check if the spawn point should spawn an enemy
     */
    public void checkIfSpawn() {
        if (numEnemies > 0) {
            if (framesSinceLastSpawn > framesBetweenSpawn) {
                spawnEnemy = true;
                framesSinceLastSpawn = 0;
            }
        } else {
            activeSpawn = false;
        }
    }

    private void setScreenX(double playerWX, double playerSX) {
        this.screenX = worldX - playerWX + playerSX;
    }

    private void setScreenY(double playerWY, double playerSY) {
        this.screenY = worldY - playerWY + playerSY;
    }

    /**
     * Draw
     */
    public void draw(Graphics2D g2, Player player) {

        setScreenX(player.worldX, player.screenX);
        setScreenY(player.worldY, player.screenY);

        g2.drawImage(image, (int) screenX, (int) screenY, (int) screenX + size, (int) screenY + size, 16*9, 0, 16*9 + imageSize, imageSize, null);

        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Arial", Font.PLAIN, 50));
        g2.drawString(Integer.toString(numEnemies), (int) (worldX - player.worldX + player.screenX), (int) (worldY - player.worldY + player.screenY));
    }
}
