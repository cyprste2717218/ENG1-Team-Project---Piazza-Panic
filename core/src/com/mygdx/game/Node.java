package com.mygdx.game;

import com.mygdx.game.enums.NodeType;
import com.mygdx.game.interfaces.IGridEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Node implements Comparable<Node>{

    private float g, h;
    private Node parent;
    private NodeType nodeType;
    private final int gridX;
    private final int gridY;
    private IGridEntity gridEntity;
    private static final Set<NodeType> INTERACTABLE_NODE_TYPES = new HashSet<>(Arrays.asList(NodeType.CHEF, NodeType.CUSTOMER, NodeType.STATION, NodeType.FOOD));
    private static final Set<NodeType> COLLIDABLE_NODE_TYPES = new HashSet<>(Arrays.asList(NodeType.WALL, NodeType.STATION, NodeType.FOOD, NodeType.CUSTOMER));

    public Node(int gridX, int gridY, NodeType nodeType){
        g = 0;
        h = 0;
        parent = null;
        this.nodeType = nodeType;
        this.gridX = gridX;
        this.gridY = gridY;
        gridEntity = null;
    }
    public Node(int gridX, int gridY){
        this(gridX, gridY, NodeType.EMPTY);
    }

    public float getF() {
        return g + h;
    }

    public float getG(){
        return g;
    }

    public void setG(float g){
        this.g = g;
    }

    public void setH(float h){
        this.h = h;
    }

    public Node getParent(){
        return parent;
    }

    public void setParent(Node p){
        parent = p;
    }

    public int getGridX(){
        return gridX;
    }

    public int getGridY(){
        return gridY;
    }

    public NodeType getNodeType(){
        return nodeType;
    }

    public void setNodeType(NodeType nodeType){
        this.nodeType = nodeType;
    }

    public boolean isInteractable(){
        return INTERACTABLE_NODE_TYPES.contains(nodeType);
    }

    public boolean isCollidable(){
        return COLLIDABLE_NODE_TYPES.contains(nodeType);
    }

    public IGridEntity getGridEntity() {
        return gridEntity;
    }

    public void setGridEntity(IGridEntity gridEntity) {
        this.gridEntity = gridEntity;
    }

    @Override
    public int compareTo(Node n) {
        if(this.getF() < n.getF()) return -1;
        if(this.getF() > n.getF()) return 1;
        return 0;
    }
}
