package com.mygdx.game.stations;

import com.badlogic.gdx.audio.Sound;
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

/**
 * The type Cooking station - stations used to perform operations that transform one foodItem to another.
 */
public class CookingStation extends Station implements ITimer {

    private float operationTimer;
    /**
     * Whether the chef can leave the station or not, currently NYI.
     */
    protected boolean canLeaveUnattended;
    /**
     * The Timer - the amount of time an operation in the station takes.
     */
    TimerUtils timer;
    /**
     * The Hashmap of operations that can be performed on foodItems for this station
     * Example: Onion -> Chopped Onion
     *     Done via input food (key) being popped off chef's stack,
     *     and output food (value) being pushed on
     */
// hash map to allow operations to be performed on foodItems:
    //
    public HashMap<String, Food> operationLookupTable;
    private Sound stationSound;

    /**
     * Instantiates a new Cooking station.
     *
     * @param operationTimer     the time an operation for this station takes
     * @param canLeaveUnattended Whether the station can be left alone, currently NYI
     * @param stationTexture     the station texture
     */
    public CookingStation(float operationTimer, boolean canLeaveUnattended, Texture stationTexture) {
        super(null, stationTexture);
        this.operationTimer = operationTimer;
        this.canLeaveUnattended = canLeaveUnattended;
        operationLookupTable = new HashMap<>();
    }
    
    public void setStationSound()    {
        if (this instanceof CuttingStation) {
            stationSound = SoundUtils.getCuttingSound();
            stationSound.loop();
        }   else if (this instanceof FryingStation) {
            stationSound = SoundUtils.getFryerSound();
        }
    }

    /**
     * When stations of this type are interacted with, if a valid food is placed into them, they will start their timers.
     * When the timer is finished they can be interacted with again to retrieve the transformed food
     *
     * @param chef           the chef that interacted with the object
     * @param interactedNode the interacted node - the node which is being interacted with
     * @param tiledMap       the tiled map
     * @param grid           the grid
     * @param match          the match
     */
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        super.onInteract(chef, interactedNode, tiledMap, grid, match);
        if(timer == null) timer = new TimerUtils(operationTimer, this, match, getSprite());
        if(timer.getIsRunning()) return;
        if(stock != null && !timer.getIsRunning()){
            chef.getFoodStack().push(stock);
            stock = null;
            timer = new TimerUtils(operationTimer, this, match, getSprite());
        }
        else{
            // error checking
            if (chef.getFoodStack().isEmpty()) {
                System.out.println("Unable to interact, chefs foodStack is empty");
                return;
            } else if (!this.operationLookupTable.containsKey(chef.getFoodStack().peek().getName())) {
                System.out.println("Cannot interact with this station, incorrect item");
                return;
            }
            stock = new Food(this.operationLookupTable.get(chef.getFoodStack().pop().getName()));
            timer.setIsRunning(true);
            setStationSound();
            stationSound.play();
        }
    }

    @Override
    public void finishedTimer(Chef chef) {
        System.out.println(stock);
        SoundUtils.getTimerFinishedSound().play();
        stationSound.stop();
    }
}
