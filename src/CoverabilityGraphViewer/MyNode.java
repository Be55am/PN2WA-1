package CoverabilityGraphViewer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MyNode extends VBox {

    private MyNode parent;
    private String mark;
    private ObservableList<MyNode> children;
    private Label markLabel, transitionLabel;
    private HBox childrenHolder;
    private String transitionName;
    private boolean used;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public MyNode(String mark) {
        this.mark = mark;
        children = FXCollections.observableArrayList();
        initPane();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        markLabel.setText(mark);
        this.mark = mark;
    }

    private void initPane() {
        setSpacing(20);
        setPadding(new Insets(10));
        setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        setAlignment(Pos.TOP_CENTER);
        markLabel = new Label(mark);
        getChildren().add(markLabel);
        markLabel.setStyle("-fx-border-color: orangered;"
                + "    -fx-font-size:13;"
                + "    -fx-padding: 5;"
                + "    -fx-border-width: 1 8 1 8;"
                + "    -fx-background-color: white;");
      //  setStyle( "    -fx-background-color: white;");
//       markLabel.getStyleClass().add("markLabel");
// set the children - generated marks

        initChildrenHolder();
    }

    private void initChildrenHolder() {
        // create the children Marks
        // create the cholder holdre
        childrenHolder = new HBox();
        childrenHolder.getChildren().addAll(children);
        childrenHolder.setSpacing(10);
        childrenHolder.setAlignment(Pos.TOP_CENTER);
        getChildren().add(childrenHolder);
    }

    ObservableList<Transition> transitionList = FXCollections.observableArrayList();

    public void addTransitions(ObservableList<Transition> transitions, ObservableList<MyNode> nodelist) {
        Transition t = null;
        transitions.filtered(tt -> tt.getParentMark().trim().equalsIgnoreCase(mark.trim())).forEach(tr -> {
            tr.setUsed(true);
            transitionList.add(tr);
        });
        findChildren(nodelist);
        transitionList.forEach(tr -> {
            tr.addNodeTransitions(transitions, nodelist);
        });
        childrenHolder.getChildren().addAll(transitionList);
//  
//        children.forEach(node -> {
//            if (!node.isUsed()) {
//                node.setUsed(true);
//                node.addTransitions(transitions, nodelist);
//            }
//        });
    }

    public void findChildren(ObservableList<MyNode> nodelist) {
        for (Transition transition : transitionList) {
            for (MyNode node : nodelist) {
                if (transition.getChildMark().trim().equalsIgnoreCase(node.getMark().trim())) {
                    children.add(node);
                    transition.setNode(node);
                    break;
                }
            }
        }

    }

    public void setTransitionName(String transitionName) {
        transitionLabel.setText(mark);
        this.transitionName = transitionName;
    }

    public void addNode(MyNode node) {
        childrenHolder.getChildren().add(node);
    }

    public AnchorPane drawLis(AnchorPane canvas) {
        Line line;
        Point2D StartLocation = markLabel.localToScene(0, 0);
        for (Transition transition : transitionList) {
            Point2D location = transition.localToScene(0, 0);
            line = new Line(StartLocation.getX() + (markLabel.getWidth() / 2),
                    StartLocation.getY() + (markLabel.getHeight()),
                    location.getX() + (transition.getWidth() / 2),
                    location.getY()
                    + transition.getNameLabel().localToParent(0, 0).getY()
            );
            line.setStrokeWidth(3);
            line.setStroke(Color.GREEN);

            canvas.getChildren().add(line);
        }
        return canvas;
    }

    public Label getMarkLabel() {
        return markLabel;
    }

}
