package AutomateDrower.Model;


import AutomateDrower.View.LinkView;
import javafx.scene.control.Label;

public class Link {

    private String name;
    private Node startNode, endNode;
    private LinkView view;


    private Label nameLabel;

    public Link(String name, Node startNode,Node endNode){
        this.name=name;
        this.startNode=startNode;
        this.endNode=endNode;
        view=new LinkView(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public LinkView getView() {
        view=new LinkView(this);
        return view;
    }
}
