package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.utils.SoundUtils;

/**
 * The type Ingredient station.
 */
//Ingredient station is used to push a specific uncut/uncooked FoodItem onto the chefs stack
public class IngredientStation extends Station   {
    /**
     * Instantiates a new Ingredient station.
     *
     * @param stock          the stock
     * @param stationTexture the station texture
     */
    public IngredientStation (Food stock, Texture stationTexture) {
        super(stock, stationTexture);
    }


    /**
     * Places an ingredient from this station on to the chef's food stack
     * @param chef           the chef that interacted with the object
     * @param interactedNode the interacted node - the node which is being interacted with
     * @param tiledMap       the tiled map
     * @param grid           the grid
     * @param match          the match
     */
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        // push one foodItem onto the interacting chef's foodStack
        if (!chef.getFoodStack().isEmpty())   {
            System.out.println(chef.getFoodStack().peek().getName());
        }
        chef.getFoodStack().push(this.stock);
        SoundUtils.getItemPickupSound().play();
        System.out.println(chef.getFoodStack().peek().getName());

    }

}
