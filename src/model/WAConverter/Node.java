package model.WAConverter;

import model.MCGGenerator.IntMarking;
import model.MCGGenerator.Place;

public class Node extends model.MCGGenerator.Node {


    private Place unboundedPlace;
    private Interval safe;
    private int energy;

    public Node(String name, Place[] places, Place unboundedPlace, Interval safe, int energy, boolean newTag){
        super(name,places);
        this.unboundedPlace=unboundedPlace;
        this.safe=safe;
        this.setNewTag(newTag);
        this.setEnergy(energy);



    }

    public void setIntervalsList(Interval safeInt) {
        this.safe = safeInt;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }


    public Interval getSafe() {
        return safe;
    }

    public void setSafe(Interval safe) {
        this.safe = safe;
    }

    public String print(Place unboundedPlace) {
        //i changed the id to marking
        String result = "        <Node id=\"([";
        for (Place p : getPlaces()) {
            if(!p.getName().equals(unboundedPlace.getName()))
            result += p.toString() + " ";
        }
        result +="], "+this.getEnergy()+")\">\n         </Node>\n";

        return result;

    }

    public boolean exists(Node n, Place unboundedPlace){
        if(this.getEnergy()==n.getEnergy()){
            for(int i=0;i<this.getPlaces().length;i++){
                if(!this.getPlaces()[i].getName().equals(unboundedPlace.getName())){
                    int m1=((IntMarking)this.getPlaces()[i].getMarking()).getValue();
                    int m2=((IntMarking)n.getPlaces()[i].getMarking()).getValue();
                    if(m1!=m2){
                        return false;
                    }
                }
            }
            return true;
        }else
            return false;
    }

//    public static Node convert(model.MCGGenerator.Node mcgNode,Place unboundedPlace){
//        Node n=new Node(mcgNode.getName());
//        Place[] places=new Place[mcgNode.getPlaces().length-1];
//        int j=0;
//        for(int i=0;i<mcgNode.getPlaces().length;i++){
//            model.MCGGenerator.Place mPlace=mcgNode.getPlaces()[i];
//            if(!mPlace.getName().equals(unboundedPlace.getName())){
//                Place waPlace=new Place(((IntMarking)mPlace.getMarking()).getValue(),mPlace.getName());
//                places[j]=waPlace;
//                j++;
//            }
//
//        }
//        n.setPlacesList(places);
//
//        return n;
//
//    }
//
//    public model.MCGGenerator.Node convert(Node node,Place unboundedPlace){
//        for(int i=0;i<this.getPlacesList().length;i++){
//            if(this.getPlacesList()[i].getName().equals(node.getPlacesList())){
//
//            }
//        }
//
//        model.MCGGenerator.Node res=new model.MCGGenerator.Node(this.name,);
//    }
}
