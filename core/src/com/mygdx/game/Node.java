package com.mygdx.game;

public class Node implements Comparable<Node>{

    private float g, h;
    private Node parent;
    private boolean isWall;
    private int gridX, gridY;

    public Node(int gridX, int gridY, boolean isWall){
        g = 0;
        h = 0;
        parent = null;
        this.isWall = isWall;
        this.gridX = gridX;
        this.gridY = gridY;
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

    public boolean getWall(){
        return isWall;
    }

    public int getGridX(){
        return gridX;
    }

    public int getGridY(){
        return gridY;
    }

    @Override
    public int compareTo(Node n) {
        if(this.getF() < n.getF()) return -1;
        if(this.getF() > n.getF()) return 1;
        return 0;
    }
}
