package Controller.PetriNetController;


import Views.PetriNet.ArrowView;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import model.PetriNet.Arrow;

import java.util.ArrayList;
import java.util.Optional;

public class ArrowController {
    private Arrow arrow;
    private ArrowView view;

    public ArrowController(Arrow arrow){
        this.arrow=arrow;
        this.view=arrow.getArrowView();

        view.setOnMouseClicked(event -> {   System.out.println("arrow clicked");
            if(event.getButton()== MouseButton.SECONDARY){

                //creating a ContextMenu
                ContextMenu contextMenu=new ContextMenu();

                MenuItem setWeight=new MenuItem("Set weight...");
                MenuItem delete=new MenuItem("Delete");
                contextMenu.getItems().addAll(setWeight,new SeparatorMenuItem(),delete);
                contextMenu.show(view,event.getScreenX(),event.getScreenY());

                setWeight.setOnAction(event1 -> setWeight());
                delete.setOnAction(event1 ->delete());
            }
        });
    }

    public void setWeight(){
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Setting marking ");
        dialog.setContentText("enter your marking : ");
        //todo there is an exception should be captured , the case of the marking is not a number
        Optional<String> result=dialog.showAndWait();

        if(result.isPresent()) {
            int weight = Integer.valueOf(result.get());
            //AnchoreController.graph.deleteShape(place);
            arrow.setWeight(weight);
            arrow.relocate(Controller.AnchoreController.staticAnchorPane);
            // AnchoreController.graph.addPlace(place);
            //place.getView().setText(new Text(place.getName()));
            Controller.AnchoreController.staticAnchorPane.getChildren().clear();
            Controller.AnchoreController.graph.paint(Controller.AnchoreController.staticAnchorPane);

        }
    }

    public void delete(){
        System.out.println("deleting arc ...");
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("this item will be deleted !");
        alert.setContentText("Are you sure you want to delete this item ? ");
        Optional<ButtonType>result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            //the user chose o  k
           //
            Controller.AnchoreController.graph.deleteArrow(arrow);

            ArrayList<Arrow> deletedArrows=new ArrayList<>();
            Controller.AnchoreController.staticAnchorPane.getChildren().clear();
            Controller.AnchoreController.graph.paint(Controller.AnchoreController.staticAnchorPane);
        }

    }
}
