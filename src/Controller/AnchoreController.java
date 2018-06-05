package Controller;
//package Controller;
import MCGGeneration.IntMarking;
import MCGGeneration.MCG;
import MCGGeneration.MCGGenerator;
import MCGGeneration.Matrix;
import Views.PlaceView;
import Views.Position;
import Views.TransitionView;
import WAConvertion.Converter;
import WAConvertion.WeightedAutomata;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class AnchoreController  {
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
        //   Place shape1=new Place(new Position(ev.getX(),ev.getY()),"p"+count);
        countTransition++;
        //        T1 =new Transition(new Position(ev.getX(),ev.getY()),"T"+count);
        drawingAreaAnchorPane.getChildren().clear();
        //todo enter the weight of the transition here
        Transition transition =new Transition(new Position(ev.getX(), ev.getY()), "T" + countTransition,"");
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
    public void convert(){
       PetriNet net=new PetriNet(graph,"default");
       System.out.println(net.toString());
        MCGGenerator generator=new MCGGenerator(net.getName());
        MCG mcg=generator.generateMCG(net);
        System.out.println(mcg.toString());
        Converter converter=new Converter(petriNet.getName());
        WeightedAutomata wa=converter.Convert(net,mcg.getUnboundedPlaces());
    }
    public void open(){

    }

    @FXML
    public void save(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.setSelectedExtensionFilter(extFilter);
        File selected=fileChooser.showSaveDialog(drawingAreaAnchorPane.getScene().getWindow());

        String pathname;
        File file;
        if(selected != null){
            pathname=selected.getAbsolutePath();
            System.out.println("saving file to "+pathname);
            file=new File(pathname+".txt");
            System.out.println(file.getAbsolutePath());
            System.out.print("saving Petri net ...");
            DataOutputStream dos=null;
            try{
                dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                dos.writeUTF("Pre");
                System.out.println("Pre");
                dos.writeUTF("\n");

                for (Transition t:graph.getTransitions()) {
                    dos.writeUTF(t.getName());
                    System.out.print(t.getName()+" ");
                }
                dos.writeUTF("\n");
                System.out.println();
                for(Place p:graph.getPlaces()){
                    dos.writeUTF(p.getName());
                    System.out.print(p.getName()+" ");
                    for (Transition t:graph.getTransitions()) {
                        int weight=0;
                        for (Arrow a:graph.getArrows()) {
                            if(a.getStartingShape().equals(p)&a.getEndingShape().equals(t)){
                                weight=a.getWeight();
                            }
                        }
                        dos.write(weight);
                        System.out.print(weight+" ");
                    }
                    dos.writeUTF("\n");
                    System.out.println();
                }
                dos.writeUTF("\n");
                dos.writeUTF("Post");
                System.out.println("post");
                dos.writeUTF("\n");
                for (Transition t:graph.getTransitions()) {
                    dos.writeUTF(t.getName());
                    System.out.print(t.getName()+" ");
                }
                dos.writeUTF("\n");
                System.out.println();
                for(Place p:graph.getPlaces()){
                    dos.writeUTF(p.getName());
                    System.out.print(p.getName()+" ");
                    for (Transition t:graph.getTransitions()) {
                        int weight=0;
                        for (Arrow a:graph.getArrows()) {
                            if(a.getStartingShape().equals(t)&a.getEndingShape().equals(p)){
                                weight=a.getWeight();
                            }
                        }
                        dos.write(weight);
                        System.out.print(weight+" ");
                    }
                    dos.writeUTF("\n");
                    System.out.println();
                }

                System.out.print("M0=[");
                for (Place p:graph.getPlaces()) {
                    dos.write(p.getMarking());
                    System.out.print(p.getMarking()+" ");
                }
                System.out.print("] \n");
                dos.flush();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            finally {
                try{
                    if(dos!=null)
                        dos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }


}
