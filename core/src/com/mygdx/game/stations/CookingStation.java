package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.interfaces.ITimer;

import java.util.HashMap;
import java.util.Stack;

public class CookingStation extends Station implements ITimer {
    public float operationTimer;
    public boolean canLeaveUnattended;
    // hash map to allow operations to be performed on foodItems:
    // Example: Onion -> Chopped Onion
    // Done via input food (key) being popped off chef's stack,
    // and output food (value) being pushed on
    public HashMap<Food, Food> operationLookupTable;

    public CookingStation(Stack<Food> inventory, float operationTimer, boolean canLeaveUnattended) {
        super(inventory);
        this.operationTimer = operationTimer;
        this.canLeaveUnattended = canLeaveUnattended;
        operationLookupTable = new HashMap<>();
    }

    @Override
    public float runTimer(float timerValue) {
        if(timerValue == 0){
            //FAILURE CONDITION
        }
        return timerValue;
    }
}
