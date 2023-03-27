package edu.wpi.romanticraijuu.backendcli;
import java.sql.Connection;
import java.io.*;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Statement;
import edu.wpi.romanticraijuu.pathfinding.*;
public class readCSV {
    Statement statement;
    String filePathSlashName;
    public static void readCSVFile(String fileName, Connection connection) throws Exception {
        Statement aStatement = connection.createStatement();
    }

    public void importIntoNode() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filePathSlashName));
        sc.useDelimiter(",");
        while (sc.hasNextLine()) {
            String currentLine = sc.nextLine();
            try(Scanner rowScanner = new Scanner(currentLine)){
                rowScanner.useDelimiter(",");
                while(rowScanner.hasNext()){

                }
            }
        }
        sc.close();
    }

    public void importIntoNodeType(){

    }

    public void importIntoEdge(){

    }

}
