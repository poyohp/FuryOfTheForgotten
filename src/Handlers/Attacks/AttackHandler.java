package Handlers.Attacks;


import Attacks.Arrow;
import Attacks.Attack;
import Attacks.Melee;
import Attacks.Ranged;
import Entities.Enemies.Enemy;
import Entities.Entity;
import Entities.Player;
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
    public boolean playerCanAttack = true;
    public int playerCooldown = 30, attackFrames = 36;
    CollisionHandler collisionHandler = new CollisionHandler();
    Tile[][] tileset;

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
    void createMelee(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        playerAttacks.add(new Melee(damage, range, width, direction, entity, xOffset, yOffset, duration));
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
    void createPlayerRanged(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed, double angle) {
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
    void updateAttacks() {

        for (Attack a : playerAttacks) {
            a.update();
            //System.out.println("updated");
        }
    }

    void createPlayerAttacks(Player p) {
       // System.out.println("skibidi");
        if (p.direction == 'r') {
            //System.out.println("right");
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 0);
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 15*Math.PI/8);
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), Math.PI/8);
        } else if (p.direction == 'u') {
           // System.out.println("up");
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), Math.PI/2);
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 3*Math.PI/8);
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 5*Math.PI/8);
        } else if (p.direction == 'l') {
            //System.out.println("left");
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), Math.PI);
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 7*Math.PI/8);
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 9*Math.PI/8);
        } else {
            //System.out.println("right");
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 3*Math.PI/2);
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 11*Math.PI/8);
            createPlayerRanged(5, Tile.tileSize, Tile.tileSize, p.direction, p, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625), 13*Math.PI/8);
        }
    }

    // Must be updated when other entities are included to take an arraylist of all entities as a parameter, not just a player)
    public void update(Player player, ArrayList<Enemy> enemy) {
        player.checkAttack();
        player.setAttackCooldown();
        if(player.toCreateAttack()) {
            createPlayerAttacks(player);
        }



        // Issue here
        if (!playerAttacks.isEmpty()) {
            updateAttacks();
        }

        for (Attack a : playerAttacks) {

            System.out.println(a.angle);

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
                    //System.out.println("2");
                    playerToRemove.add(playerAttacks.get(i));
                    //System.out.println("One down!");
                }
                if (playerAttacks.get(i).getDuration() <= 0) {
                    playerToRemove.add(playerAttacks.get(i));
                } else {
                    playerAttacks.get(i).setDuration((playerAttacks.get(i).getDuration() - 1));
                }

            }
            for (Attack a : playerToRemove) {
                //System.out.println("1");
            }
            playerAttacks.removeAll(playerToRemove);
            playerToRemove.clear();
        }

        if (!enemyAttacks.isEmpty()) {
            for (int i = 0; i < enemyAttacks.size(); i++) {
                if (collisionHandler.attackWithTileCollision(enemyAttacks.get(i), tileset)) {
                    enemyToRemove.add(enemyAttacks.get(i));
                    //System.out.println("One down!");
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
    public void draw(Graphics2D g2) {
        for (int i = 0; i < playerAttacks.size(); i++) {
            playerAttacks.get(i).draw(g2);
        }
//        for (Attack attack : enemyAttacks) {
//            System.out.println("Drawing enemy attack at: (" + attack.getX() + ", " + attack.getY() + ")");
//            attack.draw(g2);
//        }
    }


}
