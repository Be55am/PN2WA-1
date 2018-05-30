package model;

import Views.Position;

public class Shape {
    private Position position;

    public Shape(Position position){
        this.position=position;
    }


    public Position getPosition() {
        return position;
    }


    public void setPosition(Position position) {
        this.position= position;

    }

}
