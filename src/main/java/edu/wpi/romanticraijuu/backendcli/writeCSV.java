package edu.wpi.romanticraijuu.backendcli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class writeCSV {
  String filePathSlashFileNameWithoutDotCSV;
  Statement statement;

  public writeCSV(String filePathSlashFileNameWithoutDotCSV, Connection connection)
      throws SQLException {
    this.statement = connection.createStatement();
    this.filePathSlashFileNameWithoutDotCSV = filePathSlashFileNameWithoutDotCSV;
  }

  public void writeFromEdge() throws SQLException, IOException {
    ResultSet resultSet = statement.executeQuery("SELECT * FROM edge");
    File csvFile = new File(filePathSlashFileNameWithoutDotCSV + ".csv");
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

  public void writeFromNode() throws SQLException, IOException {
    ResultSet resultSet = statement.executeQuery("SELECT * FROM node");
    File csvFile = new File(filePathSlashFileNameWithoutDotCSV + ".csv");
    FileWriter outputFileWriter = new FileWriter(csvFile);
    outputFileWriter.write(
        "nodeID,xCoord,yCoord,longName,shortName,building,buildingFloor,nodeType");
    while (resultSet.next()) {
      String aNodeID = resultSet.getString("nodeID");
      int anXCoord = resultSet.getInt("xCoord");
      int aYCoord = resultSet.getInt("yCoord");
      String aLongName = resultSet.getString("longName");
      String aShortName = resultSet.getString("shortName");
      String aBuilding = resultSet.getString("buildng");
      String aBuildingFloor = resultSet.getString("buildingFloor");
      String aNodeType = resultSet.getString("nodeType");
      outputFileWriter.write("\n");
      outputFileWriter.write(
          aNodeID
              + ","
              + anXCoord
              + ","
              + aYCoord
              + ","
              + aLongName
              + ","
              + aShortName
              + ","
              + aBuilding
              + ","
              + aBuildingFloor
              + ","
              + aNodeType);
    }
    outputFileWriter.flush();
    outputFileWriter.close();
  }

  public void writeFromNodeType() throws SQLException, IOException {
    ResultSet resultSet = statement.executeQuery("SELECT * FROM nodeType");
    File csvFile = new File(filePathSlashFileNameWithoutDotCSV + ".csv");
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
