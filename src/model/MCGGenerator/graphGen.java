package model.MCGGenerator;


import Controller.OverLoadException;
import model.PetriNet.Event;
import model.PetriNet.PetriNet;

import java.util.ArrayList;

public class graphGen {
    // this is Pu
    private ArrayList<Place> unboundedPlaces=new ArrayList<>();
    // this is Q
    private ArrayList<Node> generatedNodes=new ArrayList<>();
    private MCG graph;

    public graphGen(String name) {
        this.graph = new MCG(name);
    }

    public MCG generate(PetriNet net) throws OverLoadException {
        //1
        Node q0=net.getInitialMarking();
        q0.setNewTag(true);
        graph.addNode(q0);

        //2
        while (graph.getNewNode()!=null){

            System.out.println("calculating...");
            //2.1
            Node q=graph.getNewNode();
            //2.2
            ArrayList<Event> sdf=net.getEnabledEvents(q);
            for (Event e:sdf) {
               System.out.println(e.getName());
            }
            for (Event e:net.getEnabledEvents(q)) {

                //2.2.1
                String event=e.getName();
                generatedNodes=net.generateNextMarking(q,e);
                ArrayList<Transition> enabledTransitions=net.getEnabledTransitions(e,q);
                for (Transition t:enabledTransitions) {
                    String tr=t.getName();
                }
                //2.2.2
                for (Node qPrim:generatedNodes) {
                    //2.2.2.1
                    Connection c1=null;
                    if(q.containsWMarking()&!qPrim.containsWMarking()){
                        Place wp=q.getWPlace();
                        ArrayList<Transition> enabledTransitionsTemp=new ArrayList<>(enabledTransitions);

                        enabledTransitionsTemp.remove(net.getPreTransition(wp,e));
                        Connection c=new Connection(q,qPrim,enabledTransitionsTemp);
                        graph.addConnection(c);
                    }else{

                        if(q.isEqual(qPrim)){
                            c1=new Connection(q,q,enabledTransitions);
                        }else if(graph.contain(qPrim)!=null){
                            c1=new Connection(q,graph.contain(qPrim),enabledTransitions);
                        }
                        else {
                            c1= new Connection(q, qPrim, enabledTransitions);

                        }
                        graph.addConnection(c1);


                    }
                    //2.2.2.2
                    if(!graph.duplicated(qPrim)){
                        //2.2.2.3
                        if (!graph.duplicated(qPrim)) {
                            qPrim.setNewTag(true);
                            graph.addNode(qPrim);

                            //2.2.2.3
                            if (q.inferior(qPrim) != null) {// the transitions should be the same
                                Node qBar = graph.getPrecedentQ(q, enabledTransitions);
                                if (qBar != null) {
                                    for (int i = 0; i < q.getPlaces().length; i++) {
                                        if (qBar.getPlaces()[i].inferior(q.getPlaces()[i])) {
                                            if (q.getPlaces()[i].inferior(qPrim.getPlaces()[i])) {
                                                int k = ((IntMarking) qPrim.getPlaces()[i].getMarking()).getValue() - ((IntMarking) q.getPlaces()[i].getMarking()).getValue();
                                                int r = ((IntMarking) qPrim.getPlaces()[i].getMarking()).getValue() % k;
                                                int n = ((IntMarking) qPrim.getPlaces()[i].getMarking()).getValue() / k;
                                                WMarking wm = new WMarking(k, r, n);
                                                //in case the new Wmarking is already exists


                                                qPrim.getPlaces()[i].setM(wm);
                                                graph.remove(qPrim);
                                                if(graph.contain(qPrim)!=null){
                                                    c1.setEnd(graph.contain(qPrim));
                                                }else{
                                                    graph.addNode(qPrim);
                                                    qPrim.getPlaces()[i].setM(wm);
                                                }




                                                if(unboundedPlaces.size()==0){
                                                    unboundedPlaces.add(qPrim.getWPlace());
                                                }
                                                for (int j=0;j<unboundedPlaces.size();j++) {
                                                    Place p=unboundedPlaces.get(j);
                                                    if(!p.getName().equals(qPrim.getPlaces()[i].getName())){
                                                        unboundedPlaces.add(qPrim.getPlaces()[i]);
                                                    }
                                                }
                                                if(unboundedPlaces.size()>1){
                                                    System.out.println("more than one unbounded place !");
                                                    return null;
                                                }


                                              //  Connection cc=new Connection(qPrim,qPrim,net.getEnabledTransitions(e,q));
                                               // graph.addConnection(cc);


                                            }
                                        }
                                    }
                                }

                            }


                        }
                    }
                }

            }
            q.setNewTag(false);
            generatedNodes.clear();
            if(graph.getNodes().size()>10000){
                System.out.println("its not possible to detect the unbounded node ....");
                throw new OverLoadException();

            }

        }

        return graph;
    }
}
