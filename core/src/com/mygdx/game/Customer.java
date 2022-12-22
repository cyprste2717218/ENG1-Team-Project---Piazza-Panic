package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private Food getRandomOrder() {
        Random rnd = new  Random();
        int orderIndex = rnd.nextInt(FoodItems.finishedFoods.size()-1);
        return FoodItems.finishedFoods.get(orderIndex);
    }


    @Override
    public void onInteract(Chef chef) {
        if(!chef.foodStack.isEmpty()) {
            if (chef.foodStack.peek() == order) {
                PiazzaPanic.CUSTOMER_SERVED_COUNTER++;
            }

        }
    }


    @Override
    public float runTimer(float timerValue) {
        if(timerValue == 0){
            //FAILURE CONDITION
        }
        return timerValue--;
    }





}
