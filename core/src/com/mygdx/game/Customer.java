package com.mygdx.game;

import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.interfaces.ITimer;

import java.util.Random;

public class Customer implements IInteractable, ITimer {
    boolean beenServed;
    Food order;
    float orderTimer;

    public Customer(float orderTimer){
        beenServed = false;
        order = getRandomOrder();
        this.orderTimer = orderTimer;
    }

    private Food getRandomOrder(){
        Random rnd = new Random();
        int orderIndex = rnd.nextInt(FoodItems.finishedFoods.size() - 1);
        return FoodItems.finishedFoods.get(orderIndex);
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
