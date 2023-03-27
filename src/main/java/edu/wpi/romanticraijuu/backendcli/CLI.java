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
                String str = getInput("""
                         Enter a number to select use case:
                         1- Navigate between locations.
                         2- Access database""", new String[]{"1", "2"});
                switch (str){
                    case "1":
                        runAlgorithmCLI();
                        break;
                    case "2":

                }

            }

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
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

    }
}
