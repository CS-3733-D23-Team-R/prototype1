package edu.wpi.romanticraijuu.backendcli;

public class CLI {
    public static void main(String[] args){
        System.out.println("CLI Started");
        System.out.println("Connecting to the database");
        import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

        public class Main {
            public static void main(String[] args) {
                try (Connection connection = DriverManager.getConnection("jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb", "teamr", "teamr150")) {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM test1.firstTable");
                    while (resultSet.next()) {
                        System.out.printf("%s", resultSet.getString("peopleNames"));
                    }
                } catch (SQLException e) {
                    System.out.println("Connection failure.");
                    e.printStackTrace();
                }
            }
        }
        while (true){
            System.out.println("Enter 1 for ")
        }
    }
}
