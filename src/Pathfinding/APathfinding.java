package Pathfinding;

import Handlers.LevelHandler;
import World.Tile;

import java.util.ArrayList;
import java.util.Stack;

import java.awt.*;

public class APathfinding {

    public ArrayList<Node> closedList = new ArrayList<>();
    public ArrayList<Node> openList;

    public Node[][] nodeArray;
    public Node startNode;
    public Node endNode;
    public Node currentNode;

    public MyQueue shortestPath;

    public APathfinding (Tile[][] tileArray) {
        openList = new ArrayList();
        closedList = new ArrayList<Node>();

        shortestPath = new MyQueue();

        nodeArray = new Node[tileArray.length][tileArray[0].length];
        //loops through tile array and copies over its values to a node array
        for (int row = 0; row < tileArray.length; row++) {
            for (int col = 0; col < tileArray[row].length; col++) {
                nodeArray[row][col] = new Node(tileArray[row][col]);
                if (tileArray[row][col].walkable) nodeArray[row][col].walkable = true;
                else nodeArray[row][col].walkable = false;
            }
        }
    }

    public MyQueue findPath(Tile startTile, Tile endTile) {
        //initialize starting and ending nodes to the ones in the 2D Node array
        endNode = nodeArray[endTile.getRow()][endTile.getCol()];
        startNode = nodeArray[startTile.getRow()][startTile.getCol()];

        //initialize the starting node
        startNode.gCost = 0;
        openList.add(startNode);

        //while there are still nodes to be evalutated, look for the path
        while (!openList.isEmpty()) {
            currentNode = getLowestFCost();
            openList.remove(currentNode);
            closedList.add(currentNode);

            if (currentNode == endNode) {
                return getShortestPath();
            }

            for (Node neighbour: getNeighbours(currentNode)) {
                //skips obstacles and nodes that have already been evaluated
                if (!neighbour.walkable || inList(closedList, neighbour)) continue;

                if (checkGCost(neighbour) || !inList(openList, neighbour)) {
                    setCosts(neighbour);
                    neighbour.parent = currentNode;
                    if (!inList(openList, neighbour)) openList.add(neighbour);
                }
            }
        }
        //will return empty arrayList if there was no shortest path found
        return new MyQueue();
    }

    private ArrayList<Node> getNeighbours(Node currentNode) {
        ArrayList<Node> neighbours = new ArrayList<>();

        int row = currentNode.row;
        int col = currentNode.col;

        // get neighbours (only when they are part of the map!)
        if (row > 0) neighbours.add(nodeArray[row - 1][col]); // up
        if (row < nodeArray.length) neighbours.add(nodeArray[row + 1][col]); // down
        if (col > 0) neighbours.add(nodeArray[currentNode.row][currentNode.col - 1]); // left
        if (col < nodeArray[0].length) neighbours.add(nodeArray[currentNode.row][currentNode.col + 1]); // right

        return neighbours;
    }

    private boolean inList(ArrayList<Node> list, Node node) {
        for (Node n: list) {
            if (n.equals(node)) return true;
        }
        return false;
    }

    private Node getLowestFCost() {
        Node lowestCost = openList.get(0);
        for (Node node: openList) {
            if (node.fCost <= lowestCost.fCost) lowestCost = node;
        }
        return lowestCost;
    }

    /**
     * Checks to see if the new g cost is lower than the initial g cost of a node
     * @param newNode Node to check
     * @return returns whether the new path to the neighbour is shorter or not
     */
    private boolean checkGCost(Node newNode) {
        //gets the g cost for a new node, taking the path from the current node
        int xDistance = Math.abs(newNode.col - currentNode.col);
        int yDistance = Math.abs(newNode.row - currentNode.row);
        int newGCost = currentNode.gCost + xDistance + yDistance;

        if (newGCost < newNode.gCost) {
            newNode.gCost = newGCost;
            return true;
        }
        return false;
    }

    private void setCosts (Node node) {
        int xDistance = Math.abs(node.col - endNode.col);
        int yDistance = Math.abs(node.row - endNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    private MyQueue getShortestPath () {
        shortestPath = new MyQueue();
        Node current = endNode;

        while (current != startNode) {
            shortestPath.enqueue(current);
            current = current.parent;
        }
        shortestPath.enqueue(startNode);
        return shortestPath;
    }

}
