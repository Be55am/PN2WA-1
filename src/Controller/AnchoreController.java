package Controller;
//package Controller;
import Views.PlaceView;
import Views.Position;
import Views.TransitionView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Arrow;
import model.Graph;
import model.Place;
import model.Transition;

import java.util.ArrayList;
import java.util.Optional;

public class AnchoreController  {
    // @FXML public Label source;
    @FXML private  AnchorPane drawingAreaAnchorPane;
    @FXML private ToggleGroup ChapeToggleGroup;
    @FXML private RadioButton arrowRadio;
    @FXML private RadioButton transitionRadio;
    @FXML private RadioButton placeRadio;
    public static  AnchorPane staticAnchorPane;
    private Transition T1;
    private int countTransition, countPlace , countArrow;
    private Graph graph;

    public void initialize(){
        transitionRadio.setUserData(TypeChape.TRANSITION);
        placeRadio.setUserData(TypeChape.PLACE);
        arrowRadio.setUserData(TypeChape.ARROW);
        countTransition=0;
        countPlace=0;
        countArrow= 0;
        graph=new Graph();
        //  graph.paint(drawingAreaAnchorPane);
        staticAnchorPane=drawingAreaAnchorPane;
    }
    @FXML
    public void DrawChapes(MouseEvent ev) {
        if (placeRadio.isSelected() || arrowRadio.isSelected() || transitionRadio.isSelected()) {

            if (ChapeToggleGroup.getSelectedToggle().isSelected() &&
                    ChapeToggleGroup.getSelectedToggle().getUserData() == TypeChape.TRANSITION) {
             // Method Paint Place Into AnchorPane
                paintPlace(ev);

            } else if (ChapeToggleGroup.getSelectedToggle().getUserData() == TypeChape.PLACE) {
                    // Method Paint Transition
                paintTransition(ev);


            } else if (ChapeToggleGroup.getSelectedToggle().getUserData() == TypeChape.ARROW) {
                //  Method paint Arrow
                paintArrow();

            } else {
                AlertBox("Look, a Warning Dialog", "Please select the shape do you want to drow!" , "Warning");
            }

            ChapeToggleGroup.getSelectedToggle().setSelected(false);

        }else  {
            System.out.println("No Selected Radio Button ");
        }

    }

    private void paintArrow() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Draw Arrowo");
        dialog.setHeaderText("Link The First Node to the Seconde");


// Set the icon (must be included in the project).
       dialog.setGraphic(new ImageView(this.getClass().getResource("/images/arrow.png").toString()));
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

// Add a custom icon.
        stage.getIcons().add(new Image(this.getClass().getResource("/images/arrow-m.png").toString()));


        ButtonType linkButtonType = new ButtonType("Link", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(linkButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(10, 80, 5, 5));

        TextField FirstNodes = new TextField();
        FirstNodes.setPromptText("Name of 1th Place / Transition)");
        TextField password = new TextField();
        password.setPromptText("(Name of 2nd Place / Transition)");

        grid.add(new Label("1th Name of Place / Transition:"), 0, 0);
        grid.add(FirstNodes, 1, 0);
        grid.add(new Label("2nd Name of Place / Transition:"), 0, 1);
        grid.add(password, 1, 1);

//// Enable/Disable login button depending on whether a username was entered.
//
        Node linkButton = dialog.getDialogPane().lookupButton(linkButtonType);
        linkButton.setDisable(true);
//// Do some validation (using the Java 8 lambda syntax).
        FirstNodes.textProperty().addListener((observable, oldValue, newValue) -> {
            linkButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
//
//// Request focus on the username field by default.
        Platform.runLater(() -> FirstNodes.requestFocus());

//// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == linkButtonType) {
                return new Pair<>(FirstNodes.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(LinkNodes -> {
            //   System.out.println("Username=" + LinkNodes.getKey() + ", Password=" + LinkNodes.getValue());
            if(LinkNodes.getKey().length() != 0 && LinkNodes.getValue().length() != 0) {
            char c1 = Character.toUpperCase((LinkNodes.getKey().charAt(0)));
            char c2 = Character.toUpperCase(LinkNodes.getValue().charAt(0));
            PlaceView pv1;
            PlaceView pv2;
            TransitionView tv1;
            if (c1 == 'T' && c2 == 'P') {
                tv1 = (TransitionView) drawingAreaAnchorPane.lookup(
                        "#" + LinkNodes.getKey().toUpperCase());

                pv1 = (PlaceView) drawingAreaAnchorPane.lookup(
                        "#" + LinkNodes.getValue().toUpperCase());
                Arrow arrow = new Arrow(getTransition(tv1.getPosition()), getPlace(pv1.getPosition()));
                graph.addArrow(arrow);
                drawingAreaAnchorPane.getChildren().clear();
                graph.paint(drawingAreaAnchorPane);

            } else if (c1 == 'P' && c2 == 'T') {
                pv1 = (PlaceView) drawingAreaAnchorPane.lookup(
                        "#" + LinkNodes.getKey().toUpperCase());

                tv1 = (TransitionView) drawingAreaAnchorPane.lookup(
                        "#" + LinkNodes.getValue().toUpperCase());
                Arrow arrow =
                        new Arrow(getPlace(pv1.getPosition()), getTransition(tv1.getPosition()));
                graph.addArrow(arrow);
                drawingAreaAnchorPane.getChildren().clear();
                graph.paint(drawingAreaAnchorPane);
//                        }else
//                            if (c2 == 'P') {
//                            pv2 = (PlaceView) drawingAreaAnchorPane.lookup(
//                                    "#" + LinkNodes.getValue().toUpperCase());
//                            Arrow arrow = new Arrow(getPlace(pv1.getPosition()) , getPlace(pv2.getPosition()));
//                             //  arrow.getArrowView().getStyleClass().add("arrowspace");
//
//                            graph.addArrow(arrow);
//                            drawingAreaAnchorPane.getChildren().clear();
//                            graph.paint(drawingAreaAnchorPane);
//                        }
            } else {
                AlertBox("Warning ", "Please Enter Correct place or transition ", "Warning");
            }
        } else {
                AlertBox("Warning ", "Please Enter Correct place or transition ", "Warning");

            }
        });
    }

    private void paintTransition(MouseEvent ev) {
        //   Place shape1=new Place(new Position(ev.getX(),ev.getY()),"p"+count);
        countPlace++;
        //        T1 =new Transition(new Position(ev.getX(),ev.getY()),"T"+count);
        drawingAreaAnchorPane.getChildren().clear();
        System.out.println("X Y -->"+ ev.getX()+"--"+ ev.getY());
        Place p =new  Place(new Position(ev.getX(), ev.getY()), "P" + countPlace);

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
        Transition transition =new Transition(new Position(ev.getX(), ev.getY()), "T" + countTransition);
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
        for (Place p : Graph.places){
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
        for (Transition t : Graph.transitions){
            if (    position.getPositionX() == t.getTrasitionView().getPosition().getPositionX()
                    &&
                    position.getPositionY() == t.getTrasitionView().getPosition().getPositionY()
                    ){
                return t;
            }
        }
        return  null;
    }


}
