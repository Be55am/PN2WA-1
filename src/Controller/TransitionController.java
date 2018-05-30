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
            view.relocate(event.getSceneX()-20,event.getSceneY()-90);

            Position p=transition.getPosition();
            p.setPositionX(event.getSceneX()-20);
            p.setPositionY(event.getSceneY()-90);
            transition.setPosition(p);

        } );

        view.setOnMouseReleased(event -> {
            Position p=transition.getPosition();
            p.setPositionX(event.getSceneX()-20);
            p.setPositionY(event.getSceneY()-90);
            transition.setPosition(p);
            view.relocate(p.getPositionX(),p.getPositionY());
        });

    }
}
