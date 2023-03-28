package edu.wpi.romanticraijuu.backendcli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
                String input = getInput("""
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
        String input = getInput("""
                Enter a number to select database operation:
                1- Display node and edge information
                2- Update node coordinates
                3- Update name of a location node
                4- Export node table into a csv file
                5- Import a csv file into the node table
                6- Display help on how to use this program
                7- Exit the program""", new String[]{"1", "2", "3", "4", "5", "6", "7"});

        switch (input){
            case "1":
                
                break;
            case "2":

                break;
            case "3":

                break;
            case "4":

                break;
            case "5":

                break;
            case "6":

                break;
            case "7":
                return;
        }
    }


    private static String getInput(String prompt, String[] options){
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
}
