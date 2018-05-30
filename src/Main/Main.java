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
//
//               String text = "";
//               String eventType = evt.getEventType().toString();
//               switch (eventType) {
//                   case "MOUSE_CLICKED":
//
//                       break;
//                   case "MOUSE_ENTERED":
//                       text = "Mouse entedered";
//                       break;
//                   case "MOUSE_EXITED":
//                       text = "Mouse exited";
//                       break;
//                   case "MOUSE_DRAGGED":
//                       text = "Mouse dragged";
//                       break;
//                   case "MOUSE_MOVED":
//                       text = "Mouse mouved";
//                       break;
//                   case "MOUSE_RELEASED":
//                       text = "Mouse releasead";
//                       break;
//                   case "MOUSE_PRESSED":
//                       text = "Mouse pressed";
//                       break;
//                   case "KEY_PRESSED":
//                       text = "Key pressed";
//                       break;
//                   // could have others events as well...
//                   default:
//                       text = "Evento desconhecido: "+eventType;
//                       break;
//               }
//               System.out.println(text);
//           }
//       };
//       // setting the handler to the events
//       lbl.setOnMouseClicked(eventHandler);
//       lbl.setOnMouseEntered(eventHandler);
//       lbl.setOnMouseExited(eventHandler);
//       lbl.setOnMouseDragged(eventHandler);
//       lbl.setOnMouseMoved(eventHandler);
//       lbl.setOnMousePressed(eventHandler);
//       lbl.setOnMouseReleased(eventHandler);
//       lbl.setOnKeyPressed(eventHandler);
//
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
   ///  Anchorpane= AnchoreController.getDrawingAreaAnchorPane();
        /*

//         */
//        Anchorpane=(AnchorPane) scene.lookup("#mainPanel");
//
//        //------------------------
//
//        //the cration of the object place
//        Place shape1=new Place(new Position(100,100),"P1");
//        Place shape2=new Place(new Position(200,200),"P2");
//
//        //the creation of the object transition
//        Transition t1=new Transition(new Position(300,300),"T1");
//
//        //the arrows or the lines that connects shapes
//        Arrow arrow= new Arrow(shape1,t1);
//        Arrow arrow2= new Arrow(t1,shape2);
//
//        graph=new Graph();
//        graph.addArrow(arrow);
//        graph.addArrow(arrow2);
//        graph.addPlace(shape1);
//        graph.addPlace(shape2);
//        graph.addTransition(t1);
//        graph.paint(Anchorpane);

        primaryStage.show();
    }
    public static void main(String[] args) {

        String a ="a";  String b ="b";
       if(a.equals("a")){
           System.out.println("if one");

           if(b.equals("a")) {
               System.out.println("if two");

           }
       }else {
         System.out.println("else");
       }

       launch(args);
    }
}