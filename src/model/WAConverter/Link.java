package model.WAConverter;

import model.MCGGenerator.Place;
import model.PetriNet.Event;

public class Link {
    private int energy;
    private Event event;
    private Node start;
    private Node end;

    public Link(int energy, Event event, Node start, Node end) {
        this.energy = energy;
        this.event = event;
        this.start = start;
        this.end = end;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public String print(Place unboundedPlace){
        String result= "        <Link>\n" ;
        result+="            <startNode id=\"([";
        for (Place p:this.getStart().getPlaces()) {
            if(!p.getName().equals(unboundedPlace.getName()))
            result+=p.toString()+" ";
        }
        result+="], "+this.getStart().getEnergy()+")\"> </startNode>\n";

        result+="            <endNode id= \"([";
        for (Place p:this.getEnd().getPlaces()) {
            if(!p.getName().equals(unboundedPlace.getName()))
            result+=p.toString()+" ";
        }
        result+="], "+this.getEnd().getEnergy()+")\"> </endNode>\n";
        result+="            <event>"+this.getEvent().getName()+"</event>\n";
        result+="            <Energy>" +this.getEnergy()+"</Energy>\n";
        result+="        </Link>\n";
        return result;
    }
}
