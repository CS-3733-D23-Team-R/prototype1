package edu.wpi.romanticraijuu.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Pathfinder {
    private NodeDAO nodes;
    private EdgeDAO edges;
    public Pathfinder(NodeDAO nodes, EdgeDAO edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    private ArrayList<String> findNeighboringNodes(String nodeID, String endID) throws Exception {
        // ArrayList<Node> neighbors = new ArrayList<Node>();
        ArrayList<String> neighbors = edges.getConnectedNodes(nodeID);
        for (String neighbor : neighbors) {
            if (neighbor.equals(endID)) {
                continue;
            }
            Node node = nodes.getNodeByID(neighbor);
            String type = node.getNodeType();
            if (!type.equals("HALL") && !type.equals("ELEV") && !type.equals("STAI")) {
                neighbors.remove(neighbor);
            }
        }

        return neighbors;
    }

    public Path breadthFirstSearch(String startID, String endID) throws Exception {
        Path path = new Path();
        HashMap<String, String> cameFrom = new HashMap<>();
        LinkedList<String> queue = new LinkedList<>(findNeighboringNodes(startID, endID));
        String currentNode;
        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            if (currentNode.equals(endID)) {
                break;
            }
            ArrayList<String> neighbors = findNeighboringNodes(currentNode, endID);
            for (String neighbor : neighbors) {
                if (!cameFrom.containsKey(neighbor)) {
                    queue.addLast(neighbor);
                    cameFrom.put(neighbor, currentNode);
                }
            }
        }

        currentNode = endID;
        while (!currentNode.equals(startID)) {
            path.add(currentNode);
            currentNode = cameFrom.get(currentNode);
        }

        return path;
    }
}
