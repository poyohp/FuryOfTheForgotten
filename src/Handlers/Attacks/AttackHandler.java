package Handlers.Attacks;


import Attacks.Melee.Stab;
import Attacks.Ranged.Arrow;
import Attacks.Attack;
import Attacks.Melee.Melee;
import Attacks.Ranged.Ranged;
import Entities.Enemies.Enemy;
import Entities.Entity;
import Entities.Players.Player;
import Handlers.CollisionHandler;
import Handlers.KeyHandler;
import World.Tile;


import java.awt.*;
import java.util.ArrayList;


public class AttackHandler {
    // Variables for handling attacks
    public ArrayList<Attack> playerAttacks = new ArrayList<Attack>();
    ArrayList<Attack> playerToRemove = new ArrayList<Attack>();
    public ArrayList<Attack> enemyAttacks = new ArrayList<Attack>();
    ArrayList<Attack> enemyToRemove = new ArrayList<Attack>();
    KeyHandler keyHandler;
    CollisionHandler collisionHandler = new CollisionHandler();
    Tile[][] tileset;
    
    final int playerRangedSpeed = (int) (Tile.tileRatio * 2.2);

    /**
     * Create attackHandler object
     * @param keyHandler keyHandler to detect key inputs
     */
    public AttackHandler(KeyHandler keyHandler, Tile[][] tileset) {
        this.keyHandler = keyHandler;
        this.tileset = tileset;
    }

    /**
     * Create melee attack
     * @param damage attack damage
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity Entity attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     */
    void createPlayerStab(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        playerAttacks.add(new Stab(damage, range, width, direction, entity, xOffset, yOffset, duration));
    }

    /**
     * Create player ranged attack
     * @param damage attack damage
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity Entity attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     * @param speed attack speed
     */
    void createPlayerArrow(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed, double angle) {
        playerAttacks.add(new Arrow(damage, range, width, direction, entity, xOffset, yOffset, duration, speed, angle));
    }

    /**
     * Create enemy ranged attack
     * @param damage attack damage
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param enemy enemy that attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     * @param speed attack speed
     * @param player player to target
     */
    void createEnemyRanged(int damage, int range, int width, char direction, Enemy enemy, int xOffset, int yOffset, int duration, int speed, Player player) {
        Ranged enemyAttack = new Ranged(damage, range, width, direction, enemy, xOffset, yOffset, duration, speed);
//        enemyAttack.angle = enemyAttack.calculateAttackAngle(enemyAttack.hitbox, player.hitbox);
        enemyAttacks.add(enemyAttack);
    }

    /**
     * Determine the player's ranged attack's velocities
     */
    void updateAttacks(Player player) {
        for (Attack a : playerAttacks) {
            a.update(player);
        }
    }

    void createPlayerAttacks(Player p) {
        if (p.type == 's') {
            if (p.direction == 'r') {
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 0);
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 15 * Math.PI / 8);
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, Math.PI / 8);
            } else if (p.direction == 'u') {
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, Math.PI / 2);
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 3 * Math.PI / 8);
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 5 * Math.PI / 8);
            } else if (p.direction == 'l') {
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, Math.PI);
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 7 * Math.PI / 8);
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 9 * Math.PI / 8);
            } else {
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 3 * Math.PI / 2);
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 11 * Math.PI / 8);
                createPlayerArrow(10, 8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 13 * Math.PI / 8);
            }
        } else if (p.type == 'g') {
            createPlayerStab(25, 20 * Tile.tileRatio, 10 * Tile.tileRatio, p.direction, p, 0, 0, 30);
        }
    }

    // Must be updated when other entities are included to take an arraylist of all entities as a parameter, not just a player)
    public void update(Player player, ArrayList<Enemy> enemy) {
        player.checkAttack();
        player.setAttackCooldown();

        if (player.toCreateAttack()) {
            createPlayerAttacks(player);
        }

        if (!playerAttacks.isEmpty()) {
            updateAttacks(player);
        }

        removeAttacks();
    }

    /**
     * Remove player attacks after duration ends and after collisions
     */
    private void removeAttacks() {
        if (!playerAttacks.isEmpty()) {
            for (int i = 0; i < playerAttacks.size(); i++) {
                if (collisionHandler.attackWithTileCollision(playerAttacks.get(i), tileset)) {
                    playerToRemove.add(playerAttacks.get(i));
                }
                if (playerAttacks.get(i).getDuration() <= 0) {
                    playerToRemove.add(playerAttacks.get(i));
                } else {
                    playerAttacks.get(i).setDuration((playerAttacks.get(i).getDuration() - 1));
                }

            }
            playerAttacks.removeAll(playerToRemove);
            playerToRemove.clear();
        }

        if (!enemyAttacks.isEmpty()) {
            for (int i = 0; i < enemyAttacks.size(); i++) {
                if (collisionHandler.attackWithTileCollision(enemyAttacks.get(i), tileset)) {
                    enemyToRemove.add(enemyAttacks.get(i));
                }
                if (enemyAttacks.get(i).getDuration() <= 0) {
                    enemyToRemove.add(enemyAttacks.get(i));
                }
                enemyAttacks.get(i).setDuration((enemyAttacks.get(i).getDuration() - 1));
            }
            enemyAttacks.removeAll(enemyToRemove);
        }
    }

    /**
     * Draw all attacks
     * @param g2 Graphics 2D object for drawing
     */
    public void draw(Graphics2D g2, Player player) {
        for (int i = 0; i < playerAttacks.size(); i++) {
            playerAttacks.get(i).draw(g2);
        }
    }


}
