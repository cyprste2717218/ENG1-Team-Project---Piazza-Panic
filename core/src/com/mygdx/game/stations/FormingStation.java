package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;

import java.util.*;

public class FormingStation extends CookingStation {
    public Stack inventory;
    public HashMap<List<Food>, Food> operationLookupTable_Forming;

    public FormingStation(float operationTimer, boolean canLeaveUnattended, Texture stationTexture) {
        super(operationTimer, canLeaveUnattended, stationTexture);
        operationLookupTable_Forming = new HashMap<>();
        this.inventory = new Stack<>();

        // finished food ingredients
        List<Food> burgerIngredients = Arrays.asList(FoodItems.TOASTED_BUN, FoodItems.COOKED_PATTY);
        List<Food> saladIngredients = Arrays.asList(FoodItems.CHOPPED_LETTUCE, FoodItems.CHOPPED_TOMATO, FoodItems.CHOPPED_ONION);
        operationLookupTable_Forming.put(burgerIngredients, FoodItems.BURGER);
        operationLookupTable_Forming.put(saladIngredients, FoodItems.SALAD);
    }
    // A function that loops through the stack of the forming station and
    // performs a hash map swap converting the group of items into one finished item
    private Food groupHashMapSwap() {
        // Creating an iterator of the stations stack
        Iterator inventoryIter = inventory.iterator();
        Boolean isAKey;
        // for each food array in the lookup
        for (List<Food> foodArray : operationLookupTable_Forming.keySet()) {
            isAKey = true;
            int counter = 0;
            while (inventoryIter.hasNext()) {
                // check if all items in food array have been checked
                // if so, can move onto the next hash map key
                if (counter > foodArray.size()) {
                    break;
                } else if (foodArray.contains(inventoryIter.next())) {
                    isAKey = true;
                // if food item is not in food array, moves onto next food array
                } else {
                    isAKey = false;
                    break;
                }
                counter += 1;
            }
            if (isAKey & (counter == foodArray.size())) {
                // clear the station stack
                this.inventory.clear();
                // convert the items in the stack into a finished food
                return this.operationLookupTable_Forming.get(foodArray);
            }
        }
        return null;
    }
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid) {
        // check if chef's stack is empty
        if(chef.foodStack.isEmpty()) return;
        // chef if item isn't formable
        if(!chef.foodStack.peek().isFormable) return;
        // if the water bucket is used with the station, the station is cleared
        if(chef.foodStack.peek() == FoodItems.WATER_BUCKET) {
            chef.foodStack.pop();
            this.inventory.clear();
            return;
        }

        // if the inventory is empty, then we just push an item, do not check hash map swap
        // this is because there are no recipes that form one item into a finished food
        if (this.inventory.isEmpty()) {
            System.out.println("Pushing onto empty: "+ chef.foodStack.peek().name);

            this.inventory.push(chef.foodStack.pop());
        } else if (inventory.contains(chef.foodStack.peek()))  {
            System.out.println("Item already in inventory");
            chef.foodStack.pop();
        } else  {
            System.out.println("Pushing more: "+chef.foodStack.peek().name);
            System.out.println("Stack length:"+this.inventory.size());
            this.inventory.push(chef.foodStack.pop());
            Food newFood = groupHashMapSwap();
            // checking to see if a finished food has been created
            if (newFood != null)    {
                System.out.println("Finished Food: "+newFood.name);
                chef.foodStack.push(newFood);
            }
        }
    }
}
