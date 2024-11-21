package Pathfinding;

import Handlers.LevelHandler;
import World.Tile;

import java.util.ArrayList;
import java.util.Stack;

import java.awt.*;

public class APathfinding {

    public Stack<Node> closedList = new Stack<Node>();
    public ArrayList<Node> openList;

    public Node startNode;
    public Node endNode;
    public Node currentNode;

    public APathfinding () {
        openList = new ArrayList();
        closedList = new Stack<Node>();
    }

    public ArrayList<Node> findPath(Tile startTile, Tile endTile) {
        startNode = new Node(startTile);
        startNode.gCost = 0;
        startNode.hCost = 0;
        this.openList.add(startNode);

        return null;
    }

    private void calculateCost(Node currentNode) {
        int xDistance = Math.abs(currentNode.col - startNode.col);
        int yDistance = Math.abs(currentNode.row - startNode.row);
        this.currentNode.gCost = xDistance + yDistance;

        xDistance = Math.abs(currentNode.col - endNode.col);
        yDistance = Math.abs(currentNode.row - endNode.row);
        this.currentNode.hCost = xDistance + yDistance;

        this.currentNode.fCost = currentNode.gCost + currentNode.hCost;
    }

}
