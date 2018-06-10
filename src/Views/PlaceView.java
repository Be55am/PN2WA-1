package Views;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.Serializable;

public class PlaceView extends Group implements ShapeView,Serializable{

    private Circle circle;
  // Rectangle circle;
    //private Label label;
    private Text text;
    private Text marking;
    private Position position;
    public AnchorPane anchorPane;

    public PlaceView(Position position,String name,int marking){
        this.position = position;
        this.marking=new Text(position.getPositionX()-4,position.getPositionY()+5,String.valueOf(marking));
         circle=new Circle(position.getPositionX(),position.getPositionY(),20);
         DropShadow ds1 = new DropShadow();
         ds1.setOffsetY(2.0);
         circle.setStyle("-fx-stroke-line-cap: butt;-fx-stroke-dash-offset: 6;-fx-stroke-dash-array: 12 2 4 2; -fx-stroke: green;");
         ds1.setColor(Color.WHEAT);
         circle.setEffect(ds1);
         circle.setStroke(Color.web("#005599"));
         circle.setStrokeWidth(1);
         circle.setFill(Color.WHITE);
       //  circle.setBlendMode(BlendMode.BLUE);
       //  circle.setOpacity(54);
       //  circle.setStroke(Color.BLACK);
         text=new Text(position.getPositionX()-15,position.getPositionY()+30,name);
        // circle.setFill(null);
        getChildren().addAll(circle,text,this.marking);
    }
    @Override
    public void drow(AnchorPane anchorpane) {
        anchorpane.getChildren().add(this);
        this.anchorPane=anchorpane;
    }

    public Circle getCircle() {
        return circle;
    }

//    public Label getLabel() {
//        return label;
//    }

    public Text getText() {
        return text;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setText(Text text) {
        this.text =text;
    }

 //   public void setLabel(Label label) {
 //       this.label = label;
  //  }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


}
