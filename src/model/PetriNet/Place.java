package model.PetriNet;
        
import Controller.PetriNetController.PlaceController;
import Views.PetriNet.PlaceView;
import Views.PetriNet.Position;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Place extends Shape {
    
    private PlaceView view;
    private PlaceController controller;
    private int marking;

    public Place(Position position,String name,int marking) {
        super(position,name);
        this.marking=marking;
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
        view=new PlaceView(this.getPosition(),this.getName(),this.getMarking());
         controller=new PlaceController(this);
    }

    public void setName(String name){
        super.setName(name);
        view.setText(new Text(view.getPosition().getPositionX(),view.getPosition().getPositionY(),name));
    }

    public int getMarking() {
        return marking;
    }

    public void setMarking(int marking) {
        this.marking = marking;
    }
}
