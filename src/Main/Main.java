package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.Event;

import model.*;

public class Main extends Application {
    public static AnchorPane Anchorpane;
    public static Graph graph;
   public static void AddActionEventToNodes(Shape s){
       EventHandler eventHandler = new EventHandler() {
           @Override
           public void handle(Event evt) {

           }
};

   }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Views/AppGUI.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene =new Scene(root);
        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {



       launch(args);
    }
}