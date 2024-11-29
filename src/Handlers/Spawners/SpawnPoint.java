package Handlers.Spawners;

import Entities.Enemy;
import Entities.Player;
import World.Level;
import World.Tile;

import java.util.Random;
import java.util.concurrent.TimeoutException;

public class SpawnPoint {

    public final int MIN_ENEMIES = 3;
    public final int MAX_ENEMIES = 6;

    final int enemyHealth = 100;
    final int enemySpeed = 3;
    final int enemySize = Tile.tileSize;

    public int numEnemies;

    public double worldX, worldY;
    private Random random = new Random();

    boolean playerWithinRange;
    final int range = 100;

    public SpawnPoint(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;

        playerWithinRange = false;

        setNumEnemies();

    }

    private void setNumEnemies() {
        numEnemies = random.nextInt(MIN_ENEMIES, MAX_ENEMIES+1);
    }

    public Enemy spawnEnemy(Player player, Level level) {
        return new Enemy(enemyHealth, enemySpeed, enemySize, enemySize, "Enemy", (int)worldX, (int)worldY, 4*Tile.tileSize/Tile.normalTileSize, 3*Tile.tileSize/Tile.normalTileSize, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, player, level.getMap().baseLayerTiles);
    }

}
