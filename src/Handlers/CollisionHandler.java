package Handlers;
import Attacks.Attack;
import Entities.*;
import Entities.Enemies.Enemy;
import Entities.Players.Player;
import Objects.Object;
import Objects.Chest;
import World.Tile;

import java.awt.*;

public class CollisionHandler {

    //Specifically for player-chest; since player can't walk over chest
    boolean checkChestWithEntityCollision(Entity entity, Chest chest) {
        double chestTop = chest.hitbox.worldY;
        double chestBottom = chestTop + chest.hitbox.height;
        double chestRight = chest.hitbox.worldX + chest.hitbox.width;
        double chestLeft = chest.hitbox.worldX;

        char entityDirection = entity.direction;

        if (entityDirection == 'u') {
            if (entity.entityBottom > chestBottom && entity.entityTop - entity.getSpeed() < chestBottom && entity.entityLeft < chestRight && entity.entityRight > chestLeft) {
                entity.worldY = chestBottom - entity.hitbox.yOffset;
                return true;
            }
        }

        if (entityDirection == 'd') {
            if (entity.entityTop < chestTop && entity.entityBottom + entity.getSpeed() > chestTop && entity.entityLeft < chestRight && entity.entityRight > chestLeft) {
                double bottomOffset = entity.getHeight() - (entity.hitbox.height + entity.hitbox.yOffset);
                entity.worldY = (chestTop + bottomOffset) - entity.getHeight();
                return true;
            }
        }

        if (entityDirection == 'l') {
            if (entity.entityRight > chestRight && entity.entityLeft - entity.getSpeed() < chestRight && entity.entityTop < chestBottom && entity.entityBottom > chestTop) {
                entity.worldX = chestRight - entity.hitbox.xOffset;
                return true;
            }
        }

        if (entityDirection == 'r') {
            if (entity.entityLeft < chestLeft && entity.entityRight + entity.getSpeed() > chestLeft && entity.entityTop < chestBottom && entity.entityBottom > chestTop) {
                double rightOffset = entity.getWidth() - (entity.hitbox.width + entity.hitbox.xOffset);
                entity.worldX = (chestLeft + rightOffset) - entity.getWidth();
                return true;
            }
        }

        return false;
    }

    //for interact checking --> going to be distance based!
    public boolean checkPlayerWithObjectCollision(Player player, Object object) {
        double distanceLimit = (Tile.tileSize * 0.75) * 2;
        return getDistance(player, object) <= distanceLimit;
    }

    private double getDistance(Player player, Object object) {
        double playerX = player.worldX + (double) player.getWidth() /2;
        double playerY = player.worldY + (double) player.getHeight() /2;

        double objectX = object.worldX + (double) object.width /2;
        double objectY = object.worldY + (double) object.height /2;

        return Math.abs(Math.sqrt(Math.pow(playerX - objectX, 2) + Math.pow(playerY - objectY, 2)));

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
                return true;
            }
            if (isNotWalkableTileInRow(topRow, leftCol, rightCol, tiles)) {
                return true;
            }
            if ((bottomRow + 1 >= tiles.length)) {
                return true;
            }
            if (isNotWalkableTileInRow(bottomRow, leftCol, rightCol, tiles)) {
                return true;
            }
            if (leftCol - 1 < 0) {
                return true;
            }
            if (isNotWalkableTileInCol(leftCol, topRow, bottomRow, tiles)) {
                return true;
            }
            if (rightCol + 1 > tiles[0].length) {
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
            if ((bottomRow + 1 > tiles.length)) return true;
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
     * Checks enemy collision with attack
     * Takes speed into account
     * @param enemy the enemy to check collision with
     * @param attack the attack to check collision with
     * @return true if there is overlap, false otherwise
     */
    public boolean enemyWithAttackCollision(Enemy enemy, Attack attack) {
        double enemyTop = enemy.worldY + enemy.getSpeed();
        double enemyBottom = enemyTop + enemy.getHeight();
        double enemyLeft = enemy.worldX + enemy.getSpeed();
        double enemyRight = enemyLeft + enemy.getWidth();

        double attackTop = attack.hitbox.worldY + attack.getSpeed();
        double attackBottom = attackTop + attack.hitbox.height;
        double attackLeft = attack.hitbox.worldX + attack.getSpeed();
        double attackRight = attackLeft + attack.hitbox.width;

        if (attackTop < enemyBottom && attackBottom > enemyTop && attackLeft < enemyRight && attackRight > enemyLeft) {
            return true;
        }
        return false;
    }

    /**
     * Checks enemy collision with player
     * Takes speed into account
     * @param enemy the enemy to check collision with
     * @param player the player to check collision with
     * @return true if there is overlap, false otherwise
     */
    public boolean enemyPlayerCollision(Enemy enemy, Player player) {
        double enemyTop = enemy.hitbox.worldY;
        double enemyBottom = enemyTop + enemy.hitbox.height;
        double enemyLeft = enemy.hitbox.worldX;
        double enemyRight = enemyLeft + enemy.hitbox.width;

        double playerTop = player.hitbox.worldY;
        double playerBottom = playerTop + player.hitbox.height;
        double playerLeft = player.hitbox.worldX;
        double playerRight = playerLeft + player.hitbox.width;

        if (playerTop < enemyBottom && playerBottom > enemyTop && playerLeft < enemyRight && playerRight > enemyLeft) {
            return true;
        }
        return false;
    }

}
