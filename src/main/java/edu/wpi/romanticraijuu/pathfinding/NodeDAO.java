package edu.wpi.romanticraijuu.pathfinding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    while (resultSet.next()) {
      String nodeID = resultSet.getString("nodeid");
      int xCoord = resultSet.getInt("xcoord");
      int yCoord = resultSet.getInt("ycoord");
      String buildingFloor = resultSet.getString("buildingfloor");
      String building = resultSet.getString("building");
      String nodeType = resultSet.getString("nodetype");
      String longname = resultSet.getString("longname");
      String shortName = resultSet.getString("shortname");

      Node nextNode =
          new Node(nodeID, xCoord, yCoord, buildingFloor, building, nodeType, longname, shortName);
      nodes.add(nextNode);
    }
  }

  public ArrayList<Node> getNodes() {
    return nodes;
  }

  public Node getNodeByID(String nodeID) throws TupleNotFoundException {
    for (Node node : nodes) {
      boolean nodesAreSame = node.getNodeID().equals(nodeID);
      if (nodesAreSame) return node;
    }
    throw new TupleNotFoundException("Node not found");
  }

  public void deleteNodeByID(String nodeID) throws SQLException, TupleNotFoundException {
    for (Node node : nodes) {
      boolean nodesAreSame = node.getNodeID().equals(nodeID);
      if (nodesAreSame) {
        statement.executeUpdate("delete from " + tableName + " where nodeid=" + nodeID + ";");
        nodes.remove(node);
        return;
      }
    }
    throw new TupleNotFoundException("Node not found");
  }

  //    public Node modifyNodeByID(String nodeID, int xCoord, int yCoord, String buildingFloor,
  // String building, String nodeType, String longName, String shortName) throws
  // TupleNotFoundException, SQLException {
  //        for (Node node : nodes){
  //            boolean nodesAreSame = node.getNodeID().equals(nodeID);
  //            if (nodesAreSame){
  //                statement.executeUpdate("update " + tableName + " set " + "xCoord = " + xCoord +
  // "yCoord = " + yCoord + "buildingFloor = " + buildingFloor + "building = " + building +
  // "nodeType = " + nodeType + "longName = " + longName + "shortName = " + shortName + ";");
  //                node.setXCoord(xCoord);
  //                node.setYCoord(yCoord);
  //                node.setBuildingFloor(buildingFloor);
  //                node.setBuilding(building);
  //                node.setNodeType(nodeType);
  //                node.setLongName(longName);
  //                node.setShortName(shortName);
  //                return node;
  //            }
  //        }
  //        throw new TupleNotFoundException("Node not found");
  //    }

  public Node modifyNodeXCoordByID(String nodeID, int xCoord)
      throws TupleNotFoundException, SQLException {
    Node node = this.getNodeByID(nodeID);
    statement.executeUpdate("update " + tableName + " set xCoord=" + xCoord + ";");
    node.setXCoord(xCoord);
    return node;
  }

  public Node modifyNodeYCoordByID(String nodeID, int yCoord)
      throws TupleNotFoundException, SQLException {
    Node node = this.getNodeByID(nodeID);
    statement.executeUpdate("update " + tableName + " set yCoord=" + yCoord + ";");
    node.setYCoord(yCoord);
    return node;
  }

  public Node modifyNodeFloorByID(String nodeID, String buildingFloor)
      throws TupleNotFoundException, SQLException {
    Node node = this.getNodeByID(nodeID);
    statement.executeUpdate("update " + tableName + " set buildingFloor=" + buildingFloor + ";");
    node.setBuildingFloor(buildingFloor);
    return node;
  }

  public Node modifyNodeBuildingByID(String nodeID, String building)
      throws TupleNotFoundException, SQLException {
    Node node = this.getNodeByID(nodeID);
    statement.executeUpdate("update " + tableName + " set building=" + building + ";");
    node.setBuilding(building);
    return node;
  }

  public Node modifyNodeTypeByID(String nodeID, String nodeType)
      throws TupleNotFoundException, SQLException {
    Node node = this.getNodeByID(nodeID);
    statement.executeUpdate("update " + tableName + " set nodetype=" + nodeType + ";");
    node.setNodeType(nodeType);
    return node;
  }

  public Node modifyNodeLongNameByID(String nodeID, String longName)
      throws TupleNotFoundException, SQLException {
    Node node = this.getNodeByID(nodeID);
    statement.executeUpdate("update " + tableName + " set longName=" + longName + ";");
    node.setLongName(longName);
    return node;
  }

  public Node modifyNodeShortNameByID(String nodeID, String shortName)
      throws TupleNotFoundException, SQLException {
    Node node = this.getNodeByID(nodeID);
    statement.executeUpdate("update " + tableName + " set shortName=" + shortName + ";");
    node.setShortName(shortName);
    return node;
  }

  public Node addNode(
      String nodeID,
      int xCoord,
      int yCoord,
      String buildingFloor,
      String building,
      String nodeType,
      String longName,
      String shortName)
      throws SQLException {
    statement.executeUpdate(
        "insert into "
            + tableName
            + "(nodeID, xcoord, ycoord, buildingFloor, building, nodeType, longName, shortName) VALUES ('"
            + nodeID
            + "', "
            + xCoord
            + ", "
            + yCoord
            + ", '"
            + buildingFloor
            + "', '"
            + building
            + "', "
            + nodeType
            + "', "
            + longName
            + "', '"
            + shortName
            + "');");
    Node node =
        new Node(nodeID, xCoord, yCoord, buildingFloor, building, nodeType, longName, shortName);
    nodes.add(node);
    return node;
  }

  public LinkedList<Node> getNodesByType(String nodeType) {
    LinkedList<Node> returnList = new LinkedList<Node>();
    for (Node node : nodes) {
      boolean nodeSameType = node.getNodeType().equals(nodeType);
      if (nodeSameType) returnList.add(node);
    }

    return returnList;
  }

  public void deleteAllNodes() throws SQLException {
    statement.executeUpdate("delete from " + tableName + ";");
    nodes.removeAll(nodes);
  }
}
