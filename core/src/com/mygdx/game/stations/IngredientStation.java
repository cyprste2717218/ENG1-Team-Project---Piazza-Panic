package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.utils.SoundUtils;

//Ingredient station is used to push a specific uncut/uncooked FoodItem onto the chefs stack
public class IngredientStation extends Station   {
    public IngredientStation (Food stock, Texture stationTexture) {
        super(stock, stationTexture);
    }


    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        // push one foodItem onto the interacting chef's foodStack
        if (!chef.foodStack.isEmpty())   {
            System.out.println(chef.foodStack.peek().getName());
        }
        chef.foodStack.push(this.stock);
        SoundUtils.getItemPickupSound().play();
        System.out.println(chef.foodStack.peek().getName());

    }

}
