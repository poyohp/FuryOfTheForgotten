package Handlers.Spawners;

import System.GamePanel;
import Entities.Enemy;
import Entities.Player;
import World.Level;
import World.Tile;

import java.util.Random;

public class SpawnPoint {

    private Random random = new Random();

    public final int MIN_ENEMIES = 3;
    public final int MAX_ENEMIES = 5;

    private final int secondsBetweenSpawn = random.nextInt(15, 21);
    private final int framesBetweenSpawn = secondsBetweenSpawn * (int) GamePanel.FPS;

    final int enemyHealth = 100;
    final int enemySpeed = Tile.tileSize/40;
    final int enemySize = Tile.tileSize;

    public int numEnemies;
    public int framesSinceLastSpawn;

    boolean activeSpawn;
    boolean spawnEnemy;

    public double worldX, worldY;

    boolean playerWithinRange;
    final int range = Tile.tileSize * 3;

    public SpawnPoint(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;

        activeSpawn = true;
        spawnEnemy = false;
        playerWithinRange = false;
        framesSinceLastSpawn = 0;

        setNumEnemies();

    }

    private void setNumEnemies() {
        numEnemies = random.nextInt(MIN_ENEMIES, MAX_ENEMIES+1);
    }

    public Enemy spawnEnemy(Player player, Level level) {
        numEnemies--;
        return new Enemy(enemyHealth, enemySpeed, enemySize, enemySize, "Enemy", (int)worldX, (int)worldY, 4*Tile.tileSize/Tile.normalTileSize, 3*Tile.tileSize/Tile.normalTileSize, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, player, level.getMap().baseLayerTiles, true);
    }

    public void checkIfSpawn() {
        if(numEnemies > 0) {
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

}
