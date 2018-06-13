package Controller;

import javafx.scene.control.Alert;

public class NoUnboundedPlaceException extends Exception{
    public NoUnboundedPlaceException(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error ");
        alert.setHeaderText("Oops, there is no unbounded place in the net! ");
        alert.setContentText("the conversion works only with one place unbounded nets :( ");

        alert.showAndWait();
    }
}
