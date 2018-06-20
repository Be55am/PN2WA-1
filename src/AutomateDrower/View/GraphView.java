package AutomateDrower.View;

import AutomateDrower.Model.Graph;
import AutomateDrower.Model.Link;
import AutomateDrower.Model.Node;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class GraphView extends Group {
    private static  int ELEMENTSINROW=2;
    private static final int HORIZONTALSPACING=400;
    private static final int VERTICALSPACING=300;
    private static final int DECALLAGEX=30;
    private static final int DECALLAGEY=100;

    public static Graph graph;

    public GraphView(Graph graph){
        this.graph=graph;
        //adding the nodes to the graph


    }

    public void paint(AnchorPane pane){
        ELEMENTSINROW=(int)Math.sqrt(graph.getNodesList().size());
       for(int i=0;i<graph.getNodesList().size();i++){
           graph.getNodesList().get(i).setX((i%ELEMENTSINROW)*HORIZONTALSPACING+DECALLAGEX);
           graph.getNodesList().get(i).setY((i/ELEMENTSINROW)*VERTICALSPACING+DECALLAGEY);
       }
       int[] co=new int[graph.getNodesList().size()];
        for (Link l:graph.getLinksList()) {
           if(!l.getStartNode().equals(l.getEndNode())){
               pane.getChildren().add(l.getView());
           }else{
               /////////
               for (int i = 0; i <graph.getNodesList().size() ; i++) {
                   if(l.getStartNode().equals(graph.getNodesList().get(i))){
                       co[i]++;
                     Text  text=new Text(l.getStartNode().getX()-70+(co[i]*55),l.getStartNode().getY()+32,l.getName());
                       pane.getChildren().addAll(l.getView().createSelfPath(co[i]),text);
                   }
               }

           }

        }

        for (Node n:graph.getNodesList()) {
            pane.getChildren().add(n.getView());
        }
    }


}
