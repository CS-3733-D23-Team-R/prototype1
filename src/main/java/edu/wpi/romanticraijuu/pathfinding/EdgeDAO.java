package edu.wpi.romanticraijuu.pathfinding;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class EdgeDAO {
    @Getter@Setter
    ArrayList<Edge> edges;

    private Statement statement;
    public EdgeDAO(ArrayList<Edge> aList, Connection connection) throws SQLException {
        this.edges = aList;
        Statement statement = connection.createStatement();
    }
    public ArrayList<Edge> getEdges(){
        return edges;
    }
    public void addEdge(String startNodeID, String endNodeID, String edgeID, String tablename) throws SQLException {
        Edge anEdge = new Edge(edgeID, startNodeID, endNodeID);
        edges.add(anEdge);
        statement.executeUpdate("INSERT INTO " + tablename + "(edgeID, startNode, endNode) VALUES(`"+ edgeID+"`,`"+startNodeID+"`,`"+endNodeID+"`");
    }
    public ArrayList<String> getConnectedNodes(Node aNode){
        ArrayList<String> aList = new ArrayList<>();
        for(Edge anEdge:edges){
            String currentNodeID = aNode.getNodeID();
            String currentEdgeID = anEdge.getEdgeID();
            String edgeStartNodeID = anEdge.getStartNode();
            String edgeEndNodeID = anEdge.getEndNode();
            if(currentEdgeID.equals(currentNodeID) && !currentEdgeID.equals(currentNodeID)){
                aList.add(currentEdgeID);
            } else if(edgeStartNodeID.equals(currentNodeID) && !edgeEndNodeID.equals(currentNodeID)){
                aList.add(edgeStartNodeID);
            } else if(edgeEndNodeID.equals(currentNodeID) && !edgeEndNodeID.equals(currentNodeID)){
                aList.add(edgeEndNodeID);
            }
        }
        return aList;
    }
}
