package com.mygdx.game.enums;

//An enum detailing every kind of node on the pathfinding grid
//The letters are used in the representGridAsString() method in PathfindingUtils
public enum NodeType {
    EMPTY (" "),
    WALL ("X"),
    CHEF ("C"),
    CUSTOMER ("B"), //B for Buyer?
    STATION ("S"),
    FOOD ("F");

    private String representation;

    NodeType(String representation){
        this.representation = representation;
    }

    @Override
    public String toString(){
        return representation;
    }
}
