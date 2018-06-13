package Controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Arrow;
import model.Graph;
import model.Place;
import  Views.PlaceView;
import  Views.Position;

import java.util.ArrayList;
import java.util.Optional;


public class PlaceController {
    private Place place;
    private PlaceView view;
   // private AnchorPane anchorPane;

    public PlaceController(Place place) {
        this.place = place;
        this.view = place.getView();
//      here you can adjust the place of the dragg with the scene
        view.setOnMouseDragged(event -> {
            double x = event.getSceneX()-180 ;
            double y = event.getSceneY()-60;
            if (x < 0){
                x= AnchoreController.staticAnchorPane.getLayoutX()-180;
            }

            if (y < 0) {
                y = AnchoreController.staticAnchorPane.getLayoutY() - 60;
            }
            view.relocate(x, y);
            Position p = place.getPosition();
            p.setPositionX(x);
            p.setPositionY(y);
            place.setPosition(p);
        });
        // for the right click menu
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
                setMarking.setOnAction(event1 -> setMarking());
            }else if(AnchoreController.arrowButton){
                AnchoreController.paintArrow(this.place);
            }
        });

   }


    public void reName(){
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Rename");
        dialog.setHeaderText("Renaming : "+place.getName());
        dialog.setContentText("Enter new Name : ");
        Optional<String> result= dialog.showAndWait();

        //AnchoreController.graph.getPlaces().remove(place);
        result.ifPresent(name -> place.setName(name));
        place.refrech();
        //AnchoreController.graph.addPlace(place);
        //place.getView().setText(new Text(place.getName()));

        AnchoreController.staticAnchorPane.getChildren().clear();
        AnchoreController.graph.paint(AnchoreController.staticAnchorPane);







    }
    public void setMarking(){
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Setting marking ");
        dialog.setContentText("enter your marking : ");
        //todo there is an exception should be captured , the case of the marking is not a number
        Optional<String> result=dialog.showAndWait();

        if(result.isPresent()){
           int marking=Integer.valueOf(result.get());
           //AnchoreController.graph.deleteShape(place);
             place.setMarking(marking);
             place.refrech();
           // AnchoreController.graph.addPlace(place);
            //place.getView().setText(new Text(place.getName()));

            AnchoreController.staticAnchorPane.getChildren().clear();
            AnchoreController.graph.paint(AnchoreController.staticAnchorPane);
        }
    }
    public void delete(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("this item will be deleted !");
        alert.setContentText("Are you sure you want to delete this item ? ");
        Optional<ButtonType>result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            //the user chose o  k

            Graph.places.remove(place);

            ArrayList<Arrow> deletedArrows=new ArrayList<>();


            for (Arrow a:Graph.arrows) {
                if(a.getStartingShape()==place||a.getEndingShape()==place){
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

}
