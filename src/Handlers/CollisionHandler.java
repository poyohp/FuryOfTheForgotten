package Handlers;
import Attacks.Attack;
import Entities.*;
import Entities.Enemies.Enemy;
import World.Tile;

import java.awt.*;

public class CollisionHandler {

    /**
     * Checks entity collision with attacks
     * Used for player-attack and enemy-attack collision
     * @param entity entity to check collision with
     * @param attack attack to check collision with
     * @return true if entity collides with attack, false otherwise
     */
    boolean checkEntityWithAttackCollision(Entity entity, Attack attack) {
        double attackTop = attack.hitbox.getWorldY();
        double attackBottom = attackTop + attack.hitbox.getHeight();
        double attackLeft = attack.hitbox.getWorldX();
        double attackRight = attackLeft + attack.hitbox.getWidth();

        char attackDirection1 = attack.getDirection()[0];
        char attackDirection2 = attack.getDirection()[1];

        if (attackDirection1 == 'u' || attackDirection2 == 'u') {
            if (attackTop <= entity.entityBottom && attackLeft <= entity.entityRight && attackRight >= entity.entityLeft) return true;
        }
        if (attackDirection1 == 'd' || attackDirection2 == 'd') {
            if (attackBottom >= entity.entityTop && attackLeft <= entity.entityRight && attackRight >= entity.entityLeft) return true;
        }
        if (attackDirection1 == 'l' || attackDirection2 == 'l') {
            if (attackLeft <= entity.entityRight && attackTop > entity.entityBottom && attackBottom < entity.entityTop) return true;
        }
        if(attackDirection1 == 'd' || attackDirection2 == 'd') {
            if (attackRight > entity.entityLeft && attackTop > entity.entityBottom && attackBottom < entity.entityTop) return true;
        }
        return false;
    }

    /**
     * Checks attack collision with tiles all directions
     * @param attack to check collision with
     * @param tiles List of tiles from the current level to check collision with
     * @return true if attack collides with a tile, false otherwise
     */
    public boolean attackWithTileCollision(Attack attack, Tile[][] tiles) {

        double arrowTop = attack.hitbox.worldY;
        double arrowBottom = attack.hitbox.worldY + attack.hitbox.height;
        double arrowLeft = attack.hitbox.worldX;
        double arrowRight = attack.hitbox.worldX + attack.hitbox.width;

        int topRow = (int)(arrowTop/Tile.tileSize);
        int bottomRow = (int)(arrowBottom/Tile.tileSize);
        int leftCol = (int)(arrowLeft/Tile.tileSize);
        int rightCol = (int)(arrowRight/Tile.tileSize);

        // Checking if the attack is colliding with any tiles in all directions
            if (topRow - 1 < 0) {
               // System.out.println(arrowTop);
                return true;
            }
            if (isNotWalkableTileInRow(topRow, leftCol, rightCol, tiles)) {
                return true;
            }
            if ((bottomRow + 1 >= tiles.length)) {
                System.out.println("Bottom");
                return true;
            }
            if (isNotWalkableTileInRow(bottomRow, leftCol, rightCol, tiles)) {
                return true;
            }
            if (leftCol - 1 < 0) {
                System.out.println("Left");
                return true;
            }
            if (isNotWalkableTileInCol(leftCol, topRow, bottomRow, tiles)) {
                return true;
            }
            if (rightCol + 1 > tiles[0].length) {
                System.out.println("Right");
                return true;
            }
            if (isNotWalkableTileInCol(rightCol, topRow, bottomRow, tiles)) {
                return true;
            }

        return false;
    }

