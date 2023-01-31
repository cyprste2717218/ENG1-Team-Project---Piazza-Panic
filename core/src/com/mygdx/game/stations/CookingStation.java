package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.utils.SoundUtils;

import java.util.HashMap;

public class CookingStation extends Station implements ITimer {

    public float operationTimer;
    public boolean canLeaveUnattended;
    // hash map to allow operations to be performed on foodItems:
    // Example: Onion -> Chopped Onion
    // Done via input food (key) being popped off chef's stack,
    // and output food (value) being pushed on
    public HashMap<String, Food> operationLookupTable;

    public CookingStation(float operationTimer, boolean canLeaveUnattended, Texture stationTexture) {
        super(null, stationTexture);
        this.operationTimer = operationTimer;
        this.canLeaveUnattended = canLeaveUnattended;
        operationLookupTable = new HashMap<>();
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        super.onInteract(chef, interactedNode, tiledMap, grid, match);

        // error checking
        if (chef.foodStack.isEmpty()) {
            System.out.println("Unable to interact, chefs foodStack is empty");
            return;
        } else if (!this.operationLookupTable.containsKey(chef.foodStack.peek().name)) {
            System.out.println("Cannot interact with this station, incorrect item");
            return;
        }
        // pushes the corresponding lookup of the popped item from chefs stack back onto the chefs stack
        // i.e. pops Bun, pushes Toasted Bun
        System.out.println(chef.foodStack.peek().name);
        chef.foodStack.push(new Food(this.operationLookupTable.get(chef.foodStack.pop().name)));
        System.out.println(chef.foodStack.peek().name);

        if(this instanceof CuttingStation){
            SoundUtils.getCuttingSound().play();
        }
        else if(this instanceof FryingStation){
            SoundUtils.getFryerSound().play();
        }

    }

    @Override
    public float runTimer(float timerValue) {
        if(timerValue == 0){
            //FAILURE CONDITION
        }
        return timerValue;
    }
}
