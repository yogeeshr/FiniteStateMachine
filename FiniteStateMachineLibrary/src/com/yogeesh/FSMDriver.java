package com.yogeesh;

import com.yogeesh.action.FSMActionProvider;
import com.yogeesh.entities.*;
import com.yogeesh.util.FSMSystemCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FSMDriver {

    /**
     * Assumptions
     *
     * 1. A producer can produce on only one FSM
     * 2. There can be multiple FSMs in the system
     * 3. Subscriber Can subscribe to All Events or set of Events
     */
    Map<Integer, FiniteStateMachine> finiteStateMap;


    Map<Integer, Producer> idToProducerMap;
    Map<String, Subscriber> idToSubscriberMap;

    Map<Producer, FiniteStateMachine> producerFiniteStateMachineMap;

    Map<Event, List<Subscriber>> eventSubscriberMap;

     Map<String, Event> eventNameToEventMap;

    /**
     * Constructor creating the systems
     * @param fsms
     * @param producers
     * @param events
     */
    public FSMDriver(String fsms, String producers, String events) {

        eventNameToEventMap = new HashMap<>();

        finiteStateMap = FSMSystemCreator.createFSMSet(fsms, eventNameToEventMap);

         //Print finiteMachines
        System.out.println(". . FSM constructed . . ");
        for (Object key: finiteStateMap.keySet()) {
            Integer keyValue = (Integer) key;
            System.out.println(keyValue+ " "+ finiteStateMap.get(keyValue).start.stateName);
        }
        System.out.println("No of FSM "+finiteStateMap.size());

        producerFiniteStateMachineMap = FSMSystemCreator.createProducersToFiniteStateMap(producers, finiteStateMap);

        // Print producers
        System.out.println(". . Producers constructed . . ");
        idToProducerMap = new HashMap<>();
        for (Object key: producerFiniteStateMachineMap.keySet()) {
            Producer keyValue = (Producer) key;
            System.out.println(" P : " + keyValue.id + "|" +producerFiniteStateMachineMap.get(keyValue).start.stateName);
            idToProducerMap.put(keyValue.id, keyValue);
        }
        System.out.println("No of Producers " + producerFiniteStateMachineMap.size());

        eventSubscriberMap = FSMSystemCreator.creatEventToSubscriberMap(events);

        // Print Subscribers
        idToSubscriberMap = new HashMap<>();
        System.out.println(". . Event constructed . . ");
        for (Object key: eventSubscriberMap.keySet()) {
            Event keyValue = (Event) key;
            System.out.println("Event :"+ keyValue.eventName + " | " + eventSubscriberMap.get(keyValue).size());
            for (Subscriber subscriber: eventSubscriberMap.get(keyValue)) {
                idToSubscriberMap.put(subscriber.name, subscriber);
            }
        }
        System.out.println("No of Events " + eventSubscriberMap.size() );
    }

    /**
     * Method to consume event from producer
     * @param i
     * @param e1
     */
    private void consumeEventFromProducer(int i, String e1) {
        FSMActionProvider.consumeEvent(eventNameToEventMap.get(e1), idToProducerMap.get(i), producerFiniteStateMachineMap);
        Event event = eventNameToEventMap.get(e1);

        List<Subscriber> subscribers = eventSubscriberMap.get(event);

        System.out.println("I am notifying subscribers");

        if (null==subscribers) {
            subscribers = new ArrayList<>();
        }

        // Handling case of all events subscribed Subscriber
        List<Subscriber> subscribersAll = eventSubscriberMap.get("ALL");

        if (null!=subscribersAll) {
            subscribers.addAll(subscribersAll);
        }

        // Notifying subscriber
        for (EventActionInterface eventNotify: subscribers) {
            eventNotify.listenEvent(event);
        }

    }

    private void travelFiniteState(int i) {
        FSMActionProvider.travelFSM(i, finiteStateMap);
    }

    /**
     * Main driver method
     * @param args
     */
    public static void main(String[] args) {

        // TODO : Explain this config
        String FiniteStateMachines = "1(A|B|C|D|E*A^e1^B:B^e2^D:D^e3^E:A^e4^C:C^e5^E:E^e6^F).2" +
                "(L|M|N|O|P|Q*L^e11^M:M^e12^N:N^e17^P:L^e13^O:O^e15^M:M^e16^O:O^e14^N:N^e17^P))";

        // ProducerId.FiniteStateId
        String producerToFiniteState = "1,1|2,2";

        // SubscriberId.Event
        String subscriberIdEvent = "e1.1,2|e17.3,4|ALL.5";

        // Do validations

        // Create a system with n finite state machine, producer, subscriber
        FSMDriver fsmDriver = new FSMDriver(FiniteStateMachines, producerToFiniteState, subscriberIdEvent);
        fsmDriver.consumeEventFromProducer(1, "e1");

//        fsmDriver.travelFiniteState(1);
    }

}
