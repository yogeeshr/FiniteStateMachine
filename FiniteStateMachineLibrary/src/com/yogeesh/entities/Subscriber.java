package com.yogeesh.entities;

public class Subscriber implements EventActionInterface {
    public String name;

    @Override
    public synchronized void listenEvent(Event event) {

        if (event.eventName=="e1") {
            System.out.println("Event Name is X : " + event.eventName);
        } else {
            System.out.println("Event Name is : " + event.eventName);
        }

    }

    public Subscriber(String name) {
        this.name = name;
    }
}
