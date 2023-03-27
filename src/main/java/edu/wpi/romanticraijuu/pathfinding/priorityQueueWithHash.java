package edu.wpi.romanticraijuu.pathfinding;

import java.util.Map;

class priorityQueueWithHash {
    Map<String, Integer> map;
    priorityQueueWithHash(String initialNode, int initalValue){
        this.map = map;
        map.put(initialNode, initalValue);
    }

    void add(String node, int value){
        map.put(node, value);
    }

    String remove(){
        int lowestVal = -1;

    }

}
