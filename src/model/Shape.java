package model;

import Views.Position;
import Views.ShapeView;
import javafx.scene.Node;

import java.io.Serializable;

public class Shape extends Node implements Serializable {
    private Position position;
    private String name;



    public Shape(Position position,String name){
        this.position=position;
        this.name=name;

    }


    public Position getPosition() {
        return position;
    }


    public void setPosition(Position position) {
        this.position= position;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

}
