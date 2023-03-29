package edu.wpi.romanticraijuu.pathfinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class EdgeDAO {

  private String tableName;
  private ArrayList<Edge> edges;
  private Statement statement;

  public EdgeDAO(Connection connection, String tableName) throws SQLException {
    edges = new ArrayList<Edge>();
    this.tableName = tableName;
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
    while (resultSet.next()) {
      String edgeID = resultSet.getString("edgeid");
      String startNode = resultSet.getString("startnode");
      String endNode = resultSet.getString("endnode");
      Edge anEdge = new Edge(startNode, endNode, edgeID);
      edges.add(anEdge);
    }
  }

  public ArrayList<Edge> getEdges() {
    return edges;
  }

  public Edge addEdge(String startNodeID, String endNodeID, String edgeID) throws SQLException {
    Edge anEdge = new Edge(edgeID, startNodeID, endNodeID);
    edges.add(anEdge);
    statement.executeUpdate(
        "INSERT INTO "
            + tableName
            + "(edgeID, startNode, endNode) VALUES(`"
            + edgeID
            + "`,`"
            + startNodeID
            + "`,`"
            + endNodeID
            + "`;");
    return anEdge;
  }

  public ArrayList<String> getConnectedNodes(String nodeID) {
    ArrayList<String> aList = new ArrayList<>();
    for (Edge anEdge : edges) {
      String edgeStartNodeID = anEdge.getStartNode();
      String edgeEndNodeID = anEdge.getEndNode();
      if (edgeStartNodeID.equals(nodeID)) {
        aList.add(edgeEndNodeID);
      } else if (edgeEndNodeID.equals(nodeID)) {
        aList.add(edgeStartNodeID);
      }
    }
    return aList;
  }

  public void deleteEdgeByID(String edgeID) throws TupleNotFoundException, SQLException {
    for (Edge edge : edges) {
      boolean edgesAreSame = edge.getEdgeID().equals(edgeID);
      if (edgesAreSame) {
        statement.executeUpdate("delete from " + tableName + " where edgeid=" + edgeID + ";");
        edges.remove(edge);
        return;
      }
    }
    throw new TupleNotFoundException("Edge not found");
  }

  public void deleteAllEdges() throws SQLException {
    statement.executeUpdate("delete from " + tableName + ";");
    edges.removeAll(edges);
  }

  public Edge getEdgeByID(String edgeID) throws TupleNotFoundException {
    for (Edge edge : edges) {
      boolean edgesAreSame = edge.getEdgeID().equals(edgeID);
      if (edgesAreSame) return edge;
    }
    throw new TupleNotFoundException("Node not found");
  }

  public void readCSV(String filePath) throws SQLException, FileNotFoundException {
    Scanner sc = new Scanner(new File(filePath));
    sc.useDelimiter(",");
    while (sc.hasNextLine()) {
      String edgeID = sc.next();
      String startNode = sc.next();
      String endNode = sc.next();
      statement.executeUpdate(
          "INSERT INTO "
              + tableName
              + "(edgeID, startNode, endNode) VALUES ('"
              + edgeID
              + "','"
              + startNode
              + "','"
              + endNode
              + "');");
      Edge anEdge = new Edge(startNode, endNode, edgeID);
      edges.add(anEdge);
    }
    sc.close();
  }

  public void writeCSV(String filePath) throws SQLException, IOException {
    ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
    File csvFile = new File(filePath);
    FileWriter outputFileWriter = new FileWriter(csvFile);
    outputFileWriter.write("edgeID,startNode,endNode");
    while (resultSet.next()) {
      String anEdgeID = resultSet.getString("edgeID");
      String aStartNode = resultSet.getString("startNode");
      String anEndNode = resultSet.getString("endNode");
      outputFileWriter.write("\n");
      outputFileWriter.write(anEdgeID + "," + aStartNode + "," + anEndNode);
    }
    outputFileWriter.flush();
    outputFileWriter.close();
  }
}
