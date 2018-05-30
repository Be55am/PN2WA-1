package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML private ToggleButton placeButton;
    @FXML private ToggleButton transitionButton;
    @FXML private ToggleButton arrowButton;
    @FXML private ToggleGroup ToggleButtons;

    private enum  ToggleB {
        place,transition,arrow
    }
public void initialize(){
    placeButton.setUserData(ToggleB.place);
    transitionButton.setUserData(ToggleB.transition);
    arrowButton.setUserData(ToggleB.arrow);
}

    @FXML
    public void drawingChapesMouseDragged(MouseEvent e)
    {
        System.out.println("drawingChapesMouseDragged");

      /*  Circle newCircle = new Circle(e.getX(), e.getY(),

                radius.getRadius(), brushColor);
        drawingAreaAnchorPane.getChildren().add(newCircle);*/
    }
    @FXML
    public void ChapeisSelected(MouseEvent e){
      if(ToggleButtons.getSelectedToggle().getUserData()== ToggleB.place){
          System.out.println("selected placeButton");
          placeButton.setSelected(false);
          System.out.println("released");
      } else if (transitionButton.isSelected()) {
          System.out.println("slected trannsition");
          placeButton.setSelected(false);
          System.out.println("released");
      }else if (arrowButton.isSelected()){
          System.out.println("selected  arrwo");
          placeButton.setSelected(false);
          System.out.println("released");
      }
      System.out.println("selected ......");
    }

}