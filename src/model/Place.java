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
    private PlaceController controller;
    public Place(Position position,String name,int marking) {
        super(position,name,marking);
        view=new PlaceView(position,name,marking);
         controller=new PlaceController(this);
    }
    public PlaceView getView() {
        return view;
    }
    public void paint(AnchorPane Anchorpane){
        view.drow(Anchorpane);
    }

    public void refrech(){
        view=new PlaceView(this.getPosition(),this.getName(),this.getValue());
         controller=new PlaceController(this);
    }

    public void setName(String name){
        super.setName(name);
        view.setText(new Text(view.getPosition().getPositionX(),view.getPosition().getPositionY(),name));
    }



}
