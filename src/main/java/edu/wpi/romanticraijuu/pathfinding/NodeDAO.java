package edu.wpi.romanticraijuu.pathfinding;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class NodeDAO {
    private ArrayList<Node> nodes;
    private String tableName;
    private Statement statement;

    public NodeDAO(Connection connection, String tableName) throws SQLException {
        nodes = new ArrayList<Node>();
        connection = connection;
        this.tableName = tableName;
        this.statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

        while (resultSet.next()){
            String nodeID = resultSet.getString("nodeid");
            int xCoord = resultSet.getInt("xcoord");
            int yCoord = resultSet.getInt("ycoord");
            String buildingFloor = resultSet.getString("buildingfloor");
            String building = resultSet.getString("building");
            String nodeType = resultSet.getString("nodetype");
            String longname = resultSet.getString("longname");
            String shortName = resultSet.getString("shortname");

            Node nextNode = new Node(nodeID, xCoord, yCoord, buildingFloor, building, nodeType, longname, shortName);
            nodes.add(nextNode);
        }
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }


    public Node getNodeByID(String nodeID) throws TupleNotFoundException {
        for (Node node : nodes){
            boolean nodesAreSame = node.getNodeID().equals(nodeID);
            if (nodesAreSame)
                return node;
        }
        throw new TupleNotFoundException("Node not found");
    }

    public void deleteNodeByID(String nodeID) throws SQLException, TupleNotFoundException {
        for (Node node : nodes){
            boolean nodesAreSame = node.getNodeID().equals(nodeID);
            if (nodesAreSame){
                statement.executeUpdate("delete from " + tableName + " where nodeid="+nodeID);
                nodes.remove(node);
                return;
            }
        }
        throw new TupleNotFoundException("Node not found");
    }

    public Node modifyNodeByID(String nodeID, int xCoord, int yCoord, String buildingFloor, String building, String nodeType, String longName, String shortName) throws TupleNotFoundException {
        for (Node node : nodes){
            boolean nodesAreSame = node.getNodeID().equals(nodeID);
            if (nodesAreSame){
                statement.executeUpdate("update " + tableName + " set " + "xCoord = " + xCoord + "yCoord = " + yCoord + "buildingFloor = " + buildingFloor + "building = " + building + "nodeType = " + nodeType + "longName = " + longName + "shortName = " + shortName);
                node.setXCoord(xCoord);
                node.setYCoord(yCoord);
                node.setBuildingFloor(buildingFloor);
                node.setBuilding(building);
                node.setNodeType(nodeType);
                node.setLongName(longName);
                node.setShortName(shortName);
                return node;
            }
        }
        throw new TupleNotFoundException("Node not found");
    }

    public Node addNode(String nodeID, int xCoord, int yCoord, String buildingFloor, String building, String nodeType, String longName, String shortName) throws SQLException {
        statement.executeUpdate("insert into " + tableName + "(nodeID, xcoord, ycoord, buildingFloor, building, nodeType, longName, shortName) VALUES ('" + nodeID + "', " + xCoord + ", " + yCoord + ", '" + buildingFloor + "', '" + building + "', " + nodeType + "', " + longName + "', '" + shortName + "');");
        Node node = new Node(nodeID, xCoord, yCoord, buildingFloor, building, nodeType, longName, shortName);
        nodes.add(node);
        return node;
    }

    public LinkedList<Node> getNodesByType(String nodeType){
        LinkedList<Node> returnList = new LinkedList<Node>();
        for (Node node : nodes){
            boolean nodeSameType = node.getNodeType().equals(nodeType);
            if (nodeSameType)
                returnList.add(node);
        }

        return returnList;
    }
}
