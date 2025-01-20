package Pathfinding;

import World.Tile;
import java.util.ArrayList;

public class APathfinding {

    public Node[][] nodes;

    // ArrayLists to keep track of nodes
    public ArrayList<Node> openList = new ArrayList<Node>();
    public ArrayList<Node> closedList = new ArrayList<Node>();

    // Used to create Node array from given Tile array
    public Tile[][] tileArray;
    int maxRows;
    int maxCols;

    // Important nodes
    public Node startNode;
    public Node endNode;
    public Node currentNode;

    boolean goalReached; // Whether path is found
    int steps = 0;
    public ArrayList<Node> shortestPath = new ArrayList<Node>(); // Keeps track of shortest path

    public APathfinding (Tile[][] tileArray) {
        this.tileArray = tileArray;

        this.goalReached = false;

        maxRows = tileArray.length;
        maxCols = tileArray[0].length;

        initializeNodes();
    }

    /**
     * Add all tiles to a node array
     */
    public void initializeNodes() {
        nodes = new Node[maxRows][maxCols];

        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                Node node = new Node(tileArray[row][col]);
                nodes[row][col] = node;
            }
        }
    }

    /**
     * Resets all the nodes to start pathfinding again
     * This is needed because pathfinding will be used multiple times in our program!
     */
    private void resetNodes() {
        // Resetting node attributes
        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                nodes[row][col].walkable = true;
                nodes[row][col].closed = false;
                nodes[row][col].open = false;
            }
        }

        //Reset all lists and variables
        openList.clear();
        closedList.clear();
        goalReached = false;
        shortestPath.clear();
    }

    /**
     * Sets the nodes in preparation of searching
     * @param startTile Starting tile
     * @param endTile Ending tile
     */
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

    /**
     * Sets costs for all nodes in the tileset
     * @param node Node to set the cost of
     */
    private void setCost (Node node) {
        // Calculate distance from start node to current node (gCost)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // Calculate distance from end to current node (hCost)
        xDistance = Math.abs(node.col - endNode.col);
        yDistance = Math.abs(node.row - endNode.row);
        node.hCost = xDistance + yDistance;

        // Sum of gCost and hCost is fCost
        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Searches for a path
     * @return returns false if no path is found, true if a path is found
     */
    public boolean findPath() {
        steps = 0;
        // Searches for a path while goal is not reached
        while (!goalReached && steps < 999) {
            int row = currentNode.row;
            int col = currentNode.col;

            // We have stepped on this node already, no need to evaluate it anymore
            currentNode.closed = true;
            closedList.add(currentNode);
            openList.remove(currentNode);

            // open neighbours of the current node (only when they are part of the map!)
            if ((row - 1) >= 0) openNode(nodes[row - 1][col]); // up
            if ((row + 1) < maxRows) openNode(nodes[row + 1][col]); // down
            if ((col - 1) >= 0) openNode(nodes[row][col - 1]); // left
            if ((col + 1) < maxCols) openNode(nodes[row][col + 1]); // right

            /*
             * If there are no more nodes in the open list, end the loop!
             * This prevents forever search for the next tile, if goal node is unreachable
             */
            if (openList.isEmpty()) return false;

            currentNode = getLowestCost(); //Current node is the next most promising node

            // If path has been found, reconstruct the shortest path!
            if (currentNode == endNode) {
                goalReached = true;
                getShortestPath();
            }
            steps++;
        }
        return goalReached;
    }

    /**
     * Opens a node, if possible, and adds it to the open list
     * @param node Node to be opened
     */
    void openNode (Node node) {
        if (node.closed) return;
        if (!node.open && node.walkable) {
            //If node is walkable, not checked yet, and not opened yet, open it for evaluation
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Gets the node with the lowest cost among all the open nodes
     * @return Node with lowest fcost and gcost
     */
    private Node getLowestCost() {
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
     * Makes a list of nodes with the correct path, the first index being the first node in the path
     */
    private void getShortestPath () {
        Node current = endNode;

        // Keep looping until start node is reached
        while (current != startNode) {
            shortestPath.add(0, current); // Adds node to the start of the list
            current = current.parent; // Get the parent of the node to get the next node to add
        }
    }

}
