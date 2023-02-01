package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.utils.SoundUtils;

/**
 * The type Frying station.
 */
public class FryingStation extends CookingStation{

    /**
     * Instantiates a new Frying station.
     *
     * @param operationTimer     the operation timer
     * @param canLeaveUnattended the can leave unattended
     * @param stationTexture     the station texture
     */
    public FryingStation(float operationTimer, boolean canLeaveUnattended, Texture stationTexture) {
        super(operationTimer, canLeaveUnattended, stationTexture);
        operationLookupTable.put(FoodItems.BUN.getName(), FoodItems.TOASTED_BUN);
        operationLookupTable.put(FoodItems.RAW_PATTY.getName(), FoodItems.COOKED_PATTY);
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        super.onInteract(chef, interactedNode, tiledMap, grid, match);
        SoundUtils.getFryerSound().stop();
    }
}
