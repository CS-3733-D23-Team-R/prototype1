package edu.wpi.romanticraijuu.backendcli;
import java.sql.Connection;
import java.io.*;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Statement;
import edu.wpi.romanticraijuu.pathfinding.*;
public class readCSV {
    public static void readCSVFile(NodeDAO node, String filePath) throws Exception
    {
        //parsing a CSV file into Scanner class constructor
        Statement aStatement = new Statement;
        Scanner sc = new Scanner(new File(filePath));

        while (sc.hasNext())  // TODO; FINISH IMPLEMENTATION
        {
            String line = sc.nextLine();
            node.addNode();
        }
        sc.close();  //closes the scanner
    }

}
