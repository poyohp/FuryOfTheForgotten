package Pathfinding;

import World.Tile;

public class Node {
    public Tile tile;
    public Node parent;
    public int row, col;

    /*
     * g cost is the lowest cost path from start to node (initially set to infinity)
     * h cost is the lowest cost path from node to end
     * f cost is the sum of g and h costs
     */
    public int gCost, hCost, fCost;

    public boolean walkable, open, closed;

    public Node(Tile tile) {
        // Sync tile row and col values to node row and col
        this.tile = tile;
        row = tile.getRow();
        col = tile.getCol();
    }
}
