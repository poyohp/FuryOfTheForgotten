package Pathfinding;

import java.util.ArrayList;

public class MyQueue {

    private ArrayList<Node> data;

    MyQueue() {
        data = new ArrayList<Node>();
    }

    Node dequeue() {
        if (empty()) throw new RuntimeException();

        Node node = data.get(0);
        data.remove(0);
        return node;
    }

    void enqueue(Node item) {
        data.add(item);
    }

    boolean empty() {
        return (data.size() == 0);
    }

    void clear() {
        data.clear();
    }

}

