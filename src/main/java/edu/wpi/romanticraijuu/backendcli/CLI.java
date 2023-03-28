package edu.wpi.romanticraijuu.backendcli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import edu.wpi.romanticraijuu.pathfinding.*;

public class CLI {
    private static Scanner scanner;
    private static NodeDAO nodeDAO;
    private static EdgeDAO edgeDAO;
    public static void cli(String[] args){
        scanner = new Scanner(System.in);
        System.out.println("CLI Started");
        System.out.println("Connecting to the database");

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb", "teamr", "teamr150")) {

            //Initialize backend objects
            NodeTypeDAO nodeTypeDAO = new NodeTypeDAO(connection, "prototype1.nodetype");
            nodeDAO = new NodeDAO(connection, "prototype1.node");
            edgeDAO = new EdgeDAO(connection, "prototype1.edge");

            while (true){
                String input = getSpecificInput("""
                         Enter a number to select use case:
                         1- Navigate between locations.
                         2- Access database
                         3- Exit the program""", new String[]{"1", "2"});
                switch (input){
                    case "1":
                        runAlgorithmCLI();
                        break;
                    case "2":
                        runDatabaseCLI();
                        break;
                    case "3":
                        return;
                }

            }

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    private static void runAlgorithmCLI() {
        System.out.println("Enter starting node ID: ");
        String startID = scanner.next();
        System.out.println("Enter ending node ID: ");
        String endID = scanner.next();
        Pathfinder pf = new Pathfinder(nodeDAO, edgeDAO);
        try {
            Path path = pf.breadthFirstSearch(startID, endID);

            System.out.println("Path:");
            for(String nodeID : path.getPath()) {
                System.out.println(nodeID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runDatabaseCLI(){
        String input = getSpecificInput("""
                Enter a number to select database operation:
                1- List Nodes
                2- List Edges
                3- Display information for node
                4- Display information for edge
                5- Update node coordinates
                6- Update name of a location node
                7- Export node table into a csv file
                8- Import a csv file into the node table
                9- Display help on how to use this program
                10- Exit the program""", new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});

        switch (input){
            case "1":
                System.out.println("\nList of nodes:\n");
                for (Node node : nodeDAO.getNodes())
                    System.out.println("NodeID: " + node.getNodeID());
                break;
            case "2":
                System.out.println("\nList of edges:\n");
                for (Edge edge : edgeDAO.getEdges())
                    System.out.println("EdgeID: " + edge.getEdgeID());
                break;
            case "3":
                String userNode = getGeneralInput("Enter the NodeID of the desired node");
                try{
                    Node node = nodeDAO.getNodeByID(userNode);
                    System.out.println("The node's ID is: " + node.getNodeID());
                    System.out.println("The node's coordinates are: (" + node.getXCoord() + ", " + node.getYCoord() + ")");
                    System.out.println("The node's floor is: " + node.getBuildingFloor());
                    System.out.println("The node's building is: " + node.getBuilding());
                    System.out.println("The node's type is: " + node.getNodeType());
                    System.out.println("The node's long name is: " + node.getLongName());
                    System.out.println("The node's short name is: " + node.getShortName());
                } catch (TupleNotFoundException e) {
                    System.out.println("Node not found. Please double check your information");
                }
                break;
            case "4":
                String userEdge = getGeneralInput("Enter the EdgeID of the desired edge");
                try{
                    Edge edge = edgeDAO.getEdgeByID(userEdge);
                    System.out.println("The edge's ID is: " + edge.getEdgeID());
                    System.out.println("The edge's starting node's ID is: " + edge.getStartNode());
                    System.out.println("The edge's ending node's ID is: " + edge.getEndNode());
                } catch (TupleNotFoundException e) {
                    System.out.println("Edge not found. Please double check your information");
                }
                break;
            case "5":
                String userNodeCoords = getGeneralInput("Enter the NodeID of node you wish to modify");
                break;
            case "6":

                break;
            case "7":
            case "8":
            case "9":
            case "10":
                return;
        }
    }


    private static String getSpecificInput(String prompt, String[] options){
        if (!prompt.equals(""))
            System.out.println(prompt);

        while (true){
            String input = scanner.next();

            for (String s : options ){
                if (s.equalsIgnoreCase(input))
                    return s;
            }
            System.out.println("Input not recognized. Please try again");
        }
    }

    private static String getGeneralInput(String prompt){
        if (!prompt.equals(""))
            System.out.println(prompt);

        return scanner.next();
    }
}
