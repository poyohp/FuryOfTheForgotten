package Handlers.Spawners;


import Entities.Enemies.Dragon;
import Entities.Enemies.Rabbit;
import Entities.Enemies.RoyalKnight;
import Entities.Enemies.Slime;
import Entities.Players.Player;
import Handlers.Attacks.AttackHandler;
import World.Level;
import World.Tile;
import System.Panels.GamePanel;


import java.awt.*;
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

    RoyalKnight royalKnight;
    boolean bossSpawned = false;


    /**
     * Constructor for SpawnHandler - initializes the player spawn point (only 1)
     * @param player the game player
     * @param level the current level to spawn player in
     */
    public void setPlayerSpawn(Player player, Level level) {
        for (int i = 0; i < level.getMap().spawnLayerTiles.length; i++) {
            for (int j = 0; j < level.getMap().spawnLayerTiles.length; j++) {
                Tile tile = level.getMap().spawnLayerTiles[i][j];
                if (tile.getOrgValue() == 244 || tile.getOrgValue() == 64 || tile.getOrgValue() == 36 || tile.getOrgValue() == 367 || tile.getOrgValue() == 38 || tile.getOrgValue() == 973) {
                    playerSpawnX = tile.getCol() * Tile.tileSize;
                    playerSpawnY = tile.getRow() * Tile.tileSize;
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

    public void spawnBoss(Player player, Level level, AttackHandler a) {
        royalKnight = new RoyalKnight(100, 3, Tile.tileSize*2, Tile.tileSize*2, "RoyalKnight", (int) (player.worldX * Tile.tileSize) - 2 * Tile.tileSize, (int) (player.worldY * Tile.tileSize) + Tile.tileSize, Tile.tileRatio,5*Tile.tileRatio, 70, 50, player, level.getMap().baseLayerTiles, true, a);
        level.contactEnemies.add(royalKnight);
    }

    /**
     * Sets the enemy spawn points based on the level
     * @param level the current level to set enemy spawn points
     */
    public void setEnemySpawnerPoints(Level level, Player player) {
        for (int i = 0; i < level.getMap().spawnLayerTiles.length; i++) {
            for (int j = 0; j < level.getMap().spawnLayerTiles.length; j++) {
                int value = level.getMap().spawnLayerTiles[i][j].getOrgValue();
                if (value == 246 || value == 66 || value == 34 || value == 372 || value == 37) {
                    enemySpawnPoints.add(new SpawnPoint(level.getMap().spawnLayerTiles[i][j].getWorldXPos(), level.getMap().spawnLayerTiles[i][j].getWorldYPos(), level.levelNum));
                }

                // For adding Dragon enemies
                if (value == -3 || value == -4 || value == -5 || value == -6) {
                    addDragons(value, level, player, i, j);
                }

                if (value == -10) {
                    System.out.println("Added");
                    level.archerEnemies.add(new Rabbit(20, 1.5, Tile.tileSize, Tile.tileSize, "Rabbit", (int) level.getMap().spawnLayerTiles[i][j].getWorldXPos(), (int) level.getMap().spawnLayerTiles[i][j].getWorldYPos(), 0, 0, Tile.tileSize, Tile.tileSize, player, level.getMap().baseLayerTiles, true));
                }
            }
        }
    }

    private void addDragons(int value, Level level, Player player, int i, int j) {
        String idlePngName;
        String attackPngName;

        // Assigning dragons for specific levels
        switch (level.levelNum) {
            case 0:
            case 1:
                idlePngName = "AdultRedDragon_idle.png";
                attackPngName = "AdultRedDragon_attack_hitbox.png";
                break;
            case 2:
            case 3:
                idlePngName = "YoungGreenDragon_idle.png";
                attackPngName = "YoungGreenDragon_attack_hitbox.png";
                break;
            case 4:
                idlePngName = "FaerieDragon_idle.png";
                attackPngName = "AdultRedDragon_attack_hitbox.png";
                break;
            default:
                idlePngName = "AdultRedDragon_idle.png";
                attackPngName = "AdultRedDragon_attack_hitbox.png";
                break;
        }
        char direct = 'd';
        if (value == -4) direct = 'r'; // right
        else if (value == -5) direct = 'u'; // up
        else if (value == -6) direct = 'l'; // left
        level.unkillableEnemies.add(new Dragon(100, 0.0, Tile.tileSize, Tile.tileSize, "Dragon", (int) level.getMap().spawnLayerTiles[i][j].getWorldXPos(), (int) level.getMap().spawnLayerTiles[i][j].getWorldYPos(), 0, 0, 0, 0, player, false, idlePngName, attackPngName, direct));
    }

    /**
     * Changes the level and updates the player spawn point and enemy spawn points
     * @param player the game player
     * @param level the new level to update
     */
    public void levelChanged(Player player, Level level) {
        enemySpawnPoints.clear();
        setPlayerSpawn(player, level);
        setEnemySpawnerPoints(level, player);
    }


    /**
     * Spawns enemies at the spawn point at constant rates
     * @param spawnPoint the spawn point to spawn enemies at
     * @param player the game player
     * @param level the current level
     */
    private void spawnEnemies(SpawnPoint spawnPoint, Player player, Level level) {
        if (spawnPoint.spawnEnemy) {
            level.contactEnemies.add(spawnPoint.spawnEnemy(player, level));
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
    public void update(Player player, Level level, AttackHandler a) {
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

        if (level.lastLevel && !bossSpawned) {
            spawnBoss(player, level, a);
            bossSpawned = true;
        }
    }

    public void drawSpawns(Graphics2D g2, Player player) {
        for(SpawnPoint spawnPoint : enemySpawnPoints) {
            spawnPoint.draw(g2, player);
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

