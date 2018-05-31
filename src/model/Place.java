package model;
        
import Controller.PlaceController;
import Main.Main;
import Views.PlaceView;
import Views.Position;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Place extends Shape{
    
    private PlaceView view;
    public Place(Position position,String name,int marking) {
        super(position,name,marking);
        view=new PlaceView(position,name);
        PlaceController controller=new PlaceController(this);
    }
    public PlaceView getView() {
        return view;
    }
    public void paint(AnchorPane Anchorpane){
        view.drow(Anchorpane);
    }



}
