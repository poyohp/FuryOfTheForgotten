package Handlers.Spawners;

import Entities.Player;
import World.Level;
import World.Tile;
import System.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

public class SpawnHandler implements ActionListener {

    Timer spawnTimer = new Timer((int) (1000/GamePanel.FPS), this);

    public int playerSpawnX, playerSpawnY;

    public ArrayList<SpawnPoint> enemySpawnPoints = new ArrayList<SpawnPoint>();

    public boolean started = false;

    public int numActiveSpawns = 0;

    /**
     * Constructor for SpawnHandler - initializes the player spawn point (only 1)
     * @param player the game player
     * @param level the current level to spawn player in
     */
    public void setPlayerSpawn(Player player, Level level) {
        for (int i = 0; i < level.getMap().spawnLayerTiles.length; i++) {
            for (int j = 0; j < level.getMap().spawnLayerTiles.length; j++) {
                Tile tile = level.getMap().spawnLayerTiles[i][j];
                if (tile.getOrgValue() == 244) {
                    playerSpawnX = tile.getCol() * Tile.tileSize;
                    playerSpawnY = tile.getRow() * Tile.tileSize;;
                    player.worldX = playerSpawnX;
                    player.worldY = playerSpawnY;
                }
            }
        }
    }

    /**
     * Begins spawning the player
     */
    public void startSpawning() {
        spawnTimer.start();
        started = true;
    }

    /**
     * Sets the enemy spawn points based on the level
     * @param level the current level to set enemy spawn points
     */
    public void setEnemySpawnerPoints(Level level) {
        for (int i = 0; i < level.getMap().spawnLayerTiles.length; i++) {
            for (int j = 0; j < level.getMap().spawnLayerTiles.length; j++) {
                int value = level.getMap().spawnLayerTiles[i][j].getOrgValue();
                if (value == 246) {
                    enemySpawnPoints.add(new SpawnPoint(level.getMap().spawnLayerTiles[i][j].getWorldXPos(), level.getMap().spawnLayerTiles[i][j].getWorldYPos()));
                }
            }
        }
    }

    /**
     * Changes the level and updates the player spawn point and enemy spawn points
     * @param player the game player
     * @param level the new level to update
     */
    public void levelChanged(Player player, Level level) {
        enemySpawnPoints.clear();
        setPlayerSpawn(player, level);
        setEnemySpawnerPoints(level);
    }

    /**
     * Spawns enemies at the spawn point at constant rates
     * @param spawnPoint the spawn point to spawn enemies at
     * @param player the game player
     * @param level the current level
     */
    private void spawnEnemies(SpawnPoint spawnPoint, Player player, Level level) {
        if (spawnPoint.spawnEnemy) {
            level.enemies.add(spawnPoint.spawnEnemy(player, level));
            spawnPoint.spawnEnemy = false;
        }
    }


    /**
     * Checks if the player is within range of the spawn point
     * @param player the game player
     */
    private void checkWithinRange(Player player) {
        for (SpawnPoint spawnPoint : enemySpawnPoints) {
            if(spawnPoint.activeSpawn) {
                if (Math.abs(spawnPoint.worldX - player.worldX) < spawnPoint.range && Math.abs(spawnPoint.worldY - player.worldY) < spawnPoint.range) {
                    spawnPoint.playerWithinRange = true;
                }
            }
        }
    }

    /**
     * Updates the spawn points and spawns enemies if the player is within range
     * @param player the game player
     * @param level the current level
     */
    public void update(Player player, Level level) {
        for (SpawnPoint spawnPoint : enemySpawnPoints) {
            if (spawnPoint.activeSpawn) {
                numActiveSpawns++;
                spawnPoint.checkIfSpawn();
                checkWithinRange(player);
                if (spawnPoint.playerWithinRange) {
                    spawnEnemies(spawnPoint, player, level);
                }
            }
        }
    }


    /**
     * Each timer tick - increments the frames since last spawn for each spawn point
     * Overall, used to spawn enemies at constant rates
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (SpawnPoint spawnPoint : enemySpawnPoints) {
            if (spawnPoint.activeSpawn && !spawnPoint.spawnEnemy) {
                spawnPoint.framesSinceLastSpawn++;
            }
        }
    }
}
