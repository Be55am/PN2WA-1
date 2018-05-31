package Controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import model.Arrow;
import model.Graph;
import model.Place;
import  Views.PlaceView;
import  Views.Position;

import java.util.Optional;


public class PlaceController {
    private Place place;
    private PlaceView view;
    private AnchorPane anchorPane;

    public PlaceController(Place place) {
        this.place = place;
        this.view = place.getView();
//      here you can adjust the place of the dragg with the scene
        view.setOnMouseDragged(event -> {
            view.relocate(event.getSceneX() - 200, event.getSceneY() - 80);

            Position p = place.getPosition();
            p.setPositionX(event.getSceneX() - 185);
            p.setPositionY(event.getSceneY() - 90);
            place.setPosition(p);
        });
        view.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.SECONDARY){
                //creating a ContextMenu
                ContextMenu contextMenu=new ContextMenu();
                MenuItem reName=new MenuItem("Rename...");
                MenuItem setMarking=new MenuItem("Set marking...");
                MenuItem delete=new MenuItem("Delete");
                contextMenu.getItems().addAll(reName,setMarking,new SeparatorMenuItem(),delete);
                contextMenu.show(view,event.getScreenX(),event.getScreenY());
                reName.setOnAction(event1 -> reName());
                delete.setOnAction(event1 -> delete());
            }



        });




//        view.setOnMousePressed(event -> {
//            Position p = place.getPosition();
//            p.setPositionX(event.getSceneX() - 20);
//            p.setPositionY(event.getSceneY() - 90);
//            place.setPosition(p);
//
//            view.relocate(p.getPositionX(), p.getPositionY());
//        });

//        view.setOnMousePressed(event -> {
////             if (){
////
////             }
//               PlaceView p = (PlaceView) event.getSource();
//
//  p.getChildren().clear();
             // Place placep =null;
//            for (Place place1: Graph.places) {
//
////              if (place1.getView().getPosition().getPositionX() == p.getPosition().getPositionX() &&
////                      place1.getView().getPosition().getPositionY() == p.getPosition().getPositionY()
////                      ){
////                  this.place= place1;
////              }
////            }
//
//            Arrow arrow2= new Arrow(this.place,this.place);
//            Graph graph=new Graph();
//
//            graph.addArrow(arrow2);
//            AnchoreController.staticAnchorPane.getChildren().clear();
//            graph.paint(AnchoreController.staticAnchorPane);
//
//            System.out.print(p.getPosition().getPositionX()+"-Y "+p.getPosition().getPositionY()+
//                    " -THis is place do you want"+p.getLabel()+p.getProperties().toString()+"------"+p.getId());
//        });
//    }
//
//    public enum ClickFS{
//        FIRST,SECONDE
   }

   //todo this doesn't work
    public void reName(){
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Rename");
        dialog.setHeaderText("Renaming : "+place.getName());
        dialog.setContentText("Enter new Name : ");
        Optional<String> result= dialog.showAndWait();
        result.ifPresent(name ->place.setName(name) );
        this.anchorPane=view.anchorPane;
        view.setText(place.getName());
        view.anchorPane.getChildren().remove(view);
        //this.view=new PlaceView(place.getPosition(),place.getName());
        anchorPane.getChildren().add(view);

    }
    public void delete(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("this item will be deleted !");
        alert.setContentText("Are you sure you want to delete this item ? ");
        Optional<ButtonType>result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            //the user chose o  k
            view.anchorPane.getChildren().remove(view);
            Graph.places.remove(place);

        }

    }

}
