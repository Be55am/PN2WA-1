package model;


import Controller.ArrowController;
import Views.ArrowView;
import Views.Position;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Arrow {

   private Shape startingShape,endingShape;
   private ArrowView arrowView;
   Position start,end;
   private int weight;
   private ArrowController controller;

    public Arrow(Shape startingShape,Shape endingShape,int weight){
        setStartingShape(startingShape);
        setEndingShape(endingShape);
        setWeight(weight);
        start=startingShape.getPosition();
        end=endingShape.getPosition();
        this.arrowView=new ArrowView(start.getPositionX()-0,start.getPositionY()+0,end.getPositionX()+0,end.getPositionY(),weight);
        controller=new ArrowController(this);
    }

    public Shape getStartingShape() {
        return startingShape;
    }

    public void setStartingShape(Shape startingShape) {
        this.startingShape = startingShape;
    }

    public Shape getEndingShape() {
        return endingShape;
    }

    public void setEndingShape(Shape endingShape) {
        this.endingShape = endingShape;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void paint(AnchorPane Anchorpane){

        Anchorpane.getChildren().add(arrowView);

    }
    public void relocate(AnchorPane Anchorpane){
        Anchorpane.getChildren().remove(arrowView);
//        arrowView.setStartX(start.getPositionX());
//        arrowView.setStartY(start.getPositionY());
//        arrowView.setEndX(end.getPositionX());
//        arrowView.setEndY(end.getPositionY());
//        arrowView.setWeight(weight);
            arrowView=new ArrowView(start.getPositionX()-0,start.getPositionY()+0,end.getPositionX()+0,end.getPositionY()+0,weight);
            controller=new ArrowController(this);
        Anchorpane.getChildren().add(arrowView);
    }

    public ArrowView getArrowView() {
        return arrowView;
    }

    public void setArrowView(ArrowView arrowView) {
        this.arrowView = arrowView;
    }
}
