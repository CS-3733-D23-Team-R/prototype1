package edu.wpi.romanticraijuu.pathfinding;

import java.util.ArrayList;
import lombok.Getter;

public class Path {
  @Getter private ArrayList<String> path;

  Path() {
    path = new ArrayList<String>();
  }

  void add(String nodeID) {
    path.add(0, nodeID);
  }
}
