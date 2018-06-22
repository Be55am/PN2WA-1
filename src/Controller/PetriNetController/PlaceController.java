package Controller.PetriNetController;


import Views.PetriNet.PlaceView;
import Views.PetriNet.Position;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import model.PetriNet.Arrow;
import model.PetriNet.Graph;
import model.PetriNet.Place;


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
            double x = event.getSceneX()-200 ;
            double y = event.getSceneY()-75;
           // System.out.println("X"+ x +"\n Y"+y);
            if (x < 0){
                x= Controller.AnchoreController.staticAnchorPane.getLayoutX();
            }
            if (y < 0) {
                y = Controller.AnchoreController.staticAnchorPane.getLayoutY();
            }
            view.relocate(x, y);
            Position p = place.getPosition();
            p.setPositionX(x+32);
            p.setPositionY(y+34);
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
            }else if(Controller.AnchoreController.arrowButton){
                Controller.AnchoreController.paintArrow(this.place);
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

        Controller.AnchoreController.staticAnchorPane.getChildren().clear();
        Controller.AnchoreController.graph.paint(Controller.AnchoreController.staticAnchorPane);

    }
    public void setMarking(){
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Setting marking ");
        dialog.setContentText("enter your marking : ");
        //todo there is an exception should be captured , the case of the marking is not a number
        Optional<String> result=dialog.showAndWait();

        if(result.isPresent()){
            int marking=0;
            boolean parse=false;
           if ( !result.get().contains("[a-zA-Z]+") && result.get().length() >0  ){
               try {
                   marking=Integer.valueOf(result.get());
                   parse=true;
               }catch (NumberFormatException e){
                   parse = false;
               }
           }
            if (parse){
             place.setMarking(marking);
             place.refrech();

            Controller.AnchoreController.staticAnchorPane.getChildren().clear();
            Controller.AnchoreController.graph.paint(Controller.AnchoreController.staticAnchorPane);}
            else {

               AlertBox("Error Parsing","Please enter Integer Number!.","Warning");
            }
        }
    }

    private void AlertBox(String Header, String Content , String Type) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Type);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
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
                    Controller.AnchoreController.staticAnchorPane.getChildren().remove(a.getArrowView());
                   deletedArrows.add(a);

                }
            }
            for (Arrow a:deletedArrows) {
                Controller.AnchoreController.graph.deleteArrow(a);
            }
            Controller.AnchoreController.staticAnchorPane.getChildren().clear();
            Controller.AnchoreController.graph.paint(Controller.AnchoreController.staticAnchorPane);

        }

    }

}
