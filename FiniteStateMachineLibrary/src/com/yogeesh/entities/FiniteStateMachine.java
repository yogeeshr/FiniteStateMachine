package com.yogeesh.entities;

import java.util.List;
import java.util.Set;

// assuming locks state of a finite statemachine and cannot change state machine
final public class FiniteStateMachine {
    public int id;
    public Node start;
    public Node currentState;
    public Set<Event> events;
    public List<State> states;

    public boolean changeState(Event event) throws IllegalStateException {
        Node node = currentState.eventToNodeMap.get(event);

        if (null==node) {
            new IllegalStateException("This event cannot be consumed as FSM is cannot transition");
        }

        currentState = node;

        return true;
    }
}
