package com.mygdx.game;

import com.mygdx.game.interfaces.IInteractable;

public class Node implements Comparable<Node>{

    private float g, h;
    private Node parent;
    private boolean isWall, isStation, isFood, isChef, isCustomer;
    private int gridX, gridY;
    private IInteractable interactable;

    public Node(int gridX, int gridY, boolean isWall){
        g = 0;
        h = 0;
        parent = null;
        this.isWall = isWall;
        this.isStation = false;
        this.isFood = false;
        this.gridX = gridX;
        this.gridY = gridY;
        interactable = null;
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

    public boolean getStation(){
        return isStation;
    }

    public void setStation(boolean isStation){
        this.isStation = isStation;
    }

    public boolean isFood(){
        return isFood;
    }

    public void setFood(boolean isFood){
        this.isFood = isFood;
    }

    public boolean isChef() {
        return isChef;
    }

    public void setChef(boolean chef) {
        isChef = chef;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public boolean isInteractable(){
        return isFood || isStation || isChef || isCustomer;
    }

    public boolean isCollidable(){
        return isWall || isStation || isFood;
    }

    public IInteractable getInteractable() {
        return interactable;
    }

    public void setInteractable(IInteractable interactable) {
        this.interactable = interactable;
    }


    @Override
    public int compareTo(Node n) {
        if(this.getF() < n.getF()) return -1;
        if(this.getF() > n.getF()) return 1;
        return 0;
    }
}
