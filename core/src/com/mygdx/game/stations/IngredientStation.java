package com.mygdx.game.stations;

import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;

//Ingredient station is used to push a specific uncut/uncooked FoodItem onto the chefs stack
public class IngredientStation extends CookingStation   {
    public Food foodStock;

    public IngredientStation (Food food, float operationTimer, boolean canLeaveUnattended) {
        super(null, operationTimer, canLeaveUnattended);
        this.foodStock = food;
    }

}
