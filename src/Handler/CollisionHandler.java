package Handler;
import Entities.*;
import World.Tile;

public class CollisionHandler {
    void checkEntityWithAttackCollision(Entity entity, Attack attack) {
        /*
    IF attack.directions.contains(UP):
        IF attackTop ABOVE EntityBottom:
            Return true;
    IF attack.directions.contains(DOWN):
        IF attackBottom BELOW EntityTop:
            Return true;
    IF attack.directions.contains(LEFT):
        IF attackLeft LEFT-OF EntityRight:
            Return true;
    IF attack.directions.contains(RIGHT):
        IF attackRight RIGHT-OF EntityLeft:
            Return true;
    ELSE return false
         */
    }

    void playerWithTileCollision(Player player) {
        /*
            IF player.direction is UP:
                if Tile(s)AbovePlayer AND Tile(s)AbovePlayer.isCollidable
                    RETURN true
            ELSE-IF player.direction is DOWN:
                if TilesBelowPlayer AND Tile(s)AbovePlayer.isCollidable
                    RETURN  true
            ELSE-IF player.direction is RIGHT:
                if TilesOnRightOfPlayer AND Tile(s)AbovePlayer.isCollidable
                    RETURN true
            ELSE-IF player.direction is LEFT:
                if TilesOnLeftOfPlayer AND Tile(s)AbovePlayer.isCollidable
                    RETURN  true
            ELSE RETURN false;
         */
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
