package model;

import MCGGeneration.Transition;

import java.io.Serializable;
import java.util.ArrayList;

public  class Event implements Serializable {

    private String name;
    private  ArrayList<Transition> transitions;

    public Event(String name){
        this.name=name;
        transitions=new ArrayList<>();
    }


    public void addTransition(Transition t){
        transitions.add(t);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public  void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }
}
