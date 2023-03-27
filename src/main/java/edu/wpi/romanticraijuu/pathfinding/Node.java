package edu.wpi.romanticraijuu.pathfinding;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class Node {
    private enum floor {
        L1, L2
    }
    private  enum nodeType {
        CONF, DEPT, HALL, LABS, REST, RETL, SERV, ELEV, EXIT, STAI
    }
    private int xCoord, yCoord;
    private String nodeID, longName, shortName, building;
    private floor buildingFloor;
    private nodeType nodeType;
    public Node(String nodeID, int xCoord, int yCoord, String longName, String shortName, String building, nodeType aType, floor aFloor) {
        this.nodeID = nodeID;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.longName = longName;
        this.shortName = shortName;
        this.building = building;
        this.buildingFloor = aFloor;
        this.nodeType = aType;
    }


}