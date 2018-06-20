package MCGGeneration;

import java.util.ArrayList;

public class MCG {

    private String name;
    private ArrayList <Node> nodes;
    private ArrayList <Connection> connections;


    public MCG(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
        this.connections = new ArrayList<>();
    }
    public void addNode(Node node){
        nodes.add(node);
    }
    public void addConnection(Connection c){
        connections.add(c);
    }
    public  String toString(){
        String result="<?xml version=\"1.0\"?>";
        result +="\n<Tree>\n";
        result+="   <Nodes>\n";
        for (Node node:nodes) {

            result+="   "+node.toString()+"\n";

        }
        result+="   </Nodes>\n";
        result+="   <Links>\n";
        for (Connection connection:connections) {
            result+="       <link>\n";
            result+="           <firstNode>"+connection.getStart().print()+"</firstNode>\n";
            result+="           <secondNode>"+connection.getEnd().print()+"</secondNode>\n";
            result+="           <transitions>{"+connection.printTransitions()+"}</transitions>\n";
            result+="       </link>\n";
        }
        result+="   </Links>";


        result+="\n</Tree>\n";
        return result;
    }

    public boolean duplicated(Node n) {
        for (Node node:nodes) {
            if (node.isEqual(n)) {
                return true;
            }
        }
        return false;
    }

    public Node getPrecedentQ(Node qPrim, ArrayList<Transition>t){
        for (Connection c:connections) {
            if(c.getEnd().equals(qPrim)){
                if(c.getTransitions().equals(t)){
                    if(c.getStart().inferior(qPrim)!=null){
                        return c.getStart();
                    }
                }
            }
        }
        return null;
    }

    /**
     * return new node if it exists else null
     * @return
     */
    public Node getNewNode(){
        for (Node n:nodes) {
            if(n.isNewTag()){
                return n;
            }
        }
        return null;
    }

    public Place getUnboundedPlaces(){
        for (Node n:nodes) {
            if(n.containsWMarking()){
                for (Place p:n.getPlaces()) {
                    if(p.isWmarking()){
                     return p;
                    }
                }
            }
        }
        return null;
    }

    public Node contain(Node q){
        for (Node n:nodes) {
            if(n.isEqual(q)){
                return n;
            }
        }
        return null;
    }

    public boolean remove(Node n){
        return nodes.remove(n);
    }


    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
