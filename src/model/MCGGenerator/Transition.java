package model.MCGGenerator;


import model.PetriNet.Event;

import java.io.Serializable;

public class Transition implements Serializable {

    private String name;
    private Event event;

    public Transition(String name,Event e){
        this.name=name;
        this.event=e;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String toString(){
        return this.name+":"+event.getName();
    }
}
