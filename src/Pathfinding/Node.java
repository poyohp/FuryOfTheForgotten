package Pathfinding;

import World.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Node {
    public Tile tile;
    public Node parent;
    public int row, col;
    public double gCost, hCost, fCost;

    public Node(Tile tile) {
        this.tile = tile;
        row = tile.getRow();
        col = tile.getCol();
    }

    public void getFCost () {
        this.fCost = this.gCost + this.hCost;
    }
}
