package Controller.PetriNetController;


import Views.PetriNet.Position;
import Views.PetriNet.TransitionView;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import model.PetriNet.Arrow;
import model.PetriNet.Graph;
import model.PetriNet.Transition;


import java.util.ArrayList;
import java.util.Optional;

public class TransitionController {
    Transition transition;
    TransitionView view;

    public TransitionController(Transition transition){
        this.transition=transition;
        this.view=transition.getTrasitionView();

        view.setOnMouseDragged(event ->{
            double x = event.getSceneX()-190;
            double y = event.getSceneY()-60;
            if (x < 0){
                x= Controller.AnchoreController.staticAnchorPane.getLayoutX()-175;
            }
            if (y < 0) {
                y = Controller.AnchoreController.staticAnchorPane.getLayoutY() - 50;
            }

            view.relocate(x-3,y-3);

            Position p=transition.getPosition();
            p.setPositionX(x);
            p.setPositionY(y);
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
            if(Controller.AnchoreController.arrowButton) {
                Controller.AnchoreController.paintArrow(this.transition);
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


        Controller.AnchoreController.staticAnchorPane.getChildren().clear();
        Controller.AnchoreController.graph.paint(Controller.AnchoreController.staticAnchorPane);


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

    private void setEvent(){
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle("Setting event ");
        dialog.setContentText("enter your Event : ");
        //todo there is an exception should be captured , the case of the marking is not a String
        Optional<String> result=dialog.showAndWait();
        if(result.isPresent()){
            String text=result.get();
            if (!text.matches("[0-9]+") && !result.get().contains("[0-9]+")    && text.length() > 0) {
                transition.setEvent(result.get());
                transition.refrech();
               // System.out.println("M"+ text.matches("[0-9]+"));
                Controller.AnchoreController.staticAnchorPane.getChildren().clear();
                Controller.AnchoreController.graph.paint(Controller.AnchoreController.staticAnchorPane);
            }else {
                AlertBox("Error parsing...","Enter String not Contain a Number",
                        "Text Format Error");
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

}
