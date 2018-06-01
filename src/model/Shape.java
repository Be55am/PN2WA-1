package model;

import Views.Position;
import Views.ShapeView;
import javafx.scene.Node;

public class Shape extends Node{
    private Position position;
    private String name;
    private int value;


    public Shape(Position position,String name,int value){
        this.position=position;
        this.name=name;
        this.value=value;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
