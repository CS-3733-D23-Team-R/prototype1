package edu.wpi.romanticraijuu.pathfinding;

import lombok.Getter;
import lombok.Setter;

public class Edge {
  @Getter @Setter private String edgeID;
  @Getter @Setter private String startNode;
  @Getter @Setter private String endNode;

  public Edge(String startNode, String endNode, String edgeID) {
    this.edgeID = edgeID;
    this.startNode = startNode;
    this.endNode = endNode;
  }
}
