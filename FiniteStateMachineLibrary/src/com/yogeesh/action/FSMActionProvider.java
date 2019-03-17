package com.yogeesh.action;

import com.yogeesh.entities.Event;
import com.yogeesh.entities.FiniteStateMachine;
import com.yogeesh.entities.Node;
import com.yogeesh.entities.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FSMActionProvider {

    // * * Thread safe method to consume event * *
    public synchronized static void consumeEvent(Event event, Producer producer, Map<Producer,
            FiniteStateMachine> producerFiniteStateMachineMap) throws IllegalStateException {

        FiniteStateMachine finiteStateMachine = producerFiniteStateMachineMap.get(producer);
        finiteStateMachine.changeState(event);

        // notify all subscribers
    }

    /**
     * Method to traverse FSM
     * @param i
     * @param finiteStateMap
     */
    public static void travelFSM(int i, Map<Integer,FiniteStateMachine> finiteStateMap) {
        System.out.println("FS : "+i);
        NChildPostOrderTraversal(finiteStateMap.get(i).start);
    }

    /**
     * Method to print / valid state machine constructed | TODO : INCOMPLETE
     * @param node
     */
    public static void NChildPostOrderTraversal(Node node) {

        System.out.println("Node Name : "+node.stateName);

        List<Node> nodeList = new ArrayList<>();

        for (Event event: node.eventToNodeMap.keySet()) {
            Event eventKey = (Event) event;
            System.out.println("On event : "+event.eventName+ " moves to state : "+node.eventToNodeMap.get(eventKey.eventName));
            nodeList.add(node.eventToNodeMap.get(eventKey.eventName));
        }

        for (Node node1: nodeList) {
            NChildPostOrderTraversal(node1);
        }
    }
}
