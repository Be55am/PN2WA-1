package Controller;

import model.Arrow;
import model.Graph;
import model.Place;
import  Views.PlaceView;
import  Views.Position;


public class PlaceController {
    private Place place;
    private PlaceView view;

    public PlaceController(Place place) {
        this.place = place;
        this.view = place.getView();
//
        view.setOnMouseDragged(event -> {
            view.relocate(event.getSceneX() - 20, event.getSceneY() - 90);

            Position p = place.getPosition();
            p.setPositionX(event.getSceneX() - 20);
            p.setPositionY(event.getSceneY() - 90);
            place.setPosition(p);
        });

//        view.setOnMousePressed(event -> {
//            Position p = place.getPosition();
//            p.setPositionX(event.getSceneX() - 20);
//            p.setPositionY(event.getSceneY() - 90);
//            place.setPosition(p);
//
//            view.relocate(p.getPositionX(), p.getPositionY());
//        });

//        view.setOnMousePressed(event -> {
////             if (){
////
////             }
//               PlaceView p = (PlaceView) event.getSource();
//
//  p.getChildren().clear();
             // Place placep =null;
//            for (Place place1: Graph.places) {
//
////              if (place1.getView().getPosition().getPositionX() == p.getPosition().getPositionX() &&
////                      place1.getView().getPosition().getPositionY() == p.getPosition().getPositionY()
////                      ){
////                  this.place= place1;
////              }
////            }
//
//            Arrow arrow2= new Arrow(this.place,this.place);
//            Graph graph=new Graph();
//
//            graph.addArrow(arrow2);
//            AnchoreController.staticAnchorPane.getChildren().clear();
//            graph.paint(AnchoreController.staticAnchorPane);
//
//            System.out.print(p.getPosition().getPositionX()+"-Y "+p.getPosition().getPositionY()+
//                    " -THis is place do you want"+p.getLabel()+p.getProperties().toString()+"------"+p.getId());
//        });
//    }
//
//    public enum ClickFS{
//        FIRST,SECONDE
   }

}
