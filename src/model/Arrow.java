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
        if(startingShape instanceof Place)
            arrowView=new ArrowView(start.getPositionX()+30,start.getPositionY()+30,end.getPositionX()+20,end.getPositionY()+0);
        else
            arrowView=new ArrowView(start.getPositionX()+20,start.getPositionY()+10,end.getPositionX()+30,end.getPositionY()+30);

        Anchorpane.getChildren().add(arrowView);
    }

    public ArrowView getArrowView() {
        return arrowView;
    }

    public void setArrowView(ArrowView arrowView) {
        this.arrowView = arrowView;
    }
}
