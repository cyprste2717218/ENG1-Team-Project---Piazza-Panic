package com.mygdx.game.stations;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;

//Ingredient station is used to push a specific uncut/uncooked FoodItem onto the chefs stack
public class IngredientStation extends Station   {
    public IngredientStation (Food stock) {
        super(stock);
    }


    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid) {
        // push one foodItem onto the interacting chef's foodStack
        if (!chef.foodStack.isEmpty())   {
            System.out.println(chef.foodStack.peek().name);
        }
        chef.foodStack.push(this.stock);
        System.out.println(chef.foodStack.peek().name);

    }

}
