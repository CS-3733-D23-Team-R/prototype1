package edu.wpi.romanticraijuu.backendcli;

import edu.wpi.romanticraijuu.pathfinding.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class CLI {
  private static Scanner scanner;
  private static NodeDAO nodeDAO;
  private static EdgeDAO edgeDAO;

  public static void cli() {
    scanner = new Scanner(System.in);
    System.out.println("CLI Started");
    System.out.println("Connecting to the database");

    try (Connection connection =
        DriverManager.getConnection(
            "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb", "teamr", "teamr150")) {

      // Initialize backend objects
      NodeTypeDAO nodeTypeDAO = new NodeTypeDAO(connection, "prototype1.nodetype");
      nodeDAO = new NodeDAO(connection, "prototype1.node");
      edgeDAO = new EdgeDAO(connection, "prototype1.edge");

      while (giveCLIOptions()) {}

    } catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }

  private static void runAlgorithmCLI() {}

  private static boolean giveCLIOptions() {
    String input =
        getSpecificInput(
            "Enter a number to select database operation:\n"
                + "1- List Nodes\n"
                + "2- Display pathes between nodes\n"
                + "3- List Edges\n"
                + "4- Display information for node\n"
                + "5- Display information for edge\n"
                + "6- Update node coordinates\n"
                + "7- Update name of a location node\n"
                + "8- Export node table into a csv file\n"
                + "9- Import a csv file into the node table\n"
                + "10- Display help on how to use this program\n"
                + "11- Exit the program",
            new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"});
    String userNodeID;
    String userEdgeID;
    switch (input) {
      case "1": // 1- List Nodes
        System.out.println("\nList of nodes:\n");
        for (Node node : nodeDAO.getNodes()) System.out.println("NodeID: " + node.getNodeID());
        break;
      case "2": // 2- Display pathes between nodes
        String startID = getGeneralInput("Enter starting node ID: ");
        if (!isValidNode(startID)) {
          System.out.println(
              "That node is invalid. Please double check your node and then try again");
          return true;
        }

        String endID = getGeneralInput("Enter ending node ID: ");
        if (!isValidNode(endID)) {
          System.out.println(
              "That node is invalid. Please double check your node and then try again");
          return true;
        }

        Pathfinder pf = new Pathfinder(nodeDAO, edgeDAO);
        try {
          Path path = pf.breadthFirstSearch(startID, endID);

          System.out.println("Path:");
          for (String nodeID : path.getPath()) {
            System.out.println(nodeID);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        break;
      case "3": // 3- List Edges
        System.out.println("\nList of edges:\n");
        for (Edge edge : edgeDAO.getEdges()) System.out.println("EdgeID: " + edge.getEdgeID());
        break;
      case "4": // 4- Display information for node
        userNodeID = getGeneralInput("Enter the NodeID of the desired node");
        if (!isValidNode(userNodeID)) {
          System.out.println(
              "That node is invalid. Please double check your node and then try again");
          return true;
        }

        try {
          Node node = nodeDAO.getNodeByID(userNodeID);
          System.out.println("The node's ID is: " + node.getNodeID());
          System.out.println(
              "The node's coordinates are: (" + node.getXCoord() + ", " + node.getYCoord() + ")");
          System.out.println("The node's floor is: " + node.getBuildingFloor());
          System.out.println("The node's building is: " + node.getBuilding());
          System.out.println("The node's type is: " + node.getNodeType());
          System.out.println("The node's long name is: " + node.getLongName());
          System.out.println("The node's short name is: " + node.getShortName());
        } catch (TupleNotFoundException e) {
          // System.out.println("Node not found. Please double check your information");
          e.printStackTrace();
        }
        break;
      case "5": // 5- Display information for edge
        userEdgeID = getGeneralInput("Enter the EdgeID of the desired edge");
        if (!isValidEdge(userEdgeID)) {
          System.out.println(
              "That edge is invalid. Please double check your edge and then try again");
          return true;
        }

        try {
          Edge edge = edgeDAO.getEdgeByID(userEdgeID);
          System.out.println("The edge's ID is: " + edge.getEdgeID());
          System.out.println("The edge's starting node's ID is: " + edge.getStartNode());
          System.out.println("The edge's ending node's ID is: " + edge.getEndNode());
        } catch (TupleNotFoundException e) {
          // System.out.println("Edge not found. Please double check your information");
          e.printStackTrace();
        }
        break;
      case "6": // 6- Update node coordinates
        userNodeID = getGeneralInput("Enter the NodeID of node you wish to modify");
        if (!isValidNode(userNodeID)) {
          System.out.println(
              "That node is invalid. Please double check your node and then try again");
          return true;
        }

        int xCoord = getIntegerInput("Enter the new X-Coordinate: ");
        int yCoord = getIntegerInput("Enter the new Y-Coordinate: ");
        try {
          nodeDAO.modifyNodeXCoordByID(userNodeID, xCoord);
          nodeDAO.modifyNodeYCoordByID(userNodeID, yCoord);
        } catch (SQLException e) {
          // System.out.println("There was an error with accessing the database");
          e.printStackTrace();
        } catch (TupleNotFoundException e) {
          // System.out.println("Node not found. Please double check your information");
          e.printStackTrace();
        }
        break;
      case "7": // 7- Update name of a location node
        userNodeID = getGeneralInput("Enter the NodeID of node you wish to modify");
        if (!isValidNode(userNodeID)) {
          System.out.println(
              "That node is invalid. Please double check your node and then try again");
          return true;
        }

        String longName = getGeneralInput("Enter the new name");
        try {
          nodeDAO.modifyNodeLongNameByID(userNodeID, longName);
        } catch (SQLException e) {
          // System.out.println("There was an error with accessing the database");
          e.printStackTrace();
        } catch (TupleNotFoundException e) {
          // System.out.println("Node not found. Please double check your information");
          e.printStackTrace();
        }

        break;
      case "8": // 8- Export node table into a csv file
        String outputCSVFilePath = getGeneralInput("Enter the desired output csv file");
        try {
          nodeDAO.writeCSV(outputCSVFilePath);
        } catch (SQLException | IOException e) {
          e.printStackTrace();
        }
        break;
      case "9": // 9- Import a csv file into the node table
        String inputCSVFilePath =
            getGeneralInput("Enter the path to your CSV file containing the new node information");

        try {
          nodeDAO.readCSV(inputCSVFilePath);
        } catch (SQLException | FileNotFoundException e) {
          e.printStackTrace();
        }
        break;
      case "10": // 10- Display help on how to use this program //TODO: FINISH WHEN THE CLI IS
        System.out.println("Help Menu:\n");
        break;
      case "11": // 11- Exit the program
        return false; // exits the program
    }
    return true; // reloads this function/menu
  }

  private static String getSpecificInput(String prompt, String[] options) {
    if (!prompt.equals("")) System.out.println(prompt);

    while (true) {
      String input = scanner.next();

      for (String s : options) {
        if (s.equalsIgnoreCase(input)) return s;
      }
      System.out.println("Input not recognized. Please try again");
    }
  }

  private static String getGeneralInput(String prompt) {
    if (!prompt.equals("")) System.out.println(prompt);

    return scanner.next();
  }

  private static int getIntegerInput(String prompt) {
    if (!prompt.equals("")) System.out.println(prompt);

    return scanner.nextInt();
  }

  private static boolean isValidNode(String nodeID) {
    try {
      nodeDAO.getNodeByID(nodeID);
      return true;
    } catch (TupleNotFoundException e) {
      return false;
    }
  }

  private static boolean isValidEdge(String edgeID) {
    try {
      edgeDAO.getEdgeByID(edgeID);
      return true;
    } catch (TupleNotFoundException e) {
      return false;
    }
  }
}
