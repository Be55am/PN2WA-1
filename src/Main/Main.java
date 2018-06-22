package Main;

import Controller.AnchoreController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.Event;

import model.PetriNet.Graph;
import model.PetriNet.Shape;

public class Main extends Application {
    public static AnchorPane Anchorpane;
    public static Graph graph;
    public Stage primaryStage;
   public static void AddActionEventToNodes(Shape s){
       EventHandler eventHandler = new EventHandler() {
           @Override
           public void handle(Event evt) {

           }
};

   }
    @Override
    public void start(Stage p) throws Exception{
       this.primaryStage=p;
       //Parent root = FXMLLoader.load(getClass().getResource("../Views/AppGUI.fxml"));
//        primaryStage.setTitle("SynPNtoWA");
//        Scene scene =new Scene(root);
//        scene.getStylesheets().add("style.css");
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Views/AppGUI.fxml"));
        AnchoreController main = new AnchoreController();
        main.setMainApp(this);
        loader.setController(main);
        Parent root = loader.load();
        primaryStage.setTitle("SynPNtoWA");
        Scene scene =new Scene(root);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
       launch(args);
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}