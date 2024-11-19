package Handlers;
import Attacks.Attack;
import Entities.*;
import World.Tile;

public class CollisionHandler {
    public void checkEntityWithAttackCollision(Entity entity, Attack attack) {
        if (attack.getDirection() == 'u') {

        }
    }
//    void checkEntityWithAttackCollision(Entity entity, Attack attack) {
//        /*
//    IF attack.directions.contains(UP):
//        IF attackTop ABOVE EntityBottom:
//            Return true;
//    IF attack.directions.contains(DOWN):
//        IF attackBottom BELOW EntityTop:
//            Return true;
//    IF attack.directions.contains(LEFT):
//        IF attackLeft LEFT-OF EntityRight:
//            Return true;
//    IF attack.directions.contains(RIGHT):
//        IF attackRight RIGHT-OF EntityLeft:
//            Return true;
//    ELSE return false
//         */
//    }

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
