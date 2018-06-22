package model.PetriNet;

import Controller.PetriNetController.TransitionController;
import Views.PetriNet.Position;
import Views.PetriNet.TransitionView;
import javafx.scene.layout.AnchorPane;

public class Transition extends Shape {
    TransitionView trasitionView;
    TransitionController controller;
    private String event;
    /**
     * this class generate the transitions
     * @param position
     * @param name
     */
    public Transition(Position position,String name,String event) {
        super(position,name);
        this.event=event;
        trasitionView=new TransitionView(position,name,event);

         controller=new TransitionController(this);
    }

    /**
     * paint the transition view object in the given pane
     * @param Anchorpane
     */
    public void paint(AnchorPane Anchorpane){
        trasitionView.drow(Anchorpane);
    }

    /**
     *
     * @return the view of this transition
     */
    public TransitionView getTrasitionView() {
        return trasitionView;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void refrech(){
        trasitionView=new TransitionView(this.getPosition(),this.getName(),this.event);
        controller=new TransitionController(this);
    }
}