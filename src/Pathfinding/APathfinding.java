package Pathfinding;

import Handlers.LevelHandler;

import java.awt.*;

public class APathfinding {
    Node[][] nodes;

    APathfinding(LevelHandler levelHandler) {
        int maxCols = levelHandler.getCurrentLevel().getMap().getMapWidth();
        int maxRows = levelHandler.getCurrentLevel().getMap().getMapHeight();
        nodes = new Node[maxCols][maxRows];
//        random code to be used/implemented later
//        this.setLayout(new GridLayout(maxRows, maxCols));
//
//        for (int row = 0; row < maxRows; row++) {
//            for (int col = 0; col < maxCols; col++) {
//                nodes[col][row] = new Node(col, row);
//                this.add(nodes[col][row]);
//            }
//        }
    }
}
