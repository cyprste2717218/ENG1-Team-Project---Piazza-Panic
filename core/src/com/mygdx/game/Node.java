package com.mygdx.game;

import com.mygdx.game.enums.NodeType;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Node.
 */
public class Node implements Comparable<Node>{

    private float g, h;
    private Node parent;
    private NodeType nodeType;
    private final int gridX;
    private final int gridY;
    private IGridEntity gridEntity;
    private IInteractable interactable;
    private static final Set<NodeType> INTERACTABLE_NODE_TYPES = new HashSet<>(Arrays.asList(NodeType.CHEF, NodeType.STATION, NodeType.FOOD));
    private static final Set<NodeType> COLLIDABLE_NODE_TYPES = new HashSet<>(Arrays.asList(NodeType.WALL, NodeType.STATION, NodeType.CUSTOMER));

    /**
     * Instantiates a new Node.
     *
     * @param gridX    the grid x
     * @param gridY    the grid y
     * @param nodeType the node type
     */
    public Node(int gridX, int gridY, NodeType nodeType){
        g = 0;
        h = 0;
        parent = null;
        this.nodeType = nodeType;
        this.gridX = gridX;
        this.gridY = gridY;
        gridEntity = null;
        interactable = null;
    }

    /**
     * Instantiates a new Node.
     *
     * @param gridX the grid x
     * @param gridY the grid y
     */
    public Node(int gridX, int gridY){
        this(gridX, gridY, NodeType.EMPTY);
    }

    /**
     * Gets f.
     *
     * @return the f
     */
    public float getF() {
        return g + h;
    }

    /**
     * Get g float.
     *
     * @return the float
     */
    public float getG(){
        return g;
    }

    /**
     * Set g.
     *
     * @param g the g
     */
    public void setG(float g){
        this.g = g;
    }

    /**
     * Set h.
     *
     * @param h the h
     */
    public void setH(float h){
        this.h = h;
    }

    /**
     * Get parent node.
     *
     * @return the node
     */
    public Node getParent(){
        return parent;
    }

    /**
     * Set parent.
     *
     * @param p the p
     */
    public void setParent(Node p){
        parent = p;
    }

    /**
     * Get grid x int.
     *
     * @return the int
     */
    public int getGridX(){
        return gridX;
    }

    /**
     * Get grid y int.
     *
     * @return the int
     */
    public int getGridY(){
        return gridY;
    }

    /**
     * Get node type node type.
     *
     * @return the node type
     */
    public NodeType getNodeType(){
        return nodeType;
    }

    /**
     * Set node type.
     *
     * @param nodeType the node type
     */
    public void setNodeType(NodeType nodeType){
        this.nodeType = nodeType;
    }

    /**
     * Is interactable boolean.
     *
     * @return the boolean
     */
    public boolean isInteractable(){
        return INTERACTABLE_NODE_TYPES.contains(nodeType);
    }

    /**
     * Is collidable boolean.
     *
     * @return the boolean
     */
    public boolean isCollidable(){
        return COLLIDABLE_NODE_TYPES.contains(nodeType);
    }

    /**
     * Gets grid entity.
     *
     * @return the grid entity
     */
    public IGridEntity getGridEntity() {
        return gridEntity;
    }

    /**
     * Get interactable interactable.
     *
     * @return the interactable
     */
    public IInteractable getInteractable(){return interactable;}

    /**
     * Set interactable.
     *
     * @param interactable the interactable
     */
    public void setInteractable(IInteractable interactable){
        this.interactable = interactable;
    }

    /**
     * Sets grid entity.
     *
     * @param gridEntity the grid entity
     */
    public void setGridEntity(IGridEntity gridEntity) {
        this.gridEntity = gridEntity;
    }

    /**
     * Compares the cost of using one node to another
     * @param n the object to be compared.
     * @return an integer,
     * As we know that we want the node with the least F cost, the node with the lowest returns -1
     * 0 if their path cost is the same
     */
    @Override
    public int compareTo(Node n) {
        if(this.getF() < n.getF()) return -1;
        if(this.getF() > n.getF()) return 1;
        return 0;
    }
}
