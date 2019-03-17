package com.yogeesh.util;

import com.yogeesh.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSMSystemCreator {

    /**
     * Method to desirialize and create FSM
     * @param fms
     * @param eventNameToEventMap
     * @return
     */
    public static Map<Integer, FiniteStateMachine> createFSMSet(String fms, Map<String, Event> eventNameToEventMap) {

//        System.out.println(fms);

        Map<Integer, FiniteStateMachine> finiteStateMachines = new HashMap<>();


        String[] sharededFms = fms.split("\\.");

        // Split different finite States
        for (int i=0; i<sharededFms.length; i++) {

            // printing all FS to debug
//            System.out.println(sharededFms[i]);

            if (sharededFms[i].length()>0) {

                int idOfFs = Character.getNumericValue(sharededFms[i].charAt(0));
                finiteStateMachines.put(idOfFs, null);


                // Realised now ! We dont need both states and edges just edges is enough it has states too
                // Intentionally not fixing this to get MVP ASAP
                String[] statesAndEdges = sharededFms[i].split("\\*");

                for (int j=0; j<statesAndEdges.length; j++) {
                    if (j==1) {
                        // printing all edges to debug
                        //System.out.println(statesAndEdges[j]);

                        String[] eachEge = statesAndEdges[j].split(":");

                        Map<String, Node> nameNodeMapping = new HashMap<>();

                        for (int k=0; k<eachEge.length; k++) {
                            String[] stateEdgeState = eachEge[k].split("\\^");
                            nameNodeMapping.put(stateEdgeState[0], new Node(stateEdgeState[0], new HashMap<>()));

                            String stateName = stateEdgeState[0];
                            String eventName = stateEdgeState[1];
                            String nextNodevalue = stateEdgeState[2];

                            Node nextNode = nameNodeMapping.get(nextNodevalue);
                            Node presentNode = nameNodeMapping.get(stateName);

                            if (null==nextNode) {
                                nameNodeMapping.put(nextNodevalue, new Node(nextNodevalue, new HashMap<Event, Node>()));
                            }


                            if (null==presentNode) {
                                nameNodeMapping.put(stateName, new Node(stateName, new HashMap<Event, Node>()));
                            }

                            // If it was null gets fetched again
                            nextNode = nameNodeMapping.get(nextNodevalue);
                            presentNode = nameNodeMapping.get(stateName);

                            Event event =new Event(eventName);
                            eventNameToEventMap.put(eventName, event);

                            presentNode.eventToNodeMap.put(event, nextNode);

                            // First node of FS
                            if (finiteStateMachines.get(idOfFs)==null) {
//                                System.out.println("State name : "+stateName);
                                FiniteStateMachine finiteStateMachine = new FiniteStateMachine();
                                finiteStateMachine.start = nameNodeMapping.get(stateName);
                                finiteStateMachine.currentState = nameNodeMapping.get(stateName);
                                finiteStateMachines.put(idOfFs, finiteStateMachine);
                            }

                        }
                    }
                }
            }
        }

        return finiteStateMachines;
    }

    /**
     * Method to de serialize producer data
     * @param producers
     * @param finiteStateMachineHashMap
     * @return
     */
    public static Map<Producer, FiniteStateMachine> createProducersToFiniteStateMap(String producers,
                                                                                    Map<Integer, FiniteStateMachine>
                                                                                            finiteStateMachineHashMap) {
        Map<Producer, FiniteStateMachine> producerToStateMachine = new HashMap<>();

        String[] producersMachine = producers.split("\\|");

        for (int i=0; i<producersMachine.length; i++) {

            String[] producerToMachineMap = producersMachine[i].split(",");

            if (producerToMachineMap.length==2) {
                producerToStateMachine.put(new Producer(Integer.valueOf(producerToMachineMap[0])),
                        finiteStateMachineHashMap.get(Integer.valueOf(producerToMachineMap[1])));
            }
        }

        return producerToStateMachine;
    }

    /**
     * Method to de serialize subscriber data
     * @param subscribers
     * @return
     */
    public static Map<Event,List<Subscriber>> creatEventToSubscriberMap(String subscribers) {
        Map<Event,List<Subscriber>> eventToSubscriber = new HashMap<>();

        String[] eventsToSubscriber = subscribers.split("\\|");

        for (int i=0; i<eventsToSubscriber.length; i++) {
            String[] subscriber = eventsToSubscriber[i].split("\\.");

            Event event = new Event(null);
            List<Subscriber> subscribersList = new ArrayList<>();;

            for (int j=0; j<subscriber.length; j++) {

                System.out.println(subscriber[j]);

                if (j==1) {
                    String[] subscribersArray = subscriber[j].split(",");

                    for (int k=0; k<subscribersArray.length; k++) {
                        subscribersList.add(new Subscriber(subscribersArray[k]));
                    }
                } else {
                    event.eventName = subscriber[j];
                }
            }

            eventToSubscriber.put(event, subscribersList);
        }

//        System.out.println(subscribers);

        return eventToSubscriber;

    }
}
