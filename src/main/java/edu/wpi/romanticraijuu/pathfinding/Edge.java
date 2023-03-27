package edu.wpi.romanticraijuu.pathfinding;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Edge {
    private String edgeID;
    private Node startNode;
    private Node endNode;

    public Edge(Node startNode, Node endNode, String edgeID){
        this.edgeID = edgeID;
        this.startNode = startNode;
        this.endNode = endNode;
    }
}
