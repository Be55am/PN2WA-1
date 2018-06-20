package AutomateDrower.View;

import AutomateDrower.Controller.NodeController;
import AutomateDrower.Controller.NodeController;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class NodeView extends Group {
    private Text text;
    public double x,y;
    private static final int SIZE=30;




    public NodeView(String text,double x,double y){

    this.x=x;
    this.y=y;

    this.text=new Text(this.x,this.y,text);
     this.text.setFont(Font.font("Verdana", FontWeight.BOLD, SIZE));
        // this.text.setFill(Color.WHITE);
        // this.text.setStroke(Color.web("#7080A0"));
       getChildren().addAll(this.text);
        new NodeController(this);
    }
}
