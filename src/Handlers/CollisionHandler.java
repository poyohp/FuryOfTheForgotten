package Handlers;
import Attacks.Attack;
import Entities.*;
import World.Tile;

public class CollisionHandler {
    boolean checkEntityWithAttackCollision(Entity entity, Attack attack) {
        Hitbox attackHitbox = attack.hitbox;
        int attackTop = attackHitbox.getWorldYPos();
        int attackBottom = attackTop + attackHitbox.getHeight();
        int attackLeft = attackHitbox.getWorldXPos();
        int attackRight = attackLeft + attackHitbox.getWidth();

        char attackDirection = attack.getDirection(0);
        if (attackDirection == 'u') {
            if (attackTop < entity.entityBottom && attackRight < entity.entityRight && attackLeft > entity.entityLeft) {
                return true;
            }
        } else if (attackDirection == 'd') {
            if (attackBottom > entity.entityTop && attackRight < entity.entityRight && attackLeft > entity.entityLeft) return true;

        } else if (attackDirection == 'l') {
            if (attackLeft < entity.entityRight && attackTop > entity.entityTop && attackBottom < entity.entityBottom) return true;
        } else {
            if (attackRight > entity.entityLeft && attackTop > entity.entityTop && attackBottom < entity.entityBottom) return true;
        }
        return false;
    }

    boolean playerWithTileCollision(Player player, Tile[][] tiles) {
        int topRow = (int)player.entityTop/Tile.tileSize;
        int bottomRow = (int)player.entityBottom/Tile.tileSize;
        int leftCol = (int)player.entityLeft/Tile.tileSize;
        int rightCol = (int)player.entityRight/Tile.tileSize;

        if (player.direction == 'u') {
            return isCollidableTileInRow(topRow - 1, leftCol, rightCol, tiles);
        } else if (player.direction == 'd') {
            return isCollidableTileInRow(bottomRow + 1, leftCol, rightCol, tiles);
        } else if (player.direction == 'l') {
            return isCollidableTileInColumn(leftCol - 1, topRow, bottomRow, tiles);
        } else {
            return isCollidableTileInColumn(rightCol + 1, topRow, bottomRow, tiles);
        }
    }

    private boolean isCollidableTileInRow(int row, int leftCol, int rightCol, Tile[][] tiles) {
        for (int col = leftCol; col <= rightCol; col++) {
            if (!(tiles[row][col].isCollidable)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollidableTileInColumn(int col, int topRow, int bottomRow, Tile[][] tiles) {
        for (int row = topRow; row <= bottomRow; row++) {
            if (!(tiles[row][col].isCollidable)) {
                return true;
            }
        }
        return false;
    }

    void enemyWithPlayerAttackCollision(Player player) {
        /*
    For playerAttack in playerAttackList in Level:
        IF playerAttack closeTo Enemy //compare x and y
        IF entityWithAttackCollision(enemy, playerAttack):
            return playerAttack;
         */
    }

    void playerWithEnemyAttackCollision(Enemy enemy) {
        /*
    For enemyAttack in enemyAttackList in Level:
        IF enemyAttack closeTo Player//compare x and y
        IF entityWithAttackCollision(player, enemyAttack):
                return enemyAttack;
         */
    }
}
