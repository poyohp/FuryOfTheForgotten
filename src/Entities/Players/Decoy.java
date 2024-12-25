package Entities.Players;

import Entities.Entity;
import Entities.Hitbox;
import Handlers.CollisionHandler;
import Handlers.ImageHandler;
import Handlers.KeyHandler;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Decoy extends Player {

    Player player;
    CollisionHandler collisionHandler;
    Tile[][] tiles;
    BufferedImage sprites = ImageHandler.loadImage("Assets/Entities/Players/Goblin/Goblin_Rogue.png");
    int column1 = 7, column2 = 39, column3 = 71, column4 = 103, row1 = 7, row2 = 39, row3 = 71, row4 = 103, row5 = 135, row6 = 167, row7 = 199, row8 = 231, row9 = 263, row10 = 295, row11 = 327, row12 = 359, row13 = 391, row14 = 423, row15 = 455;
    Color transparent = new Color(0,0,0,0);

    public Decoy(Player player) {
        super(100000000, 4, player.getWidth(), player.getHeight(), "decoy", player.worldX, player.worldY, player.hitbox.xOffset, player.hitbox.yOffset, player.hitbox.width, player.hitbox.height, new KeyHandler());
        this.direction = player.direction;
        this.player = player;
        this.collisionHandler = new CollisionHandler();
        this.tiles = player.tiles;
    }

    public void move() {
        if (direction == 'u') {
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldY -= getSpeed();

        } else if (direction == 'd') {
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldY += getSpeed();
        }
        if (direction == 'l') {
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldX -= getSpeed();

        } else if (direction == 'r') {
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldX += getSpeed();
        }
    }

    public void updateScreenPosition() {
        // gets player coordinates and offsets by the player's place on the screen
        screenX = worldX - player.worldX + player.screenX;
        screenY = worldY - player.worldY + player.screenY;
    }

    public void update() {
        move();
        updateEntityPosition();
        updateScreenPosition();
        hitbox.update(this);
        animationState = player.animationState;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (direction == 'u') {
            switch (animationState) {
                case 0:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth() + 2*Tile.tileRatio, (int) screenY + this.getHeight(), column1, row6, column1 + Tile.normalTileSize + 2, row6 + Tile.normalTileSize, transparent, null);
                    break;
                case 1:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth() + 2*Tile.tileRatio, (int) screenY + this.getHeight(), column2, row6, column2 + Tile.normalTileSize + 2, row6 + Tile.normalTileSize, transparent, null);
                    break;
                case 2:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth() + 2*Tile.tileRatio, (int) screenY + this.getHeight(), column3, row6, column3 + Tile.normalTileSize + 2, row6 + Tile.normalTileSize, transparent, null);
                    break;
                case 3:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth() + 2*Tile.tileRatio, (int) screenY + this.getHeight(), column4, row6, column4 + Tile.normalTileSize + 2, row6 + Tile.normalTileSize, transparent, null);
                    break;
            }
        } else if (direction == 'r') {
            switch (animationState) {
                case 0:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row5, column1 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                    break;
                case 1:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row5, column2 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                    break;
                case 2:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row5, column3 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                    break;
                case 3:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row5, column4 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                    break;
            }
        } else if (direction == 'd') {
            switch (animationState) {
                case 0:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row4, column1 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                    break;
                case 1:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row4, column2 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                    break;
                case 2:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row4, column3 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                    break;
                case 3:
                    g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row4, column4 + Tile.normalTileSize, row4 + Tile.normalTileSize + 1, transparent, null);
                    break;
            }
        } else {
            switch (animationState) {
                case 0:
                    g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column1, row5, column1 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                    break;
                case 1:
                    g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column2, row5, column2 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                    break;
                case 2:
                    g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column3, row5, column3 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                    break;
                case 3:
                    g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column4, row5, column4 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                    break;
            }
        }
    }

}
