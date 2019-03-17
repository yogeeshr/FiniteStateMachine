package com.yogeesh.entities;

public class Event {
    // Asuumption : Unique event Names
    public String eventName;

    public Event(String EventName) {
        this.eventName = EventName;
    }

    @Override
    public int hashCode()
    {
        return eventName.hashCode();
    }

    @Override
    public boolean equals(Object eventTemp)
    {
        return eventName.equalsIgnoreCase(((Event)eventTemp).eventName);
    }
}
