package Handlers;
import Attacks.Attack;
import Entities.*;
import World.Tile;

import java.awt.*;

public class CollisionHandler {
    /**
     * Checks for collision between entity and attack
     * @param entity
     * @param attack
     * @return true if collision detected, false if not
     */
    boolean checkEntityWithAttackCollision(Entity entity, Attack attack) {
        // Positions of the hitbox
        double attackTop = attack.hitbox.getWorldYPos();
        double attackBottom = attackTop + attack.hitbox.getHeight();
        double attackLeft = attack.hitbox.getWorldXPos();
        double attackRight = attackLeft + attack.hitbox.getWidth();

        char attackDirection1 = attack.getDirection()[0];
        char attackDirection2 = attack.getDirection()[1];

        // Makes sure that a collision has happened
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
     * Checks for collision of player against tiles
     * @param player Player to check for collision
     * @param tiles Tileset with tiles
     * @return true if collision detected, false if not
     */
    public boolean playerWithTileCollision(Player player, Tile[][] tiles) {
        // Gets all the rows and columns that the player is currently in
        int topRow = (int)(player.entityTop/Tile.tileSize);
        int bottomRow = (int)(player.entityBottom/Tile.tileSize);
        int leftCol = (int)(player.entityLeft/Tile.tileSize);
        int rightCol = (int)(player.entityRight/Tile.tileSize);

        /*
         * Checks first for player direction, then if player is on an edge of the tileset.
         * Then, checks IF the player moves, whether it will collide with a tile or not
         * If yes, returns true. If not, then returns false
         */
        if (player.direction == 'u') {
            if (topRow - 1 < 0) return true; // Makes sure that collision checking is not out of bounds
            if (isNotWalkableTileInRow(topRow - 1, leftCol, rightCol, tiles)) {
                double tileBottom = tiles[topRow - 1][leftCol].getWorldYPos() + Tile.tileSize;
                if (player.entityTop - player.getSpeed() < tileBottom) return true;
            }
        } else if (player.direction == 'd') {
            if ((bottomRow + 1 >= tiles.length)) return true; // Makes sure that collision checking is not out of bounds
            if (isNotWalkableTileInRow(bottomRow + 1, leftCol, rightCol, tiles)) {
                double tileTop = tiles[bottomRow + 1][leftCol].getWorldYPos();
                if (player.entityBottom + player.getSpeed() > tileTop) return true;

            }
        } else if (player.direction == 'l') {
            if (leftCol - 1 < 0) return true; // Makes sure that collision checking is not out of bounds
            if (isNotWalkableTileInCol(leftCol - 1, topRow, bottomRow, tiles)) {
                 double tileRight = tiles[topRow][leftCol-1].getWorldXPos() + Tile.tileSize;
                if (player.entityLeft - player.getSpeed() < tileRight) return true;

            }
        } else if(player.direction == 'r') {
            if (rightCol + 1 > tiles[0].length) return true; // Makes sure that collision checking is not out of bounds
            if (isNotWalkableTileInCol(rightCol + 1, topRow, bottomRow, tiles)) {
                double tileLeft = tiles[bottomRow][rightCol + 1].getWorldXPos();
                if (player.entityRight + player.getSpeed() >= tileLeft) return true;

            }
        }
        return false;
    }

    /**
     * Checks to see if ANY of the tiles in the rows above or below a player's position are not walkable.
     * @param row row to check in
     * @param leftCol left column in row to check
     * @param rightCol right column in row to check
     * @param tiles tileset to check in
     * @return true if there are any non-walkable tiles, false if there are only walkable tiles
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
     * Checks to see if ANY of the tiles in the columns to the right or left of a player's position are not walkable.
     * @param col column to check in
     * @param topRow top row in column to check
     * @param bottomRow bottom row in column to check
     * @param tiles tileset to check in
     * @return true if there are any non-walkable tiles, false if there are only walkable tiles
     */
    private boolean isNotWalkableTileInCol(int col, int topRow, int bottomRow, Tile[][] tiles) {
        for (int row = topRow; row <= bottomRow; row++) {
            if (!(tiles[row][col].walkable)) return true;
        }
        return false;
    }

//    public boolean enemyWithAttackCollision(Enemy enemy, Attack attack) {
//        return checkEntityWithAttackCollision(enemy, attack);
//    }

    public boolean playerWithAttackCollision(Player player, Attack attack) {
        return checkEntityWithAttackCollision(player, attack);
    }

    /**
     * Checks enemy collision with attack (temporary, will be updated after prototype is completed)
     * @param enemy Enemy to check
     * @param attack Attack to check
     * @return
     */
    static public boolean enemyWithAttackCollision (Enemy enemy, Attack attack) {
        if (enemy.hitbox.intersects(attack.hitbox)) return true;
        else return false;
    }

    /** Checks if player is colliding with enemy
     * @param enemy to check
     * @param player to check
     * @return true if collision happened, false if not
     */
    static public boolean enemyPlayerCollision(Enemy enemy, Player player) {
        Rectangle enemyBox = new Rectangle((int)enemy.worldX, (int)enemy.worldY, enemy.getWidth(), enemy.getHeight());
        Rectangle playerBox = new Rectangle((int)player.worldX, (int)player.worldY, player.getWidth(), player.getHeight());

        if (enemyBox.intersects(playerBox)) return true;
        else return false;
    }

}
