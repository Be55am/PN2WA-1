package model;


import Views.ArrowView;
import Views.Position;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Arrow {

   private Shape startingShape,endingShape;
   private ArrowView arrowView;
   Position start,end;

    public Arrow(Shape startingShape,Shape endingShape){
        setStartingShape(startingShape);
        setEndingShape(endingShape);
        start=startingShape.getPosition();
        end=endingShape.getPosition();
        this.arrowView=new
        ArrowView(start.getPositionX(),start.getPositionY(),end.getPositionX(),end.getPositionY());
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

    public void paint(AnchorPane Anchorpane){

        Anchorpane.getChildren().add(arrowView);

    }
    public void relocate(AnchorPane Anchorpane){
        Anchorpane.getChildren().remove(arrowView);
        arrowView=new ArrowView(start.getPositionX()+20,start.getPositionY()+20,end.getPositionX()+20,end.getPositionY()+20);
        Anchorpane.getChildren().add(arrowView);
    }

    public ArrowView getArrowView() {
        return arrowView;
    }

    public void setArrowView(ArrowView arrowView) {
        this.arrowView = arrowView;
    }
}
