package edu.wpi.romanticraijuu.pathfinding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NodeTypeDAO {
    private ArrayList<String> nodeTypes;
    private Connection connection;
    private String tablename;
    private Statement statement;

    public NodeTypeDAO(Connection connection, String tablename) throws SQLException {
        nodeTypes = new ArrayList<String>();
        this.connection = connection;
        this.tablename = tablename;
        this.statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tablename);

        while (resultSet.next()){
            String nodeType = resultSet.getString("nodetype");
            nodeTypes.add(nodeType);
        }
    }

    public void deleteNodeType(String type) throws SQLException, Exception {
        for (String nodeType : nodeTypes){
            if (type.equals(nodeType)){
                statement.executeUpdate("delete from " + tablename + " where nodetype="+type);
                nodeTypes.remove(type);
                return;
            }
        }
        throw new Exception("Node not found");
    }

    public void addNodeType(String type) throws SQLException {
        statement.executeUpdate("insert into " + tablename + "(nodetype) values(" + type + ");");
        nodeTypes.add(type);
    }
}
