package Handlers.Attacks;


import Attacks.Melee.Stab;
import Attacks.Melee.Swing;
import Attacks.Melee.Swipe;
import Attacks.Ranged.Arrow;
import Attacks.Attack;
import Attacks.Melee.Melee;
import Attacks.Ranged.BloodOrb;
import Entities.Enemies.Enemy;
import Entities.Entity;
import Entities.Players.Player;
import Handlers.CollisionHandler;
import Handlers.KeyHandler;
import World.Level;
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
    Level level;
    
    final int playerRangedSpeed = (int) (Tile.tileRatio * 2.2);
    final int enemyRangedSpeed = Tile.tileRatio * 2;

    /**
     * Create attackHandler object
     * @param keyHandler keyHandler to detect key inputs
     */
    public AttackHandler(KeyHandler keyHandler, Tile[][] tileset, Level level) {
        this.keyHandler = keyHandler;
        this.tileset = tileset;
        this.level = level;
    }

    public void levelChanged(Level newLevel) {
        this.tileset = newLevel.getMap().baseLayerTiles;
        playerAttacks.clear();
        enemyAttacks.clear();
    }

    /**
     * Create melee attack
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity Entity attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     */
    void createPlayerStab(int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        playerAttacks.add(new Stab(range, width, direction, entity, xOffset, yOffset, duration));
    }

    /**
     * Create melee attack
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity Entity attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     */
    void createPlayerSwing(int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        playerAttacks.add(new Swing(range, width, direction, entity, xOffset, yOffset, duration));
    }

    /**
     * Create player ranged attack
     * @param range attack range
     * @param width attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity Entity attack corresponds to
     * @param xOffset attack x offset
     * @param yOffset attack y offset
     * @param duration attack duration
     * @param speed attack speed
     */
    void createPlayerArrow(int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed, double angle) {
        playerAttacks.add(new Arrow(range, width, direction, entity, xOffset, yOffset, duration, speed, angle));
    }

    void createPlayerSwipe(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        playerAttacks.add(new Swipe(damage, range, width, direction, entity, xOffset, yOffset, duration));
    }


    /**
     * Determine the player's ranged attack's velocities
     */
    void updatePlayerAttacks(Player player) {
        for (Attack a : playerAttacks) {
            a.update(player);
        }
    }

    void updateEnemyAttacks(Player player) {
        for(Attack a : enemyAttacks) {
            a.update(player);
        }
    }

    public void createPlayerBloodOrb(int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed, double angle, ArrayList<Enemy> enemies) {
        playerAttacks.add(new BloodOrb(range, width, direction, entity, xOffset, yOffset, duration, speed, angle, enemies));
    }

    void createPlayerAttacks(Player p) {
        if (p.type == 's') {
            if (p.direction == 'r') {
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 0); //RIGHT
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 15 * Math.PI / 8); // RIGHT DOWN
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, Math.PI / 8); // RIGHT UP
            } else if (p.direction == 'u') {
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, Math.PI / 2); // UP
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 3 * Math.PI / 8); //UP RIGHT
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 5 * Math.PI / 8); //UP LEFT
            } else if (p.direction == 'l') {
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, Math.PI); //LEFT
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 7 * Math.PI / 8); //LEFT UP
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 9 * Math.PI / 8); //LEFT DOWN
            } else {
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 3 * Math.PI / 2); //DOWN
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 11 * Math.PI / 8); //DOWN LEFT
                createPlayerArrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, p.direction, p, 0, 0, 150, playerRangedSpeed, 13 * Math.PI / 8); //DOWN RIGHT
            }
        } else if (p.type == 'g') {
            createPlayerStab(20 * Tile.tileRatio, 10 * Tile.tileRatio, p.direction, p, 0, 0, 30);
        } else if (p.type == 'z') {
            createPlayerSwing(15 * Tile.tileRatio, 20 * Tile.tileRatio, p.direction, p, 0, 0, 36);
        } else if (p.type == 'v') {
            createPlayerSwipe(1 + 2 - p.bloodBarState, 16 * Tile.tileRatio, 20 * Tile.tileRatio, p.direction, p, 0, 0, 15);
        }
    }

    public void update(Player player, Level level) {
        this.level = level;
        this.tileset = level.getMap().baseLayerTiles;
        player.checkAttack();
        player.setAttackCooldown();

        if (player.toCreateAttack()) createPlayerAttacks(player);
        if (!playerAttacks.isEmpty()) updatePlayerAttacks(player);
        if(!enemyAttacks.isEmpty()) updateEnemyAttacks(player);

        for (Enemy enemy: level.unkillableEnemies)  {
            if (enemy.attacking) {
                enemyAttacks.add(new Melee(1, (10 * Tile.tileRatio), 10 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 3));
            }
        }

        for (Enemy enemy: level.archerEnemies)  {
            if (enemy.attacking) {
                if (enemy.direction == 'r') {
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 0));
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 15 * Math.PI / 8));
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, Math.PI / 8));
                } else if (enemy.direction == 'u') {
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, Math.PI/2));
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 3 * Math.PI / 8));
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 5 * Math.PI / 8));
                } else if (enemy.direction == 'l') {
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, Math.PI));
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 7 * Math.PI / 8));
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 9 * Math.PI / 8));
                } else {
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 3 * Math.PI / 2));
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 11 * Math.PI / 8));
                    enemyAttacks.add(new Arrow(8 * Tile.tileRatio, 3 * Tile.tileRatio, enemy.direction, enemy, 0, 0, 150, enemyRangedSpeed, 13 * Math.PI / 8));

                }
                enemy.attacking = false;
            }
        }

        removeAttacks(player);
    }

    /**
     * Remove player attacks after duration ends and after collisions
     */
    private void removeAttacks(Player player) {

        if((player.type != 's' && player.type != 'v')&& playerAttacks.size() > 1) {
            for (int i = 0; i < playerAttacks.size(); i++) {
                if (i != 0) {
                    playerAttacks.remove(i);
                }
            }
        }



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
                } else {
                    enemyAttacks.get(i).setDuration((enemyAttacks.get(i).getDuration() - 1));
                }

            }

            enemyAttacks.removeAll(enemyToRemove);
            enemyToRemove.clear();

        }
    }

    /**
     * Draw all attacks
     *
     * @param g2     Graphics 2D object for drawing
     */
    public void draw(Graphics2D g2) {
        for (Attack playerAttack : playerAttacks) {
            playerAttack.draw(g2);
        }

        for (Attack enemyAttack: enemyAttacks) {
            enemyAttack.draw(g2);
        }
    }


}
