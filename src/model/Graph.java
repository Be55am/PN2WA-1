package model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class Graph {

    public static ArrayList<Arrow> arrows=new ArrayList<Arrow>();
    public static ArrayList<Place> places=new ArrayList<Place>();
    public static ArrayList<Transition>transitions=new ArrayList<Transition>();
    private  AnchorPane Anchorpane;

    public void addPlace(Place place){
        places.add(place);
        place.getPosition().connect(this);
    }
    public void addTransition(Transition transition){
        transitions.add(transition);
        transition.getPosition().connect(this);
    }

    public void addArrow(Arrow arrow){
        arrows.add(arrow);
    }

    public void deleteShape(Shape shape){
        places.remove(shape);
    }

    public void paint(AnchorPane Anchorpane){
        this.Anchorpane=Anchorpane;

        for (Arrow arrow:arrows) {

            arrow.paint(Anchorpane);

        }
        for (Place place:places){
            place.paint(Anchorpane);
        }
        for(Transition transition:transitions){
            transition.paint(Anchorpane);
        }
    }

    public void refrech(){
        for (Arrow arrow:arrows) {
            arrow.relocate(Anchorpane);
        }
        /*
        for (Place place:places){
            place.paint(pane);
        }
        for(Transition transition:transitions){
            transition.paint(pane);
        }
        */

    }



    public ArrayList<Arrow> getArrows() {
        return arrows;
    }

    public void setArrows(ArrayList<Arrow> arrows) {
        this.arrows = arrows;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public static void setPlaces(ArrayList<Place> places) {
        Graph.places = places;
    }

    public static void setTransitions(ArrayList<Transition> transitions) {
        Graph.transitions = transitions;
    }

    public static ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void deleteArrow(Arrow arrow){
        arrows.remove(arrow);
    }
}
