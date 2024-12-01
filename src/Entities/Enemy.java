package Entities;

import Pathfinding.APathfinding;
import Pathfinding.Node;
import World.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Entity {

    // Used for attacking
    private int vision = 600;
    public double damage;
    public boolean isFollowing = false;
    public boolean attacking = true;

    Player player; // Player to reference

    // Variables used for pathfinding
    public boolean onPath;
    APathfinding pathFinder;
    Tile[][] tileset;

    /**
     * Enemy that follows player
     * @param health enemy health
     * @param speed enemy speed
     * @param width enemy width
     * @param height enemy height
     * @param name enemy name
     * @param worldX world x position
     * @param worldY world y position
     * @param xOffset x offset for hitbox
     * @param yOffset y offset for hitbox
     * @param hitBoxWidth hitbox width
     * @param hitBoxHeight hitbox height
     * @param player player to follow
     * @param tileset tileset to use for pathfinding
     * @param isFollowing whether enemy is following player or not
     */
    public Enemy(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);
        this.player = player;
        this.tileset = tileset;
        this.isFollowing = isFollowing;

        damage = 0.5; // Damage for all enemies

        pathFinder = new APathfinding(tileset);

        setScreenPosition();
        this.onPath = true;
    }

    /**
     * Updates enemy position and position on screen, and updates hitbox as well.
     * Then, moves the enemy
     */
    public void update() {
        updateEntityPosition();
        setScreenPosition();
        hitbox.update(this);
        move();
    }

    /**
     * This method will be used later, to check whether player is in vision of the enemy
     * @return True if player is in vision, false if not
     */
    private boolean playerInVision() {
        if (Math.abs(player.entityLeft - this.entityLeft) < vision && Math.abs(player.entityTop - this.entityTop) < vision) {
            return true;
        }
        else return false;
    }

    /**
     * Moves enemy
     */
    public void move() {

        // If enemy is going to follow player
        if (onPath) {
            int goalRow = (int) (player.entityTop/ Tile.tileSize); //top row of the player
            int goalCol = (int) (player.entityLeft/Tile.tileSize); //left row of the player

            searchPath(goalRow, goalCol);

        } //else: different random actions if the player is not in enemy vision
    }

    void attack() {
        /*
        IF player is within enemy vision:
			DISPLAY attack image
            Create NEW enemyAttack(worldX, worldY)
         */
    }

    /**
     * Sets enemy's position on the screen
     */
    void setScreenPosition() {
        // gets player coordinates and offsets by the player's place on the screen
        screenX = worldX - player.worldX + player.screenX;
        screenY = worldY - player.worldY + player.screenY;
    }

    /**
     * Searches for a path to the player
     * @param goalRow Player's row
     * @param goalCol Player's column
     */
    public void searchPath(int goalRow, int goalCol) {
        int startRow = (int) (this.entityTop/Tile.tileSize); //top row of the enemy
        int startCol = (int) (this.entityLeft/Tile.tileSize); //left row of the enemy

        pathFinder.setNodes(tileset[startRow][startCol], tileset[goalRow][goalCol]);

        // If a path is found, move towards the player
        if (pathFinder.findPath()) {
            ArrayList<Node> path = pathFinder.shortestPath; // List of tiles to go to

            // Next tile to go to
            double nextCol = path.get(0).col;
            double nextRow = path.get(0).row;

            // Next x and y position to go to (calculated with tile size)
            double nextWorldX = nextCol * Tile.tileSize;
            double nextWorldY = nextRow * Tile.tileSize;

            // Move towards the right direction
            if (worldX > nextWorldX) {
                direction = 'l'; // left
                worldX -= getSpeed();
            }
            else if (worldX < nextWorldX) {
                direction = 'r'; // right
                worldX += getSpeed();
            }
            else if (worldY > nextWorldY) {
                direction = 'u'; // up
                worldY -= getSpeed();
            }
            else if (worldY < nextWorldY) {
                direction = 'd'; // down
                worldY += getSpeed();
            }
        }
    }

    /**
     * Draws the enemy and its health bar
     * @param g2 Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2) {
        drawHealth(g2);
        g2.setColor(Color.PINK);
        g2.fillRect((int) screenX, (int) screenY, this.getWidth(), this.getHeight());
        g2.setColor(Color.ORANGE);
    }
}
