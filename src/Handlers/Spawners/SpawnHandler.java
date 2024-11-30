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

    ArrayList<SpawnPoint> enemySpawnPoints = new ArrayList<SpawnPoint>();

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

    public void startSpawning() {
        spawnTimer.start();
    }

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

    public void levelChanged(Player player, Level level) {
        enemySpawnPoints.clear();
        setPlayerSpawn(player, level);
        setEnemySpawnerPoints(level);
    }

    private void spawnEnemies(SpawnPoint spawnPoint, Player player, Level level) {
        if(spawnPoint.spawnEnemy) {
            level.enemies.add(spawnPoint.spawnEnemy(player, level));
        }
    }

    private void checkWithinRange(Player player) {
        for (SpawnPoint spawnPoint : enemySpawnPoints) {
            if(spawnPoint.activeSpawn) {
                if (Math.abs(spawnPoint.worldX - player.worldX) < spawnPoint.range && Math.abs(spawnPoint.worldY - player.worldY) < spawnPoint.range) {
                    spawnPoint.playerWithinRange = true;
                }
            }
        }
    }

    public void update(Player player, Level level) {
        for (SpawnPoint spawnPoint : enemySpawnPoints) {
            if(spawnPoint.activeSpawn) {
                spawnPoint.checkIfSpawn();
                checkWithinRange(player);
                if(spawnPoint.playerWithinRange) {
                    spawnEnemies(spawnPoint, player, level);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(SpawnPoint spawnPoint : enemySpawnPoints) {
            spawnPoint.framesSinceLastSpawn++;
        }
    }
}
