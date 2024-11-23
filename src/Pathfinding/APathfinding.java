package Pathfinding;

import World.Tile;
import java.util.ArrayList;

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

    public void initializeNodeCosts() {
        for (int row = 0; row < nodeArray.length; row++) {
            for (int col = 0; col < nodeArray[row].length; col++) {
                setCosts(nodeArray[row][col]);
            }
        }
    }

    public MyQueue findPath(Tile startTile, Tile endTile) {
        //initialize starting and ending nodes to the ones in the 2D Node array
        endNode = nodeArray[endTile.getRow()][endTile.getCol()];
        startNode = nodeArray[startTile.getRow()][startTile.getCol()];

        // sets all the node costs
        initializeNodeCosts();

        //initialize the starting node
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

                // if neighbour is not in open list
                if (!inList(openList, neighbour)) {
                    neighbour.parent = currentNode;
                    openList.add(neighbour);
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
        if (row < nodeArray[0].length - 1) neighbours.add(nodeArray[row + 1][col]); // down
        if (col > 0) neighbours.add(nodeArray[currentNode.row][currentNode.col - 1]); // left
        if (col < nodeArray.length - 1) neighbours.add(nodeArray[currentNode.row][currentNode.col + 1]); // right

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

    private void setCosts (Node currentNode) {
        int xDistance = Math.abs(currentNode.col - startNode.col);
        int yDistance = Math.abs(currentNode.row - startNode.row);
        currentNode.gCost = xDistance + yDistance;

        xDistance = Math.abs(currentNode.col - endNode.col);
        yDistance = Math.abs(currentNode.row - endNode.row);
        currentNode.hCost = xDistance + yDistance;

        currentNode.fCost = currentNode.gCost + currentNode.hCost;
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