    /**
     * Checks player collision with all tiles in all directions
     * @param player player to check collision with
     * @param tiles List of tiles from the current level to check collision with
     * @return true if player collides with a tile, false otherwise
     */
    public boolean playerWithTileCollision(Player player, Tile[][] tiles) {

        // Setting the row and column of the player
        int topRow = (int)(player.entityTop/Tile.tileSize);
        int bottomRow = (int)(player.entityBottom/Tile.tileSize);
        int leftCol = (int)(player.entityLeft/Tile.tileSize);
        int rightCol = (int)(player.entityRight/Tile.tileSize);

        // Checking if the player is colliding with a tile in the direction they are moving
        if (player.direction == 'u') {
            if (topRow - 1 < 0) return true;
            if (isNotWalkableTileInRow(topRow - 1, leftCol, rightCol, tiles)) {
                double tileBottom = tiles[topRow - 1][leftCol].getWorldYPos() + Tile.tileSize;
                if (player.entityTop - player.getSpeed() < tileBottom) return true;
            }
        } else if (player.direction == 'd') {
            if ((bottomRow + 1 >= tiles.length)) return true;
            if (isNotWalkableTileInRow(bottomRow + 1, leftCol, rightCol, tiles)) {
                double tileTop = tiles[bottomRow + 1][leftCol].getWorldYPos();
                if (player.entityBottom + player.getSpeed() > tileTop) return true;

            }
        } else if (player.direction == 'l') {
            if (leftCol - 1 < 0) return true;
            if (isNotWalkableTileInCol(leftCol - 1, topRow, bottomRow, tiles)) {
                 double tileRight = tiles[topRow][leftCol-1].getWorldXPos() + Tile.tileSize;
                if (player.entityLeft - player.getSpeed() < tileRight) return true;

            }
        } else if(player.direction == 'r') {
            if (rightCol + 1 > tiles[0].length) return true;
            if (isNotWalkableTileInCol(rightCol + 1, topRow, bottomRow, tiles)) {
                double tileLeft = tiles[bottomRow][rightCol + 1].getWorldXPos();
                if (player.entityRight + player.getSpeed() >= tileLeft) return true;

            }
        }
        return false;
    }

    /**
     * Checks if there is a non-walkable tile in the row of the player
     * @param row row of the player
     * @param leftCol left column of the player
     * @param rightCol right column of the player
     * @param tiles List of tiles from the current level
     * @return true if there is a non-walkable tile in the row of the player, false otherwise
     */
    private boolean isNotWalkableTileInRow(int row, int leftCol, int rightCol, Tile[][] tiles) {
        for (int col = leftCol; col <= rightCol; col++) {
            if (!(tiles[row][col].walkable)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a non-walkable tile in the column of the player
     * @param col column of the player
     * @param topRow top row of the player
     * @param bottomRow bottom row of the player
     * @param tiles List of tiles from the current level
     * @return
     */
    private boolean isNotWalkableTileInCol(int col, int topRow, int bottomRow, Tile[][] tiles) {
        for (int row = topRow; row <= bottomRow; row++) {
            if (!(tiles[row][col].walkable)) return true;
        }
        return false;
    }

    // THE FOLLOWING 2 METHODS USE RECTANGLES - WE WILL USE OUR "checkEntityWithAttackCollision" METHOD AFTER PROTOTYPE

    /**
     * Checks enemy collision with attack (temporary, will be updated after prototype is completed)
     * @param enemy the enemy to check collision with
     * @param attack the attack to check collision with
     * @return
     */
    static public boolean enemyWithAttackCollision (Enemy enemy, Attack attack) {
        if (enemy.hitbox.intersects(attack.hitbox)) return true;
        else return false;
    }

    /**
     * Checks enemy collision with player (temporary, will be updated after prototype is completed)
     * Used to deal damage - since enemies deal damage through collision for now
     * @param enemy the enemy to check collision with
     * @param player the player to check collision with
     * @return
     */
    static public boolean enemyPlayerCollision(Enemy enemy, Player player) {
        Rectangle enemyBox = new Rectangle((int)enemy.worldX, (int)enemy.worldY, enemy.getWidth(), enemy.getHeight());
        Rectangle playerBox = new Rectangle((int)player.worldX, (int)player.worldY, player.getWidth(), player.getHeight());

        if (enemyBox.intersects(playerBox)) return true;
        else return false;
    }

}
