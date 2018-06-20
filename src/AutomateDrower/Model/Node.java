package AutomateDrower.Model;


import AutomateDrower.View.NodeView;

public class Node {

    private String marking ;
    private NodeView view;
    private double x,y;



    public Node(String marking,double x,double y){
        this.marking=marking;

        view=new NodeView(marking,x,y);
    }



    public String getMarking() {
        return marking;
    }

    public void setMarking(String marking) {
        this.marking = marking;
    }

    public NodeView getView() {
        return view;
    }

    public void setView(NodeView view) {
        this.view = view;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        view=new NodeView(this.marking,this.x,y);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        view=new NodeView(marking,x,y);
    }
}
