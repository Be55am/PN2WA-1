package model.PetriNet;


import Controller.PetriNetController.ArrowController;
import Views.PetriNet.ArrowView;
import Views.PetriNet.Position;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;

public class Arrow implements Serializable{

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

        double startX=start.getPositionX();
        double startY=start.getPositionY();
        double endX=end.getPositionX();
        double endY=end.getPositionY();
        if(endingShape instanceof Place){
            if((startX-endX)<0) {
                endX = endX - 17;
                startX=startX+18;
            }
            else {
                endX=endX+15;
                startX-=13;
            }

            if((startY-endY)<0) {
                endY=endY-10;
                startY=startY+6;
            }
            else {
                endY=endY+10;
                startY=startY-5;
            }
            startX+=20;
            startY+=15;
            endX+=2;
            endY+=2;


        }else {
            if((startX-endX)<0) {
                endX = endX - 13;
                startX=startX+8;
            }
            else {
                endX=endX+15;
                startX-=18;
            }

            if((startY-endY)<0) {
                endY=endY-10;
                startY=startY+13;
            }
            else {
                endY=endY+10;
                startY=startY-15;
            }
            endX+=23;
            endY+=18;


        }



        this.arrowView=new ArrowView(startX,startY,endX,endY,weight);
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

        double startX=start.getPositionX();
        double startY=start.getPositionY();
        double endX=end.getPositionX();
        double endY=end.getPositionY();
        if(endingShape instanceof Place){
            if((startX-endX)<0) {
                endX = endX - 17;
                startX=startX+18;
            }
            else {
                endX=endX+15;
                startX-=13;
            }

            if((startY-endY)<0) {
                endY=endY-10;
                startY=startY+6;
            }
            else {
                endY=endY+10;
                startY=startY-5;
            }
            startX+=20;
            startY+=15;
            endX+=2;
            endY+=2;


        }else {
            if((startX-endX)<0) {
                endX = endX - 13;
                startX=startX+8;
            }
            else {
                endX=endX+15;
                startX-=18;
            }

            if((startY-endY)<0) {
                endY=endY-10;
                startY=startY+13;
            }
            else {
                endY=endY+10;
                startY=startY-15;
            }
            endX+=23;
            endY+=18;


        }


            arrowView=new ArrowView(startX,startY,endX,endY,weight);
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
