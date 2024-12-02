package Handlers.Spawners;

import System.GamePanel;
import Entities.Enemy;
import Entities.Player;
import World.Level;
import World.Tile;

import java.awt.*;
import java.util.Random;

public class SpawnPoint {

    private Random random = new Random();

    // HANDLING ALL FINAL SPAWN POINT VALUES
    public final int MIN_ENEMIES = 3;
    public final int MAX_ENEMIES = 5;
    private final int secondsBetweenSpawn = random.nextInt(1, 2);
    private final int framesBetweenSpawn = secondsBetweenSpawn * (int) GamePanel.FPS;

    final int enemyHealth = 100;
    final int enemySpeed = Tile.tileSize/40;
    final int enemySize = Tile.tileSize;

    public int numEnemies;
    public int framesSinceLastSpawn;

    public boolean activeSpawn;
    boolean spawnEnemy;

    public double worldX, worldY;

    boolean playerWithinRange;
    final int range = Tile.tileSize * 3;

    /**
     * Constructor for SpawnPoint - initializes all spawn point values
     * @param worldX x position of the spawn point
     * @param worldY y position of the spawn point
     */
    public SpawnPoint(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;

        activeSpawn = true;
        spawnEnemy = false;
        playerWithinRange = false;
        framesSinceLastSpawn = 0;

        setNumEnemies();

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
        return new Enemy(enemyHealth, enemySpeed, enemySize, enemySize, "Enemy", (int)worldX, (int)worldY, 4*Tile.tileSize/Tile.normalTileSize, 3*Tile.tileSize/Tile.normalTileSize, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, player, level.getMap().baseLayerTiles, true);
    }

    /**
     * Check if the spawn point should spawn an enemy
     */
    public void checkIfSpawn() {
        if(numEnemies > 1) {
            if(framesSinceLastSpawn > framesBetweenSpawn) {
                spawnEnemy = true;
                framesSinceLastSpawn = 0;
            } else {
                if(activeSpawn) {
                    spawnEnemy = false;
                    framesSinceLastSpawn++;
                }
            }
        } else {
            activeSpawn = false;
        }
    }

    /**
     *
     */
    public void draw(Graphics2D g2, Player player) {
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Arial", Font.PLAIN, 50));
        g2.drawString(Integer.toString(numEnemies), (int) (worldX - player.worldX + player.screenX), (int) (worldY - player.worldY + player.screenY));
    }
        // Draw the spawn point
}
