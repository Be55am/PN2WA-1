package model.MCGGenerator;

import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable{

    private String name;
    private boolean newTag;
    private Place[] places;

    public Node(String name, Place[] places) {
        this.name = name;
        this.places = places;
    }

    /**
     * return true if the new tag is true.
     * @return
     */
    public boolean isNewTag() {
        return newTag;
    }

    public void setNewTag(boolean newTag) {
        this.newTag = newTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Place[] getPlaces() {
        return places;
    }

    public void setPlaces(Place[] places) {
        this.places = places;
    }

    public String toString(){
        String result="     <node>\n";
        result+="              <marking>[";
        for (Place p:places) {
            result+=p.toString()+" ";
        }
        result+="]</marking>\n";
        result+="        </node>\n";
        return result;
    }

    public String print(){
        String result="[";
        for (Place p:places) {
            result+=p.toString()+" ";
        }
        result+="]";
        return result;
    }

    public boolean isEqual(Node n){
        for(int i=0;i<places.length;i++){
            if(!places[i].isEqual(n.getPlaces()[i]))
                return false;
        }
        return true;
    }

    public boolean containsWMarking(){
        for (Place p:places) {
            if(p.isWmarking())
                return true;

        }
        return false;
    }

    public ArrayList<Place> inferior(Node n){
        ArrayList<Place>result=new ArrayList<>();
        boolean b=false;
        for(int i=0;i<places.length;i++){
            Place[] x=places;
            x[i].toString();
            if(places[i].inferior(n.getPlaces()[i])){
                b=true;
                result.add(places[i]);
            }else if(places[i].isEqual(n.getPlaces()[i])){

            }else
                return null;
        }
        if(b)
        return result;
        else
            return null;
    }

    public model.WAConverter.Node convertToWANode(Place unboundedPlace){
        for (Place p:places) {
//            if(p.getName().equals(unboundedPlace.getName())){
//                p.setM(new IntMarking(0));
//            }
        }
        return new model.WAConverter.Node(this.getName(),this.places,unboundedPlace,null,-9999,true);
    }

    public Place getWPlace(){
        for (Place p:this.getPlaces()) {
            if(p.getMarking() instanceof WMarking){
                return  p;
            }
        }
        return null;
    }


}
