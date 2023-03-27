package edu.wpi.romanticraijuu.pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import static java.lang.Math.abs;

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

    public Path aStarSearch(String startID, String endID) throws Exception{
        Path path = new Path();
        HashMap<String, String> cameFrom = new HashMap<>();
        HashMap<String, Integer> costSoFar = new HashMap<>();
        //TODO
        PriorityQueue<String> pQueue = new PriorityQueue<>();
        pQueue.add(startID, 0);
        String currentNode;
        while(!pQueue.isEmpty()){
            currentNode = pQueue.remove();

            if(currentNode == endID){ break; }

            ArrayList<String> neighbors = findNeighboringNodes(currentNode, endID);
            for (String neighbor : neighbors) {
                int newCost = costSoFar.get(currentNode) + nodeDist(currentNode, neighbor);
                if (costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)){
                    costSoFar.replace(neighbor, newCost);
                    int priority = newCost + hueristic(neighbor, endID);
                    pQueue.add(neighbor, newCost);
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

    private int nodeDist(String currentNodeID, String nextNodeID) throws Exception{
        //finds difference in x,y
        Node currNode = nodes.getNodeByID(currentNodeID);
        Node nextNode = nodes.getNodeByID(nextNodeID);

        int xDif = abs(currNode.getXCoord() - nextNode.getXCoord());
        int yDif = abs(currNode.getYCoord() - nextNode.getYCoord());

        return(xDif + yDif); //returns distance
    }

    //TODO - actually return a hueristic
    private int hueristic(String nodeID, String endID){
        //returns A* hueristic for node
        return(0);
    }

//TODO create this as a helper function
//    private Path cameFromToPath(HashMap<> cameFromList){
//
//    }
}
