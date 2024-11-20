package Pathfinding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Node extends JButton implements ActionListener {
    public int col, row;
    public int gCost, hCost, fCost;
    public boolean isCollidable;
    Node parent;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
        this.isCollidable = false;
        this.setBackground(Color.WHITE);
        this.addActionListener(this);
    }

    public void getFCost () {

        this.fCost = this.gCost + this.hCost;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(col + " " + row);
    }
}
