package model;

import MCGGeneration.Transition;

import java.io.Serializable;
import java.util.ArrayList;

public  class Event implements Serializable {

    private String name;
    private Transition transitions;

    public Event(String name){
        this.name=name;

    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public  void setTransitions(Transition transitions) {
        this.transitions = transitions;
    }
}
