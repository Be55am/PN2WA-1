package Controller;

import javafx.scene.control.Alert;

public class NotDeterministicException extends Exception {
    public NotDeterministicException(){
        System.out.println("Not deterministic Petri Net!");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error ");
        alert.setHeaderText("Oops, this is not a deterministic Petri net ! ");
        alert.setContentText("We support only deterministic nets :( ");

        alert.showAndWait();
    }
}
