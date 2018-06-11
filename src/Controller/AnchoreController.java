package Controller;
//package Controller;
import CoverabilityGraphViewer.AnimatedZoomOperator;
import CoverabilityGraphViewer.MyNode;
import CoverabilityGraphViewer.XMLReader;
import MCGGeneration.*;
import Views.PlaceView;
import Views.Position;
import Views.TransitionView;
import WAConvertion.Converter;
import WAConvertion.WeightedAutomata;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.*;
import model.Place;
import model.Transition;

import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class AnchoreController {
    // @FXML public Label source;
    @FXML private  AnchorPane drawingAreaAnchorPane;
    @FXML private ToggleGroup ChapeToggleGroup;
    @FXML public  RadioButton arrowRadio;
    @FXML private RadioButton transitionRadio;
    @FXML private RadioButton placeRadio;

    public static  AnchorPane staticAnchorPane;
    private Transition T1;
    public static boolean arrowButton;
    private int countTransition, countPlace , countArrow;
    public static Graph graph;
    private boolean opened=false;


    private static PetriNet petriNet;

    public static Shape startingShape=null;

    public void initialize(){
        transitionRadio.setUserData(TypeChape.TRANSITION);
        placeRadio.setUserData(TypeChape.PLACE);
        arrowRadio.setUserData(TypeChape.ARROW);
        arrowButton=arrowRadio.isSelected();
        countTransition=0;
        countPlace=0;
        countArrow= 0;
        graph=new Graph();
        //  graph.paint(drawingAreaAnchorPane);
        staticAnchorPane=drawingAreaAnchorPane;

        arrowRadio.setOnMouseClicked(event -> arrowButton=arrowRadio.isSelected());
        placeRadio.setOnMouseClicked(event -> arrowButton=arrowRadio.isSelected());
        transitionRadio.setOnMouseClicked(event -> arrowButton=arrowRadio.isSelected());
    }
    @FXML
    public void DrawChapes(MouseEvent ev) {
        if (placeRadio.isSelected()|| transitionRadio.isSelected()) {

            if (ChapeToggleGroup.getSelectedToggle().isSelected() &&
                    ChapeToggleGroup.getSelectedToggle().getUserData() == TypeChape.TRANSITION) {
             // Method Paint Place Into AnchorPane
                paintPlace(ev);

            } else if (ChapeToggleGroup.getSelectedToggle().getUserData() == TypeChape.PLACE) {
                    // Method Paint Transition
                paintTransition(ev);


            } else {
                AlertBox("Look, a Warning Dialog", "Please select the shape do you want to drow!" , "Warning");
            }

            ChapeToggleGroup.getSelectedToggle().setSelected(false);

        }else  {
            System.out.println("No Selected Radio Button ");
        }

    }


    public static void paintArrow(Shape shape) {
     System.out.println("painting arrow ...");
     if(startingShape==null){
         startingShape=shape;
     }else {
         if((startingShape instanceof Place&shape instanceof Place)||(startingShape instanceof Transition &shape instanceof Transition)) {
             //the first is a place

             // the second is place too
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setHeaderText("this is not how it works !");
             alert.setContentText("the arrow should be between different objects .");

             ButtonType retry = new ButtonType("Retry");
             ButtonType cancel = new ButtonType("Cancel");
             alert.getButtonTypes().setAll(retry, cancel);

             Optional<ButtonType> result = alert.showAndWait();
             if (result.get() == retry) {
                 // the user clicked retry
             } else {
                 // the user clicked cancel
                 startingShape = null;
             }
         }
         else{
             //the second is a transition
             System.out.println("the arrow added ");
             //todo insert weight here
             Arrow arrow=new Arrow(startingShape,shape,1);
             int res=graph.addArrow(arrow);
             if(res==1) {
                 graph.refrech();
                 startingShape = null;
                 arrowButton = false;
             }else{
                 Alert alert=new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Oops ...");
                 alert.setHeaderText("Arrow can't be added !");
                 alert.setContentText("this arrow already exists :/");
                 alert.showAndWait();
                 startingShape=null;
                 arrowButton=false;
             }
         }
     }



    }

    private void paintTransition(MouseEvent ev) {
        //   Place shape1=new Place(new Position(ev.getX(),ev.getY()),"p"+count);
        countPlace++;
        //        T1 =new Transition(new Position(ev.getX(),ev.getY()),"T"+count);
        drawingAreaAnchorPane.getChildren().clear();
        System.out.println("X Y -->"+ ev.getX()+"--"+ ev.getY());
        //todo read the weight of the place
        Place p =new  Place(new Position(ev.getX(), ev.getY()), "P" + countPlace,0);

        //   p.setO
        p.getView().setId("P" + countPlace);
        //add style to the place
        // p.getView().getStyleClass().add("placeview");

        graph.addPlace(p);

        graph.paint(drawingAreaAnchorPane);
    }

    private void paintPlace(MouseEvent ev) {

        //todo enter the weight of the transition here
        TextInputDialog dialog =new TextInputDialog();
        dialog.setTitle("Set Event");
        dialog.setHeaderText("Enter event for this transition");
        dialog.setContentText("Please enter event name :");

        Optional<String>result=dialog.showAndWait();
        if(result.isPresent()){
            //   Place shape1=new Place(new Position(ev.getX(),ev.getY()),"p"+count);
            countTransition++;
            //        T1 =new Transition(new Position(ev.getX(),ev.getY()),"T"+count);
            drawingAreaAnchorPane.getChildren().clear();
            Transition transition =new Transition(new Position(ev.getX(), ev.getY()), "T" + countTransition,result.get());
            transition.getTrasitionView().setId("T"+countTransition);
            //    transition.getTrasitionView().getStyleClass().add("arrowspace");
//
//                      "  border:solid 5px #000;" +
//                      "  border-color:#000 transparent transparent transparent;" +
//                      "  border-radius: 50%/100px 100px 0 0;" +
//                      "");
            graph.addTransition(transition);
            graph.paint(drawingAreaAnchorPane);
        }



    }

    private void AlertBox(String Header, String Content , String Type) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Type);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
    }

    @FXML
    void HandleClearArea(MouseEvent event) {
        //drawingAreaAnchorPane.getChildren().clear();
//        Graph.places.clear();
//        Graph.arrows.clear();
//        Graph.transitions.clear();
    graph.setArrows(new ArrayList<Arrow>());
    graph.setTransitions(new ArrayList<Transition>());
    graph.setPlaces(new ArrayList<Place>());
    drawingAreaAnchorPane.getChildren().clear();
    graph.paint(drawingAreaAnchorPane);
    }
    /**
     * Repaint
     */


    public enum TypeChape {
        PLACE , TRANSITION, ARROW
    }

    private Place getPlace(Position position){
        for (Place p : graph.places){
            if (    position.getPositionX() == p.getView().getPosition().getPositionX()
                    &&
                    position.getPositionY() == p.getView().getPosition().getPositionY()
                    ){
                return p;
            }
        }
        return  null;
    }
    public Transition getTransition(Position position){
        for (Transition t : graph.transitions){
            if (    position.getPositionX() == t.getTrasitionView().getPosition().getPositionX()
                    &&
                    position.getPositionY() == t.getTrasitionView().getPosition().getPositionY()
                    ){
                return t;
            }
        }
        return  null;
    }

    // todo make it happen
    @FXML
    public void convert() throws UnboundedPlaceException {

        //remove this to open files
        if(!opened)
        petriNet=new PetriNet(graph,"default");

        opened=false;
        graphGen generator=new graphGen(petriNet.getName());
        MCG mcg=generator.generate(petriNet);
        if(mcg==null){
            throw new UnboundedPlaceException();
        }else{


            BufferedWriter writer=null;
            try{
                File coverabilityFile=new File("coverabilityGraph.xml");
                writer=new BufferedWriter(new FileWriter(coverabilityFile));
                writer.write(mcg.toString());
                writer.flush();
                writer.close();

            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if (writer != null)
                        writer.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            drawCoverabilityGraph();
        }


        System.out.println(mcg.toString());
        Converter converter=new Converter(petriNet.getName());
        WeightedAutomata wa=converter.Convert(petriNet,petriNet.getInitialUnboundedMarking(mcg.getUnboundedPlaces()));
        System.out.println(wa.print());

    }
    @FXML
    public void open(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Petri net","*.spn"));
        File selectedFile=fileChooser.showOpenDialog(drawingAreaAnchorPane.getScene().getWindow());
        if(selectedFile!=null){
           ObjectInputStream ois;
           try {
               ois=new ObjectInputStream(new BufferedInputStream(new FileInputStream(selectedFile)));
              // graph=(Graph)ois.readObject();

               System.out.println("Opening the saved project ...");
               petriNet=(PetriNet)ois.readObject();
               opened=true;

               System.out.println(petriNet.toString());
              // graph=new Graph();
               staticAnchorPane.getChildren().clear();
               //graph.paint(staticAnchorPane);
           }catch(IOException | ClassNotFoundException e){
               e.printStackTrace();
           }
        }


    }

    @FXML
    public void save(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Petri net (*.spn)", "*.spn");
        fileChooser.setSelectedExtensionFilter(extFilter);
        File selected=fileChooser.showSaveDialog(drawingAreaAnchorPane.getScene().getWindow());

        String pathname;
        File file;
        if(selected != null){
            pathname=selected.getAbsolutePath();
            System.out.println("saving file to "+pathname);
            file=new File(pathname);
            System.out.println(file.getAbsolutePath());
            System.out.print("saving Petri net ...");
          ObjectOutputStream oos=null;
          petriNet=new PetriNet(graph,"default");
          try{
              oos=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
              oos.writeObject(petriNet);

              oos.close();

          } catch (IOException e){
              e.printStackTrace();
          }finally {
              try {
                  if (oos != null) {
                      oos.close();
                  }
              }catch (IOException e){
                  e.printStackTrace();
              }
          }

       }

    }
    @FXML
    public void generateCoverabilityGraph(){
        //remove this to open files
        if(!opened)
            petriNet=new PetriNet(graph,"default");

        opened=false;
        graphGen generator=new graphGen(petriNet.getName());
        MCG mcg=generator.generate(petriNet);
        BufferedWriter writer=null;
        try{
            File coverabilityFile=new File("coverabilityGraph.xml");
            writer=new BufferedWriter(new FileWriter(coverabilityFile));
            writer.write(mcg.toString());
            writer.flush();
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (writer != null)
                    writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        drawCoverabilityGraph();

    }

    public void drawCoverabilityGraph(){
        Stage stage=new Stage();
        XMLReader reader = new XMLReader();
        try {
            reader.PrintTreeMGC();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        MyNode node = reader.getGraoh();
//        String css = this.getClass().getResource("E://style.css").toExternalForm();
//        node.setstgetStylesheets().add("E://style.css");
        StackPane root = new StackPane();
        root.getChildren().add(node);
        ScrollPane sp = new ScrollPane(root);

        AnimatedZoomOperator zoomOperator = new AnimatedZoomOperator();
        AnimatedZoomOperator zoomOperator2 = new AnimatedZoomOperator();

        sp.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if(event.isControlDown()){
                    double zoomFactor = 1.5;
                    if (event.getDeltaY() <= 0) {
                        // zoom out
                        zoomFactor = 1 / zoomFactor;
                    }
                    for (Node n:root.getChildren()) {
                        zoomOperator.zoom(n, zoomFactor, event.getSceneX(), event.getSceneY());
                    }
                    zoomOperator2.zoom(node, zoomFactor, event.getSceneX(), event.getSceneY());

                }
            }
        });

        Scene scene = new Scene(sp);


        stage.setScene(scene);
        stage.show();
        AnchorPane  linesHolder = reader.getLinesHolder(node);
        root.getChildren().add(0, linesHolder);


// Listen to scroll events (similarly you could listen to a button click, slider, ...)
        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override

            public void handle(ScrollEvent event) {
                if(event.isControlDown()){
                    double zoomFactor = 1.5;
                    if (event.getDeltaY() <= 0) {
                        // zoom out
                        zoomFactor = 1 / zoomFactor;
                    }


                    for (Node n:root.getChildren()) {
                        zoomOperator.zoom(n, zoomFactor, event.getSceneX(), event.getSceneY());
                    }
                    zoomOperator2.zoom(node, zoomFactor, event.getSceneX(), event.getSceneY());

                }
            }
        });

    }


}
