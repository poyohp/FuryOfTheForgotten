package Handlers;
import Attacks.Attack;
import Entities.*;
import World.Tile;

public class CollisionHandler {
    boolean checkEntityWithAttackCollision(Entity entity, Attack attack) {
        double attackTop = attack.hitbox.getWorldYPos();
        double attackBottom = attackTop + attack.hitbox.getHeight();
        double attackLeft = attack.hitbox.getWorldXPos();
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

    public boolean playerWithTileCollision(Player player, Tile[][] tiles) {
        int topRow = (int)(player.entityTop/Tile.tileSize);
        System.out.println("TOP: " + topRow);
        int bottomRow = (int)(player.entityBottom/Tile.tileSize);
        System.out.println("BOTTOM: " + bottomRow);
        int leftCol = (int)(player.entityLeft/Tile.tileSize);
        int rightCol = (int)(player.entityRight/Tile.tileSize);

        if (player.direction == 'u') {
            if (topRow - 1 < 0) return true;
            if (isNotWalkableTileInRow(topRow - 1, leftCol, rightCol, tiles)) {
                double tileBottom = tiles[topRow - 1][leftCol].getWorldYPos() + Tile.tileSize;
                if (player.entityTop - player.getSpeed() <= tileBottom) {
                    player.worldY = tileBottom;
                    System.out.println("Collision UP is going to happen!");
                    return true;
                }
            }
        } else if (player.direction == 'd') {
            if ((bottomRow + 1 >= tiles.length)) return true;
            if (isNotWalkableTileInRow(bottomRow + 1, leftCol, rightCol, tiles)) {
                double tileTop = tiles[bottomRow + 1][leftCol].getWorldYPos();
                if (player.entityBottom + player.getSpeed() >= tileTop){
                    player.worldY = tileTop - player.getHeight();
                    System.out.println("Collision DOWN is going to happen!");
                    return true;
                }
            }
        } else if (player.direction == 'l') {
            if (leftCol - 1 < 0) return true;
            if (isNotWalkableTileInCol(leftCol - 1, topRow, bottomRow, tiles)) {
                // double tileRight = tiles[leftCol-1][topRow].getWorldXPos() + Tile.tileSize; for some reason this doesnt work
                double tileRight = tiles[leftCol-1][topRow].getWorldXPos();

                if (player.entityLeft - player.getSpeed() < tileRight) {
                    System.out.println("Collision LEFT is going to happen!");
                    player.worldX = tileRight;
                    return true;
                }
            }
        } else if(player.direction == 'r') {
            if (rightCol + 1 >= tiles[0].length) return true;
            if (isNotWalkableTileInCol(rightCol + 1, topRow, bottomRow, tiles)) {
                double tileLeft = tiles[rightCol + 1][topRow].getWorldXPos();
                if (player.entityRight + player.getSpeed() > tileLeft) {
                    System.out.println("Collision RIGHT is going to happen!");
                    player.worldX = tileLeft - player.getWidth();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isNotWalkableTileInRow(int row, int leftCol, int rightCol, Tile[][] tiles) {
        for (int col = leftCol; col <= rightCol; col++) {
            if (!(tiles[row][col].walkable)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotWalkableTileInCol(int col, int topRow, int bottomRow, Tile[][] tiles) {
        for (int row = topRow; row <= bottomRow; row++) {
            if (!(tiles[row][col].walkable)) {
                System.out.println("TILE RIGHT/LEFT NOT WALKABLE!");
                return true;
            }
        }
        return false;
    }

    public boolean enemyWithAttackCollision(Enemy enemy, Attack attack) {
        return checkEntityWithAttackCollision(enemy, attack);
    }

    public boolean playerWithAttackCollision(Player player, Attack attack) {
        return checkEntityWithAttackCollision(player, attack);
    }

}
