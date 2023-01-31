package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TimerUtils;

import java.util.HashMap;

public class CookingStation extends Station implements ITimer {

    public float operationTimer;
    public boolean canLeaveUnattended;
    TimerUtils timer;
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
        if(timer == null) timer = new TimerUtils(operationTimer, this, match, getSprite());
        if(timer.getIsRunning()) return;
        if(stock != null && !timer.getIsRunning()){
            chef.foodStack.push(stock);
            stock = null;
            timer = new TimerUtils(operationTimer, this, match, getSprite());
        }
        else{
            // error checking
            if (chef.foodStack.isEmpty()) {
                System.out.println("Unable to interact, chefs foodStack is empty");
                return;
            } else if (!this.operationLookupTable.containsKey(chef.foodStack.peek().getName())) {
                System.out.println("Cannot interact with this station, incorrect item");
                return;
            }
            stock = new Food(this.operationLookupTable.get(chef.foodStack.pop().getName()));
            timer.setIsRunning(true);
        }








        // pushes the corresponding lookup of the popped item from chefs stack back onto the chefs stack
        // i.e. pops Bun, pushes Toasted Bun


        if(this instanceof CuttingStation){
            SoundUtils.getCuttingSound().play();
        }
        else if(this instanceof FryingStation){
            SoundUtils.getFryerSound().play();
        }

    }

    @Override
    public void finishedTimer(Chef chef) {
        System.out.println(stock);
        SoundUtils.getTimerFinishedSound().play();
    }
}
