package Pathfinding;

import World.Tile;

public class Node {
    public Tile tile;
    public Node parent;
    public boolean walkable;
    public int row, col;

    /*
    g cost is the lowest cost path from start to node (initially set to infinity)
    h cost is the lowest cost path from node to end
    f cost is the sum of g and h costs
     */
    public int gCost = Integer.MAX_VALUE, hCost, fCost;

    public Node(Tile tile) {
        this.tile = tile;
        row = tile.getRow();
        col = tile.getCol();
    }
}
