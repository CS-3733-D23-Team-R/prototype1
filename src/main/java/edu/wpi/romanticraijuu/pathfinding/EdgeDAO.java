package edu.wpi.romanticraijuu.pathfinding;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class EdgeDAO {

    String tablename;
    ArrayList<Edge> edges;
    private Statement statement;
    public EdgeDAO(ArrayList<Edge> aList, Connection connection, String tablename) throws SQLException {
        this.tablename = tablename;
        this.edges = aList;
        Statement statement = connection.createStatement();
    }
    public ArrayList<Edge> getEdges(){
        return edges;
    }
    public Edge addEdge(String startNodeID, String endNodeID, String edgeID) throws SQLException {
        Edge anEdge = new Edge(edgeID, startNodeID, endNodeID);
        edges.add(anEdge);
        statement.executeUpdate("INSERT INTO " + tablename + "(edgeID, startNode, endNode) VALUES(`"+ edgeID+"`,`"+startNodeID+"`,`"+endNodeID+"`");
        return anEdge;
    }
    public ArrayList<String> getConnectedNodes(String nodeID){
        ArrayList<String> aList = new ArrayList<>();
        for(Edge anEdge:edges) {
            String edgeStartNodeID = anEdge.getStartNode();
            String edgeEndNodeID = anEdge.getEndNode();
            if(edgeStartNodeID.equals(nodeID) || edgeEndNodeID.equals(nodeID)) {
                aList.add(edgeStartNodeID);
            }
        }
        return aList;
    }
}
