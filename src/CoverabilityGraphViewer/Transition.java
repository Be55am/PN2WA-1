package CoverabilityGraphViewer;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Arrow;

public class Transition extends VBox {

    private String name, parentMark, childMark;
    private boolean used;
    private MyNode node;
    private Label nameLabel;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Transition(String name, String parentMark, String childMark) {
        this.name = name;
        this.parentMark = parentMark;
        this.childMark = childMark;
        initPane();
    }

    public void setNode(MyNode node) {
        this.node = node;
        if (!node.isUsed()) {
            getChildren().add(node);
        }
    }

    private void initPane() {
        setSpacing(20);
        setPadding(new Insets(10));
        setMaxSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        setAlignment(Pos.TOP_CENTER);
        nameLabel = new Label(name);
        nameLabel.setStyle("-fx-border-color: #00FF00;"
                + "    -fx-font-size:13;"
                + "    -fx-padding: 5;"
                + "    -fx-border-width: 2 2 8 2;"
                + "    -fx-border-radius: 5 5 0 0;"
                + "    -fx-background-color: white;");
        nameLabel.getStyleClass().add("transitionLabel");
        getChildren().add(nameLabel);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentMark() {
        return parentMark;
    }

    public void setParentMark(String parentMark) {
        this.parentMark = parentMark;
    }

    public String getChildMark() {
        return childMark;
    }

    public void setChildMark(String childMark) {
        this.childMark = childMark;
    }

    void addNodeTransitions(ObservableList<Transition> transitions, ObservableList<MyNode> nodelist) {
        if (node != null && !node.isUsed()) {
            node.setUsed(true);
            node.addTransitions(transitions, nodelist);
        }
    }

    public void addNode() {

    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public MyNode getNode() {
        return node;
    }

    AnchorPane drawLine(AnchorPane canvas) {
        if (node == null) {
            addNewNode();
        }

        Line line;
        ArrowHead row = null;
        Point2D StartLocation = nameLabel.localToScene(0, 0);
        Point2D endLocation = node.localToScene(0, 0);

        if (StartLocation.getY()
                < endLocation.getY()) {
            row = new ArrowHead(
                    StartLocation.getX() + (nameLabel.getWidth() / 2),
                    StartLocation.getY() + (nameLabel.getHeight()),
                    endLocation.getX() + (node.getWidth() / 2),
                    endLocation.getY()
                            + node.getMarkLabel().localToParent(0, 0).getY()
            );
            row.setFill(Color.GREEN);

            canvas.getChildren().add(row);

        } else {
            line = new Line(StartLocation.getX(),
                    StartLocation.getY() + (nameLabel.getHeight()),
                    StartLocation.getX() - 30,
                    StartLocation.getY() + (nameLabel.getHeight())
            );
            line.setStroke(Color.RED);
            line.setStrokeWidth(2);
            canvas.getChildren().add(line);
            line = new Line(
                    StartLocation.getX() - 30,
                    StartLocation.getY() + (nameLabel.getHeight()),
                    StartLocation.getX() - 30,
                    endLocation.getY()
                            + node.getMarkLabel().localToParent(0, 0).getY()
            );

            line.setStroke(Color.RED);

            line.setStrokeWidth(2);
            canvas.getChildren().add(line);
            line = new Line(
                    StartLocation.getX() - 30,
                    endLocation.getY()
                            + node.getMarkLabel().localToParent(0, 0).getY(),
                    endLocation.getX() + node.getMarkLabel().localToParent(0, 0).getX(),
                    endLocation.getY()
                            + node.getMarkLabel().localToParent(0, 0).getY()
            );
            row =new ArrowHead(StartLocation.getX() - 30,endLocation.getY()
                    + node.getMarkLabel().localToParent(0, 0).getY(),
                    endLocation.getX() + node.getMarkLabel().localToParent(0, 0).getX(),
                    endLocation.getY()
                            + node.getMarkLabel().localToParent(0, 0).getY());
//            line.setStroke(Color.RED);
//            line.setStrokeWidth(1);
            row.setFill(Color.RED);
            canvas.getChildren().add(row);
        }


        return canvas;
    }

    public void addNewNode() {
        node = new MyNode(childMark);
        node.setUsed(used);
        getChildren().add(node);
        node.setStyle("-fx-background-color : rgba(255,0,0,0.5)");
    }
}
