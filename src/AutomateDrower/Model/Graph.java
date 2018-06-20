package AutomateDrower.Model;


import AutomateDrower.View.GraphView;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;


public class Graph {


    private ObservableList<Node> nodesList;
    private ObservableList<Link> linksList ;
    private GraphView view;


    public Graph(ObservableList<Node> nodes,ObservableList<Link> links){
        nodesList=nodes;
        linksList=links;
        view=new GraphView(this);

    }

    public void paint(AnchorPane pane){
        this.view.paint(pane);
    }

    public void addNode(Node n){
        if(!nodesList.contains(n))
            nodesList.add(n);
        else
            System.out.println("node cant be added : already exists !");
    }

    public void addLink(Link l){
        if(!linksList.contains(l)){
            linksList.add(l);
        }else
            System.out.println("link cant be added : already exists !");
    }

    public ObservableList<Node> getNodesList() {
        return nodesList;
    }

    public ObservableList<Link> getLinksList() {
        return linksList;
    }

    public GraphView getView() {
        return view;
    }

    public void setView(GraphView view) {
        this.view = view;
    }
}
