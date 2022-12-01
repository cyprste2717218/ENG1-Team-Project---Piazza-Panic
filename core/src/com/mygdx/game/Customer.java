package com.mygdx.game;

import com.mygdx.game.FoodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;

public class Customer implements IInteractable {
    boolean beenServed;
    Food order;
    float orderTimer;

    public Customer(Food order, float orderTimer){
        beenServed = false;
        this.order = order;
        this.orderTimer = orderTimer;
    }


    @Override
    public void onInteract() {

    }
}
