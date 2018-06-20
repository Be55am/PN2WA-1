package Controller;

import javafx.scene.control.Alert;

public class OverLoadException extends Exception{

    public OverLoadException(){
        System.out.println("OverLoad !");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error ");
        alert.setHeaderText("Oops, its not possible to detect the unbounded place ! ");
        alert.setContentText("the marking should be M>M'>M'' to create a w place ");

        alert.showAndWait();
    }
}
