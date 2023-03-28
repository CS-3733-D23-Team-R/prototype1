package edu.wpi.romanticraijuu.pathfinding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NodeTypeDAO {
    private ArrayList<String> nodeTypes;
    private String tableName;
    private Statement statement;

    public NodeTypeDAO(Connection connection, String tableName) throws SQLException {
        nodeTypes = new ArrayList<String>();
        connection = connection;
        this.tableName = tableName;
        this.statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + ";");

        while (resultSet.next()){
            String nodeType = resultSet.getString("nodetype");
            nodeTypes.add(nodeType);
        }
    }

    public void deleteNodeType(String type) throws SQLException, TupleNotFoundException {
        for (String nodeType : nodeTypes){
            if (type.equals(nodeType)){
                statement.executeUpdate("delete from " + tableName + " where nodetype="+type+";");
                nodeTypes.remove(type);
                return;
            }
        }
        throw new TupleNotFoundException("Node not found");
    }

    public void addNodeType(String type) throws SQLException {
        statement.executeUpdate("insert into " + tableName + "(nodetype) values(" + type + ");");
        nodeTypes.add(type);
    }

    public void deleteAllNodeTypes() throws SQLException {
        statement.executeUpdate("delete from " + tableName + ";");
        nodeTypes.removeAll(nodeTypes);
    }
}
