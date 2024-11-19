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

    private boolean isTileRowCollidable (Tile tile) {
        return true;
    }

    boolean playerWithTileCollision(Player player, Tile[][] tiles) {
        int topRow = (int)player.entityTop/Tile.tileSize;
        int bottomRow = (int)player.entityBottom/Tile.tileSize;
        int leftCol = (int)player.entityLeft/Tile.tileSize;
        int rightCol = (int)player.entityRight/Tile.tileSize;

        if (player.direction == 'u') {
            for (int i = leftCol; i < rightCol; i++) {
                if (!(tiles[topRow - 1][i].isCollidable)) {
                    return false;
                }
            }
            return true;
        } else if (player.direction == 'd') {
            for (int i = leftCol; i < rightCol; i++) {
                if (!(tiles[bottomRow + 1][i].isCollidable)) {
                    return false;
                }
            }
            return true;
        } else if (player.direction == 'l') {
            for (int i = topRow; i < bottomRow; i++) {
                if (!(tiles[i][leftCol].isCollidable)) {
                    return false;
                }
            }
            return true;
        } else {
            for (int i = topRow; i < bottomRow; i++) {
                if (!(tiles[i][rightCol].isCollidable)) {
                    return false;
                }
            }
            return true;
        }
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
