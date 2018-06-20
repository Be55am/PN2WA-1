package model;

import Controller.NoUnboundedPlaceException;
import MCGGeneration.*;
import MCGGeneration.Place;
import MCGGeneration.Transition;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class PetriNet implements Serializable{

    private String name;
    private Matrix pre;
    private Matrix post;
    private Matrix c;
    private Node initialMarking;

    public PetriNet(String name, Matrix pre, Matrix post, Node initialMarking) {
        this.name=name;
        this.pre = pre;
        this.post = post;
        calculateC();
        this.initialMarking=initialMarking;


    }

    public PetriNet(Graph graph,String netName){
        this.name=netName;
        System.out.println("Creating the net ...");
        MCGGeneration.Transition[] transitions=new MCGGeneration.Transition[graph.getTransitions().size()];
        String[] places=new String[graph.getPlaces().size()];
        int[][] valuesPre=new int[graph.getPlaces().size()][graph.getTransitions().size()];
        MCGGeneration.Place[] initialPlaces=new MCGGeneration.Place[places.length];

        for (int i = 0; i <graph.getTransitions().size(); i++) {
            String name=graph.getTransitions().get(i).getName();
            model.Event e=new model.Event(graph.getTransitions().get(i).getEvent());
            MCGGeneration.Transition t=new MCGGeneration.Transition(name,e);
            transitions[i]=t;
        }

        for (int i = 0; i <graph.getPlaces().size() ; i++) {
            places[i]=graph.getPlaces().get(i).getName();
            IntMarking m=new IntMarking(graph.getPlaces().get(i).getMarking());
            MCGGeneration.Place p=new MCGGeneration.Place(graph.getPlaces().get(i).getName(),m);
            initialPlaces[i]=p;
        }

        for (int i = 0; i <graph.getPlaces().size() ; i++) {
            for (int j = 0; j <graph.getTransitions().size() ; j++) {
                for (Arrow a:graph.getArrows()) {
                    if(a.getStartingShape().getName().equals(places[i])&a.getEndingShape().getName().equals(transitions[j].getName())){
                        valuesPre[i][j]=a.getWeight();
                    }
                }
            }
        }
         this.pre=new Matrix("Pre",transitions,places,valuesPre);

        //matrix post
        int[][] valuesPost=new int[graph.getPlaces().size()][graph.getTransitions().size()];

        for (int i = 0; i <graph.getPlaces().size() ; i++) {
            for (int j = 0; j <graph.getTransitions().size() ; j++) {
                for (Arrow a:graph.getArrows()) {
                    if(a.getStartingShape().getName().equals(transitions[j].getName())&a.getEndingShape().getName().equals(places[i])){
                        valuesPost[i][j]=a.getWeight();
                    }
                }
            }
        }

         this.post=new Matrix("Post",transitions,places,valuesPost);
        this.initialMarking=new MCGGeneration.Node("M0",initialPlaces);

        calculateC();

    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void calculateC(){

        int[][] val=new int[pre.getPlaces().length][pre.getTransitions().length];
        for(int i=0;i<val[0].length;i++){
            for(int j=0;j<val.length;j++){
                int x=post.getValues()[j][i]-pre.getValues()[j][i];
                val[j][i]=x;
            }
        }
        c=new Matrix("C",pre.getTransitions(),pre.getPlaces(),val);
    }

    public String toString(){
        String result="net:"+name+"\n";
        result+=pre.toString()+"\n";
        result+=post.toString()+"\n";
        result+=c.toString()+"\n";
        result+=initialMarking.toString()+"\n";

        return result;
    }

    public Node getInitialMarking() {
        return initialMarking;
    }

    public void setInitialMarking(Node initialMarking) {
        this.initialMarking = initialMarking;
    }

    /**
     * return a list of the enabled transitions in the marking n
     * @param n
     * @return
     */
    public ArrayList<Transition> getEnabledTransitions(Node n){
        int sum=0;
        ArrayList<Transition> result=new ArrayList<Transition>();
      for(int i=0;i<pre.getTransitions().length;i++){
          boolean b=true;
          for(int j=0;j<pre.getPlaces().length;j++){
              if(n.getPlaces()[j].getMarking() instanceof IntMarking) {
                  //if the marking is an integer
                  int marking=((IntMarking)n.getPlaces()[j].getMarking()).getValue();
                  sum =   marking-pre.getValues()[j][i];
                  if(sum<0){
                      b=false;
                  }
              }else{
                  // if the marking is w marking
//                  WMarking marking=(WMarking)n.getPlaces()[j].getMarking();
//                  // todo im not sure about this but looks right you should check it later
//                  int v=marking.getK()*marking.getN()+marking.getR();
//                  //todo i think this one should be pre and not c
//                  sum=c.getValues()[j][i]+v;
//                  if(sum<0){
                     // b=true;
//                  }

              }
          }
          boolean k=false;
          for (int j = 0; j < pre.getPlaces().length; j++) {
              if(pre.getValues()[j][i]!=0)
                  k=true;
          }
          if(b&k)
          result.add(pre.getTransitions()[i]);
      }
      return result;
    }

    /**
     * returns the events with the enabled transitions in the marking n
     * @param n
     * @return
     */
    public ArrayList<Event> getEnabledEvents(Node n){
        ArrayList<Event> result=new ArrayList<>();
        ArrayList<Transition> enabledTransitions=this.getEnabledTransitions(n);
        for (Transition t:enabledTransitions) {
            model.Event e=t.getEvent();
            if(result.size()>0) {
                boolean b=true;
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getName().equals(e.getName())) {
                        b=false;
                    }
                }
                if(b)
                result.add(e);
            }else {
                result.add(e);
            }

        }
        return result;
    }

    public ArrayList<Transition> getEnabledTransitions(Event e, Node n){
        ArrayList<Transition> eTransitions=getEventTransitions(e);
        ArrayList<Transition> enabledTrans=this.getEnabledTransitions(n);
        ArrayList<Transition> executionTrans=new ArrayList<>();

        //getting the enabled transitions of e
        for (Transition t:eTransitions) {
            if(enabledTrans.contains(t)){
                executionTrans.add(t);
            }
        }

        return executionTrans;
    }
    //return the transitions associated to an event e
    public ArrayList<Transition>getEventTransitions(Event e){
        ArrayList<Transition> result=new ArrayList<>();
        for (int i=0;i<this.pre.getTransitions().length;i++){
            Transition t=this.pre.getTransitions()[i];
            if(t.getEvent().getName().equals(e.getName())){
                result.add(t);
            }
        }
        return result;
    }

    public Node executeEvent(Event e, Node n){
        ArrayList<Transition> eTransitions=getEventTransitions(e);
        ArrayList<Transition> enabledTrans=this.getEnabledTransitions(n);
        ArrayList<Transition> executionTrans=new ArrayList<>();
        Place[] result=new Place[n.getPlaces().length];

        //getting the enabled transitions of e
        for (Transition t:eTransitions) {
           if(enabledTrans.contains(t)){
               executionTrans.add(t);
           }
        }

        //calculating the sum of c
        int[]cSum=new int[n.getPlaces().length];
        for(int i=0;i<cSum.length;i++){
            cSum[i]=0;
            for(int j=0;j<executionTrans.size();j++){
                for(int k=0;k<this.c.getTransitions().length;k++){
                    if(this.c.getTransitions()[k].equals(executionTrans.get(j))){
                        cSum[i]+=this.c.getValues()[i][k];
                    }
                }
            }

        }
        //the execution
        for(int i=0;i<cSum.length;i++){
            if(n.getPlaces()[i].getMarking() instanceof IntMarking){
                //the place has an int marking
                int x=cSum[i]+((IntMarking) n.getPlaces()[i].getMarking()).getValue();
                IntMarking marking=new IntMarking(x);
                Place p=new Place(n.getPlaces()[i].getName(),marking);
                result[i]=p;
            }else {
                // the place has wMarking
                int k=((WMarking)n.getPlaces()[i].getMarking()).getK();
                int r=((WMarking)n.getPlaces()[i].getMarking()).getR();
                int u=((WMarking)n.getPlaces()[i].getMarking()).getN();
                int x=cSum[i];


                    WMarking newMarking=new WMarking(k,r+x,u);

                    Place p=new Place(n.getPlaces()[i].getName(),newMarking);
                    result[i]=p;





            }

        }
        return new Node(null,result);

    }

    public ArrayList<Event> getEvents(){
        ArrayList<Event> result=new ArrayList<>();
        for (Transition t:pre.getTransitions()) {
            boolean b=false;
            if(result.size()>0) {
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getName().equals(t.getEvent().getName())) {
                        b = true;
                    }
                }
                if (!b) {
                    result.add(t.getEvent());
                }
            }else
                result.add(t.getEvent());

        }
        return result;
    }

    public Matrix getPre() {
        return pre;
    }

    public void setPre(Matrix pre) {
        this.pre = pre;
    }
    /**
     * this methode return a list of the transitions markings from the pre matrix of the unbounded place
     * @param place
     * @return
     */
    public ArrayList<Integer> getPre(String place){
        //todo we are removing the duplicated numbers im not sure if this is right or no
        ArrayList<Integer> res=new ArrayList();
        for(int i=0;i<pre.getPlaces().length;i++){
            if(pre.getPlaces()[i].equals(place)){
                for(int j=0;j<pre.getTransitions().length;j++){
                    if(pre.getValues()[i][j]!=0){
                        res.add(pre.getValues()[i][j]);
                    }
                }
            }
        }
        SortedSet set=new TreeSet();
        set.addAll(res);


        return new ArrayList<>(set);
    }
    /**
     * delta is the sum of the enabled transitions x unbounded place in the c matrix
     * @param unboundedPlace
     * @param node
     * @param event
     * @return
     */
    public int getDelta(Place unboundedPlace, Node node, Event event){
        ArrayList<Transition>enabledTransitions=this.getEnabledTransitions(event,node);
        int delta=0;
        for (int i=0;i<c.getPlaces().length;i++){
            if(c.getPlaces()[i].equals(unboundedPlace.getName())) {
                for (int j = 0; j < c.getTransitions().length; j++) {
                    if(enabledTransitions.contains(c.getTransitions()[j])){
                        delta+=c.getValues()[i][j];
                    }
                }
            }
        }
        return delta;
    }
    public Marking getInitialMarking(Place p){
        Node n=this.getInitialMarking();
        for (Place place:n.getPlaces()) {
            if(place.getName().equals(p.getName())){
                return place.getMarking();
            }
        }
        System.out.println("place not found in get initial marking methode");
        return null;
    }
    public Place getInitialUnboundedMarking(Place p) throws NoUnboundedPlaceException {

        if(p==null)
            return null;

        for (Place place : initialMarking.getPlaces()) {
            if (p.getName().equals(place.getName())) {
                    return place;
            }
        }

        System.out.println("unbounded place marking not found petriNet:getInitialUnboundedMarking().");
        return null;
    }
    public ArrayList<Node> generateNextMarking(Node q, Event e){

        ArrayList<Transition> eTransitions=getEventTransitions(e);
        ArrayList<Transition> enabledTrans=this.getEnabledTransitions(q);
        ArrayList<Transition> executionTrans=new ArrayList<>();
        Place[] result=new Place[q.getPlaces().length];
        ArrayList<Node> resultat=new ArrayList<>();

        //getting the enabled transitions of e
        for (Transition t:eTransitions) {
            if(enabledTrans.contains(t)){
                executionTrans.add(t);
            }
        }

        //calculating the sum of c
        int[]cSum=new int[q.getPlaces().length];
        for(int i=0;i<cSum.length;i++){
            cSum[i]=0;
            for(int j=0;j<executionTrans.size();j++){
                for(int k=0;k<this.c.getTransitions().length;k++){
                    if(this.c.getTransitions()[k].equals(executionTrans.get(j))){
                        cSum[i]+=this.c.getValues()[i][k];
                    }
                }
            }

        }

        // classifying the node

        if(q.containsWMarking()){
            //q is a w marking
            Place wp=q.getWPlace();
            WMarking wm=(WMarking) wp.getMarking();
            int k=wm.getK();
            int n=wm.getN();
            int r=wm.getR();

            ArrayList<Transition> enabledTransitions= this.getEnabledTransitions(e,q);
            int sum=0;

            for (int i = 0; i <c.getPlaces().length ; i++) {
                if(c.getPlaces()[i].equals(wp.getName())){

                    for (int j = 0; j <pre.getTransitions().length ; j++) {
                        if(enabledTransitions.contains(pre.getTransitions()[j])){
                            sum+=pre.getValues()[i][j];

                        }
                    }
                }
            }


            if((k*n+r)<sum){
                // q is partially enable

                for(int i=0;i<q.getPlaces().length;i++){
                    if(q.getPlaces()[i].getName().equals(wp.getName())){
                        IntMarking marking=new IntMarking(0);
                        Place p1=new Place(q.getPlaces()[i].getName(),marking);
                        result[i]=p1;


                    }else{
                        result[i]=q.getPlaces()[i];
                    }
                }
                Node n1=new Node(null,result);
                resultat=generateNextMarking(n1,e);

                for(int i=0;i<q.getPlaces().length;i++){
                    if(q.getPlaces()[i].getName().equals(wp.getName())){
                        WMarking marking=new WMarking(k,r,n+1);
                        Place p1=new Place(q.getPlaces()[i].getName(),marking);
                        result[i]=p1;


                    }else{
                        result[i]=q.getPlaces()[i];
                    }
                }
                Node n2=new Node(null,result);
                resultat.addAll(generateNextMarking(n2,e));

                //this block removes the duplicated nodes
                if(resultat.size()>2)
                for(int i=0;i<resultat.size()-1;i++){
                    if(resultat.get(i).isEqual(resultat.get(i+1)));
                    resultat.remove(resultat.get(i));
                }





            }else {
                // q is totally enabled
                for(int i=0;i<cSum.length;i++){
                    if(q.getPlaces()[i].getMarking() instanceof IntMarking){
                        //the place has an int marking
                        int x=cSum[i]+((IntMarking) q.getPlaces()[i].getMarking()).getValue();
                        IntMarking marking=new IntMarking(x);
                        Place p=new Place(q.getPlaces()[i].getName(),marking);
                        result[i]=p;
                    }else {
                        // the place has wMarking
                        int k2=((WMarking)q.getPlaces()[i].getMarking()).getK();
                        int r2=((WMarking)q.getPlaces()[i].getMarking()).getR();
                        int n2=((WMarking)q.getPlaces()[i].getMarking()).getN();
                        int x=cSum[i];
                        int y=k2*n2+r2;

                      if(x>=0){
                          if(x+r2>=k2){
                              n2=(y+x)/k2;
                              r2=(y+x)%k2;
                          }else{
                              r2+=x;
                          }
                      }else{
                          if((-x)<=r2){
                              r2=r2+x;
                          }else{
                              n2=(y+x)/k2;
                              r2=(y+x)%k2;
                          }
                      }
                        WMarking newMarking=new WMarking(k2,r2,n2);

                        Place p=new Place(q.getPlaces()[i].getName(),newMarking);
                        result[i]=p;

                    }

                }
                Node res=new Node(null,result);
                resultat.add(res);
            }

        }else{
            // q is a I marking

            //the execution
            for(int i=0;i<cSum.length;i++){
                int x=cSum[i]+((IntMarking) q.getPlaces()[i].getMarking()).getValue();
                IntMarking marking=new IntMarking(x);
                Place p=new Place(q.getPlaces()[i].getName(),marking);
                result[i]=p;
                }

            Node res=new Node(null,result);
            resultat.add(res);
        }


        return resultat;
    }
    public Transition getPreTransition(Place p, Event e){
        for (int i = 0; i <pre.getPlaces().length; i++) {
            if(pre.getPlaces()[i].equals(p.getName())){
                for (int j = 0; j <pre.getTransitions().length ; j++) {
                    if(pre.getTransitions()[j].getEvent().getName().equals(e.getName())){
                        if(pre.getValues()[i][j]!=0){
                            return pre.getTransitions()[j];
                        }
                    }
                }
            }
        }
        System.out.println("no transition found : getPreTransition()");
        return null;
    }

    public boolean isDeterministic(){
        for (int i = 0; i <pre.getPlaces().length ; i++) {
            for (int j = 0; j < pre.getTransitions().length; j++) {
                if(pre.getValues()[i][j]!=0) {
                    Transition t = pre.getTransitions()[j];
                    for (int k = 0; k < pre.getTransitions().length; k++) {
                        if (pre.getValues()[i][k] != 0) {
                            if (j != k & pre.getTransitions()[k].getEvent().getName().equals(t.getEvent().getName())){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}






