package Controller;

import javafx.scene.control.Alert;

public class DrowSomethingException extends Exception {
    public DrowSomethingException(){
        System.out.println("draw something !");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error ");
        alert.setHeaderText("there is no places in the net ");
        alert.setContentText("Add some Places in the drawing area ! ");

        alert.showAndWait();
    }
}
