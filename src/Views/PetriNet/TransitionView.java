package Views.PetriNet;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TransitionView extends javafx.scene.Group implements ShapeView{

    private Position position;
    Rectangle rectangle ;
    Text text;

    public TransitionView(Position position,String name,String event){
        super();
        this.position=position;
        rectangle=new Rectangle(position.getPositionX(),position.getPositionY(),35,20);
        rectangle.setStroke(Color.CHARTREUSE);
        rectangle.setFill(Color.WHITE);
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        rectangle.setStyle("-fx-stroke-line-cap: butt;-fx-stroke-dash-offset: 6;-fx-stroke-dash-array: 12 2 4 2; -fx-stroke: blue;");
//add CSS Class to transition
        rectangle.getStyleClass().add("transitioneview");
        text=new Text(position.getPositionX()+5,position.getPositionY()+35,name+":"+event);
        text.setTextAlignment(TextAlignment.CENTER);
        getChildren().addAll(rectangle,text);
    }

    public Position getPosition() {
        return position;
    }


    @Override
    public void drow(AnchorPane Anchorpane) {
        Anchorpane.getChildren().addAll(this);
    }
}
