package com.mygdx.game.enums;

/**
 * The enum Node type - details every kind of node on the pathfinding grid
 * The letters are used in the representGridAsString() method in PathfindingUtils.
 */

public enum NodeType {
    /**
     * Empty node type.
     */
    EMPTY (" "),
    /**
     * Wall node type.
     */
    WALL ("X"),
    /**
     * Chef node type.
     */
    CHEF ("C"),
    /**
     * Customer node type - B for Buyer is used to not conflict with C for Chef.
     */
    CUSTOMER ("B"),
    /**
     * Station node type.
     */
    STATION ("S"),
    /**
     * Food node type.
     */
    FOOD ("F");

    private String representation;

    NodeType(String representation){
        this.representation = representation;
    }

    /**
     * @return a String representation of each Node Type
     */
    @Override
    public String toString(){
        return representation;
    }
}
