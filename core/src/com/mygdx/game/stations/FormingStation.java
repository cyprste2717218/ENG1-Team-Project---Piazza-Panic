package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.utils.SoundUtils;

import java.util.*;

/**
 * The type Forming station.
 */
public class FormingStation extends CookingStation {
    /**
     * The Inventory.
     */
    Stack<Food> inventory;
    /**
     * The Operation lookup table forming.
     */
    HashMap<List<String>, Food> operationLookupTable_Forming;


    /**
     * Instantiates a new Forming station.
     *
     * @param operationTimer
     * @param canLeaveUnattended the can leave unattended
     * @param stationTexture     the station texture
     */
    public FormingStation(float operationTimer, boolean canLeaveUnattended, Texture stationTexture) {
        super(operationTimer, canLeaveUnattended, stationTexture);
        operationLookupTable_Forming = new HashMap<>();
        this.inventory = new Stack<>();

        // finished food ingredients
        List<String> burgerIngredients = Arrays.asList(FoodItems.TOASTED_BUN.getName(), FoodItems.COOKED_PATTY.getName());
        List<String> saladIngredients = Arrays.asList(FoodItems.CHOPPED_LETTUCE.getName(), FoodItems.CHOPPED_TOMATO.getName(), FoodItems.CHOPPED_ONION.getName());
        operationLookupTable_Forming.put(burgerIngredients, FoodItems.BURGER);
        operationLookupTable_Forming.put(saladIngredients, FoodItems.SALAD);
    }
    // A function that loops through the stack of the forming station and
    // performs a hash map swap converting the group of items into one finished item

    /**
     * Checks to see if the inventory list matches any recipies in the hashmap
     * @return the food found from combining every item in the inventory, or null if nothing is found
     */
    private Food groupHashMapSwap() {
        //Convert the inventory into a string list
        List<String> components = new ArrayList<>();
        for(Food food: inventory)components.add(food.getName());
        for(List<String> recipe: operationLookupTable_Forming.keySet()){
            if(recipe.containsAll(components) && recipe.size() == components.size()) return new Food(operationLookupTable_Forming.get(recipe));
        }
        return null;
    }

    /**
     *
     * Allows you to place as many food items in this station as you want
     * A water bucket clears the inventory
     * Used to combine foods in recipies to produce a final product
     *
     * @param chef           the chef that interacted with the object
     * @param interactedNode the interacted node - the node which is being interacted with
     * @param tiledMap       the tiled map
     * @param grid           the grid
     * @param match          the match
     */
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        // check if chef's stack is empty
        if(chef.getFoodStack().isEmpty()) return;
        // chef if item isn't formable
        if(!chef.getFoodStack().peek().isFormable()) return;
        // if the water bucket is used with the station, the station is cleared
        if(chef.getFoodStack().peek().equals(FoodItems.WATER_BUCKET)) {
            System.out.println("Clearing Forming Station");
            chef.getFoodStack().pop();
            this.inventory.clear();
            return;
        }

        SoundUtils.getFormingSound().play();
        // if the inventory is empty, then we just push an item, do not check hash map swap
        // this is because there are no recipes that form one item into a finished food
        if (this.inventory.isEmpty()) {
            System.out.println("Pushing onto empty: "+ chef.getFoodStack().peek().getName());
            this.inventory.push(chef.getFoodStack().pop());
        } else if (inventory.contains(chef.getFoodStack().peek()))  {
            System.out.println("Item already in inventory");
            chef.getFoodStack().pop();
        } else  {
            System.out.println("Pushing more: "+ chef.getFoodStack().peek().getName());
            System.out.println("Stack length:"+this.inventory.size());
            this.inventory.push(chef.getFoodStack().pop());
            Food newFood = groupHashMapSwap();
            // checking to see if a finished food has been created
            if (newFood != null)    {
                System.out.println("Finished Food: "+ newFood.getName());
                chef.getFoodStack().push(newFood);
            }
        }
        SoundUtils.getFormingSound().stop();
    }
}
