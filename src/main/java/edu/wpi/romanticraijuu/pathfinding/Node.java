package edu.wpi.romanticraijuu.pathfinding;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class Node {

    private int xCoord, yCoord;
    private String nodeID, longName, shortName, building, buildingFloor, nodeType;
    public Node(String nodeID, int xCoord, int yCoord, String buildingFloor, String building, String nodeType, String longName, String shortName) {
        this.nodeID = nodeID;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.building = buildingFloor;
        this.buildingFloor = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }


}