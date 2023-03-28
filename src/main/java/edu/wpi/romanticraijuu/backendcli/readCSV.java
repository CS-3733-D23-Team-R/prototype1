package edu.wpi.romanticraijuu.backendcli;
import java.sql.Connection;
import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
public class readCSV {
    Statement statement;
    String filePathSlashName;
    public void readCSVFile(String filePathSlashName, Connection connection) throws Exception {
        Statement aStatement = connection.createStatement();
        this.filePathSlashName = filePathSlashName;
    }

    public void importIntoNode() throws FileNotFoundException, SQLException { //does .next() move the scanner to the next character?
        Scanner sc = new Scanner(new File(filePathSlashName));
        sc.useDelimiter(",");
        while (sc.hasNextLine()) {
            String nodeID = sc.next();
            int xCoord = sc.nextInt();
            int yCoord = sc.nextInt();
            String longName = sc.next();
            String shortName = sc.next();
            String building = sc.next();
            String buildingFloor = sc.next();
            String nodeType = sc.next();
            statement.executeUpdate("insert into node"+ "(nodeID, xcoord, ycoord, buildingFloor, building, nodeType, longName, shortName) VALUES ('" + nodeID + "', " + xCoord + ", " + yCoord + ", '" + buildingFloor + "', '" + building + "', " + nodeType + "', " + longName + "', '" + shortName + "');");
        }
        sc.close();
    }

    public void importIntoNodeType() throws FileNotFoundException, SQLException { //does .next() move the scanner to the next character?
        Scanner sc = new Scanner(new File(filePathSlashName));
        sc.useDelimiter(",");
        while(sc.hasNextLine()){
            String nodeType = sc.next();
            statement.executeUpdate("INSERT INTO nodeType(nodeType) VALUES('"+nodeType+"');");
        }
        sc.close();
    }


    public void importIntoEdge() throws FileNotFoundException, SQLException { //does .next() move the scanner to the next character?
        Scanner sc = new Scanner(new File(filePathSlashName));
        sc.useDelimiter(",");
        while(sc.hasNextLine()){
            String edgeID = sc.next();
            String startNode = sc.next();
            String endNode = sc.next();
            statement.executeUpdate("INSERT INTO edge(edgeID, startNode, endNode) VALUES ('"+edgeID+"','"+startNode+"','"+endNode+"');");
        }
        sc.close();
    }

}
