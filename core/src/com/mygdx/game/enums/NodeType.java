package com.mygdx.game.enums;

public enum NodeType {
    EMPTY (" "),
    WALL ("X"),
    CHEF ("C"),
    CUSTOMER ("B"),
    STATION ("S"),
    FOOD ("F");

    private String representation;
    private int timeMultiplier;

    NodeType(String representation){
        this.representation = representation;
    }

    @Override
    public String toString(){
        return representation;
    }
}
