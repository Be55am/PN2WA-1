package Controller;

import javafx.scene.control.Alert;

public class UnboundedPlaceException extends Exception {
    public UnboundedPlaceException(){
        System.out.println("More than one unbounded place Exception !");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error ");
        alert.setHeaderText("Oops, there is more than one unbounded Place ! ");
        alert.setContentText("We support only one unbounded place ");

        alert.showAndWait();
    }
}
