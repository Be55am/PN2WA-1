package model;

import Controller.TransitionController;
import Views.Position;
import Views.TransitionView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Transition extends Shape {
    TransitionView trasitionView;
    /**
     * this class generate the transitions
     * @param position
     * @param name
     */
    public Transition(Position position,String name,int weight) {
        super(position,name,weight);
        trasitionView=new TransitionView(position,name);

        TransitionController controller=new TransitionController(this);
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
}