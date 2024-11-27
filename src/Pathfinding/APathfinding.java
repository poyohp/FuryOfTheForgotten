package Pathfinding;

import World.Tile;
import java.util.ArrayList;

public class APathfinding {

    public Node[][] nodes;

    public ArrayList<Node> openList = new ArrayList<Node>();
    public ArrayList<Node> checkedList = new ArrayList<Node>();

    public Tile[][] tileArray;
    int maxRows;
    int maxCols;

    public Node startNode;
    public Node endNode;
    public Node currentNode;

    boolean goalReached;
    int steps;
    public ArrayList<Node> shortestPath = new ArrayList<Node>();

    public APathfinding (Tile[][] tileArray) {
        this.tileArray = tileArray;

        this.steps = 0;
        this.goalReached = false;

        maxRows = tileArray.length;
        maxCols = tileArray[0].length;

        initializeNodes();
    }

    public void initializeNodes() {
        nodes = new Node[maxRows][maxCols];

        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                Node node = new Node(tileArray[row][col]);
                nodes[row][col] = node;
            }
        }
    }

    private void resetNodes() {
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                nodes[row][col].walkable = true;
                nodes[row][col].checked = false;
                nodes[row][col].open = false;
            }
        }

        //Reset all lists and variables
        openList.clear();
        checkedList.clear();
        goalReached = false;
        steps = 0;
        shortestPath.clear();
    }

    public void setNodes(Tile startTile, Tile endTile) {

        resetNodes();

        //Setting all the important nodes
        startNode = nodes[startTile.getRow()][startTile.getCol()];
        endNode = nodes[endTile.getRow()][endTile.getCol()];
        currentNode = startNode;

        openList.add(currentNode);

        // Setting values for nodes
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                // If corresponding Tile is walkable, then node is walkable
                if (tileArray[row][col].walkable) nodes[row][col].walkable = true;
                else nodes[row][col].walkable = false;
                setCost(nodes[row][col]);
            }
        }
    }

    private void setCost (Node node) {
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - endNode.col);
        yDistance = Math.abs(node.row - endNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        // Limit of 400 steps ensures that if there is no path, the program stops searching
        while (!goalReached && steps < 400) {
            int row = currentNode.row;
            int col = currentNode.col;

            currentNode.checked = true; // We have already stepped on this node as a best path candidate
            checkedList.add(currentNode);
            openList.remove(currentNode);

            // open neighbours of the current node (only when they are part of the map!)
            if ((row - 1) >= 0) openNode(nodes[row - 1][col]); // up
            if ((row + 1) < maxRows) openNode(nodes[row + 1][col]); // down
            if ((col - 1) >= 0) openNode(nodes[row][col - 1]); // left
            if ((col + 1) < maxCols) openNode(nodes[row][col + 1]); // right

            // If there are no more nodes in the open list, end the loop!
            if (openList.isEmpty()) break;

            currentNode = getLowestFCost(); //Current node is the next most promising node

            if (currentNode == endNode) {
                goalReached = true;
                getShortestPath();
            }
            steps++;
        }
        return goalReached;
    }

    void openNode (Node node) {
        if (!node.open && !node.checked && node.walkable) {
            //If node is walkable, not checked yet, and not opened yet, open it for evaluation
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private Node getLowestFCost() {
        //Takes the first node and sets it up initially to have the lowest cost
        Node bestNode = openList.get(0);
        int bestNodeFCost = 999; //Setting an inital lowest fCost

        for (Node node: openList) {
            //Check for a better fcost
            if (node.fCost < bestNodeFCost) {
                bestNode = node;
                bestNodeFCost = node.fCost;
            } else if (node.fCost == bestNodeFCost) {
                // If there is an equal fcost, get the node with the lower gCost
                if (node.gCost < bestNode.gCost) {
                    bestNode = node;
                }
            }
        }
        return bestNode;
    }

    /**
     * Returns list of nodes with the correct path, the first index being the first node in the path
     */
    private void getShortestPath () {
        Node current = endNode;

        while (current != startNode) {
            shortestPath.add(0, current); // Adds node to the start of the list
            current = current.parent;
        }
    }

}
