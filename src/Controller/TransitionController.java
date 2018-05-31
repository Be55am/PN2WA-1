package Controller;

import  model.Transition;
import  Views.Position;
import  Views.TransitionView;

public class TransitionController {
    Transition transition;
    TransitionView view;

    public TransitionController(Transition transition){
        this.transition=transition;
        this.view=transition.getTrasitionView();

        view.setOnMouseDragged(event ->{
            view.relocate(event.getSceneX()-190,event.getSceneY()-50);

            Position p=transition.getPosition();
            p.setPositionX(event.getSceneX()-190);
            p.setPositionY(event.getSceneY()-50);
            transition.setPosition(p);

        } );

        view.setOnMouseReleased(event -> {
            Position p=transition.getPosition();
            p.setPositionX(event.getSceneX()-190);
            p.setPositionY(event.getSceneY()-50);
            transition.setPosition(p);
            view.relocate(p.getPositionX(),p.getPositionY());
        });

    }
}
