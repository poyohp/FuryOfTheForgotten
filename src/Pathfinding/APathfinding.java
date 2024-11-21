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
        //initialize starting and ending nodes
        startNode = new Node(startTile);
        startNode.gCost = 0;
        startNode.hCost = 0;
        openList.add(startNode);

        endNode = new Node(endTile);

        while (true) {
            currentNode = getLowestFCost();
            openList.remove(currentNode);
            closedList.push(currentNode);

            if (currentNode.col == endNode.col && currentNode.row == endNode.row) {
                return getShortestPath();
            }
        }
    }

    private ArrayList<Node> getNeighbours(Node currentNode) {
        ArrayList<Node> neighbours = new ArrayList<>();
        return null;
    }

    private Node getLowestFCost() {
        Node lowestCost = openList.get(0);
        for (Node node: openList) {
            if (node.fCost < lowestCost.fCost) currentNode = node;
        }
        return lowestCost;
    }

    private void setCosts (Node currentNode) {
        int xDistance = Math.abs(currentNode.col - startNode.col);
        int yDistance = Math.abs(currentNode.row - startNode.row);
        this.currentNode.gCost = xDistance + yDistance;

        xDistance = Math.abs(currentNode.col - endNode.col);
        yDistance = Math.abs(currentNode.row - endNode.row);
        this.currentNode.hCost = xDistance + yDistance;

        this.currentNode.fCost = currentNode.gCost + currentNode.hCost;
    }

    private ArrayList<Node> getShortestPath () {
        ArrayList<Node> shortestPath = new ArrayList<>();
        while (!closedList.empty()) {
            shortestPath.add(closedList.pop());
        }
        return shortestPath;
    }

}
