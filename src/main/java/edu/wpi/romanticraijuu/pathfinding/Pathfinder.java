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
    ArrayList<String> toRemove = new ArrayList<>();
    for (String neighbor : neighbors) {
      if (neighbor.equals(endID)) {
        continue;
      }
      Node node = nodes.getNodeByID(neighbor);
      String type = node.getNodeType();
      if (!type.equals("HALL") && !type.equals("ELEV") && !type.equals("STAI")) {
        toRemove.add(neighbor);
      }
    }
    neighbors.removeAll(toRemove);

    return neighbors;
  }

  public Path breadthFirstSearch(String startID, String endID) {
    Path path = new Path();
    HashMap<String, String> cameFrom = new HashMap<>();
    LinkedList<String> queue = new LinkedList<>();
    queue.add(startID);
    String currentNode;
    while (!queue.isEmpty()) {
      currentNode = queue.remove();
      if (currentNode.equals(endID)) {
        break;
      }
      ArrayList<String> neighbors = edges.getConnectedNodes(currentNode);
      // System.out.println("Neighbors: " + neighbors);
      for (String neighbor : neighbors) {
        // System.out.println("Neighbors: " + neighbor);
        if (!cameFrom.containsKey(neighbor)) {
          queue.addLast(neighbor);
          cameFrom.put(neighbor, currentNode);
          // System.out.println("Put " + currentNode + " into cameFrom for " + neighbor);
        }
      }
    }
    // System.out.println("End id: " + endID);
    String currentPathNode = endID;
    while (!currentPathNode.equals(startID)) {
      path.add(currentPathNode);
      currentPathNode = cameFrom.get(currentPathNode);
      // System.out.println(currentPathNode);
    }

    return path;
  }
}
