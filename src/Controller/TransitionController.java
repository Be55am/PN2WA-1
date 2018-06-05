package Controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import model.Arrow;
import model.Graph;
import  model.Transition;
import  Views.Position;
import  Views.TransitionView;

import java.util.ArrayList;
import java.util.Optional;

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


        view.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.SECONDARY){
                ContextMenu contextMenu=new ContextMenu();
                MenuItem reName=new MenuItem("Rename...");
                MenuItem setEvent=new MenuItem("Set Event...");
                MenuItem delete=new MenuItem("Delete");
                contextMenu.getItems().addAll(reName,setEvent,new SeparatorMenuItem(),delete);
                contextMenu.show(view,event.getScreenX(),event.getScreenY());

                reName.setOnAction(event1 -> reName());
                delete.setOnAction(event1 -> delete());
                setEvent.setOnAction(event1 -> setEvent());
            }
            if(AnchoreController.arrowButton) {
                AnchoreController.paintArrow(this.transition);
            }

        });

    }

    public void reName(){
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Rename");
        dialog.setHeaderText("Renaming : "+transition.getName());
        dialog.setContentText("Enter new Name : ");
        Optional<String> result= dialog.showAndWait();

        result.ifPresent(name -> transition.setName(name));
        transition.refrech();


        AnchoreController.staticAnchorPane.getChildren().clear();
        AnchoreController.graph.paint(AnchoreController.staticAnchorPane);


    }

    public void delete(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("this item will be deleted !");
        alert.setContentText("Are you sure you want to delete this item ? ");
        Optional<ButtonType>result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            //the user chose o  k

            Graph.transitions.remove(transition);

            ArrayList<Arrow> deletedArrows=new ArrayList<>();


            for (Arrow a:Graph.arrows) {
                if(a.getStartingShape()==transition||a.getEndingShape()==transition){
                    System.out.println("arrwo deleted");
                    AnchoreController.staticAnchorPane.getChildren().remove(a.getArrowView());
                    deletedArrows.add(a);

                }
            }
            for (Arrow a:deletedArrows) {
                AnchoreController.graph.deleteArrow(a);
            }
            AnchoreController.staticAnchorPane.getChildren().clear();
            AnchoreController.graph.paint(AnchoreController.staticAnchorPane);

        }

    }

    private void setEvent(){
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Setting event ");
        dialog.setContentText("enter your Event : ");
        //todo there is an exception should be captured , the case of the marking is not a String
        Optional<String> result=dialog.showAndWait();

        if(result.isPresent()){

            //AnchoreController.graph.deleteShape(place);
            transition.setEvent(result.get());
            transition.refrech();

            AnchoreController.staticAnchorPane.getChildren().clear();
            AnchoreController.graph.paint(AnchoreController.staticAnchorPane);
        }
    }
}
