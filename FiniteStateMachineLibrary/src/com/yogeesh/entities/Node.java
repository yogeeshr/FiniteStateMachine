package com.yogeesh.entities;

import java.util.Map;

public class Node {
    public String stateName;
    public Map<Event, Node> eventToNodeMap;

    public Node(String name, Map<Event, Node> eventMap) {
        stateName = name;
        this.eventToNodeMap = eventMap;
    }
}
