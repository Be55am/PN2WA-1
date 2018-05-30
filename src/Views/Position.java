package Views;

import model.Graph;

public class Position {

    private double positionX;
    private double positionY;
    private Graph graph;

    public Position(double x,double y){
        this.positionX=x;
        this.positionY=y;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        if(positionX>=0)
        this.positionX = positionX;
        else
            this.positionX=0;
        graph.refrech();
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        if(positionY>=0)
        this.positionY = positionY;
        else
            this.positionY=0;
        graph.refrech();
    }
    public void connect(Graph graph){
        this.graph=graph;
    }

    public void moveTo(double x, double y){
        this.setPositionX(x);
        this.setPositionY(y);

    }
}
