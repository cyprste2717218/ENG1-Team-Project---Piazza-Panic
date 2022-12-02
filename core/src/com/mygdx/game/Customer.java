package com.mygdx.game;

import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.interfaces.ITimer;

public class Customer implements IInteractable, ITimer {
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

    @Override
    public float runTimer(float timerValue) {
        if(timerValue == 0){
            //FAILURE CONDITION
        }
        return timerValue--;
    }
}
