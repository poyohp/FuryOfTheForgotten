package Handlers;
import Attacks.Attack;
import Entities.*;
import World.Level;
import World.Tile;

public class CollisionHandler {
    boolean checkEntityWithAttackCollision(Entity entity, Attack attack) {
        int attackTop = attack.hitbox.getWorldYPos();
        int attackBottom = attackTop + attack.hitbox.getHeight();
        int attackLeft = attack.hitbox.getWorldXPos();
        int attackRight = attackLeft + attack.hitbox.getWidth();

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

    void enemyWithPlayerAttackCollision(Level level) {
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
