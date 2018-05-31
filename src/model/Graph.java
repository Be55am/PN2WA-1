package model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public  class Graph {

    public static  ArrayList<Arrow> arrows=new ArrayList<>();
    public static  ArrayList<Place> places=new ArrayList<>();
    public static  ArrayList<Transition>transitions=new ArrayList<>();
    private  AnchorPane Anchorpane;
    private  String name;

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

    public  void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public  void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public  ArrayList<Transition> getTransitions() {
        return this.transitions;
    }

    public void deleteArrow(Arrow arrow){
        arrows.remove(arrow);
    }
}
