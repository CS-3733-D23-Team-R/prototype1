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

public class NodeTypeDAO {
  private ArrayList<String> nodeTypes;
  private String tableName;
  private Statement statement;

  public NodeTypeDAO(Connection connection, String tableName) throws SQLException {
    nodeTypes = new ArrayList<String>();
    connection = connection;
    this.tableName = tableName;
    this.statement = connection.createStatement();

    ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + ";");

    while (resultSet.next()) {
      String nodeType = resultSet.getString("nodetype");
      nodeTypes.add(nodeType);
    }
  }

  public void deleteNodeType(String type) throws SQLException, TupleNotFoundException {
    for (String nodeType : nodeTypes) {
      if (type.equals(nodeType)) {
        statement.executeUpdate("delete from " + tableName + " where nodetype=" + type + ";");
        nodeTypes.remove(type);
        return;
      }
    }
    throw new TupleNotFoundException("Node not found");
  }

  public void addNodeType(String type) throws SQLException {
    statement.executeUpdate("insert into " + tableName + "(nodetype) values(" + type + ");");
    nodeTypes.add(type);
  }

  public void deleteAllNodeTypes() throws SQLException {
    statement.executeUpdate("delete from " + tableName + ";");
    nodeTypes.removeAll(nodeTypes);
  }

  public void readCSV(String filePath) throws SQLException, FileNotFoundException {
    Scanner sc = new Scanner(new File(filePath));
    sc.useDelimiter(",");
    while (sc.hasNextLine()) {
      String nodeType = sc.next();
      statement.executeUpdate("INSERT INTO nodeType(nodeType) VALUES('" + nodeType + "');");
      nodeTypes.add(nodeType);
    }
    sc.close();
  }

  public void writeCSV(String filePath) throws SQLException, IOException {
    ResultSet resultSet = statement.executeQuery("SELECT * FROM nodeType");
    File csvFile = new File(filePath);
    FileWriter outputFileWriter = new FileWriter(csvFile);
    outputFileWriter.write("nodeType");
    while (resultSet.next()) {
      String aNodeType = resultSet.getString("nodeType");
      outputFileWriter.write("\n");
      outputFileWriter.write(aNodeType);
    }
    outputFileWriter.flush();
    outputFileWriter.close();
  }
}
