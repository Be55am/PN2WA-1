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

    public int addArrow(Arrow arrow){
        for (Arrow a:getArrows()) {
            if(a.getStartingShape()==arrow.getStartingShape()&a.getEndingShape()==arrow.getEndingShape()){
                return 0;
            }
        }
        arrows.add(arrow);
        return 1;
    }

    public void deleteShape(Shape shape){
        places.remove(shape);
    }

    public void paint(AnchorPane Anchorpane){
        this.Anchorpane=Anchorpane;


        for (Place place:places){
            place.paint(Anchorpane);
        }
        for(Transition transition:transitions){
            transition.paint(Anchorpane);
        }

        for (Arrow arrow:arrows) {

            arrow.paint(Anchorpane);

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
        Graph.arrows = arrows;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public  void setPlaces(ArrayList<Place> places) {
        Graph.places = places;
    }

    public  void setTransitions(ArrayList<Transition> transitions) {
        Graph.transitions = transitions;
    }

    public  ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void deleteArrow(Arrow arrow){
        arrows.remove(arrow);
    }
}
