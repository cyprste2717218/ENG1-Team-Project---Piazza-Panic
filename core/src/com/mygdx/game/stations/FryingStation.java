package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.FoodItems;

public class FryingStation extends CookingStation{

    public FryingStation(float operationTimer, boolean canLeaveUnattended, Texture stationTexture) {
        super(operationTimer, canLeaveUnattended, stationTexture);
        operationLookupTable.put(FoodItems.BUN, FoodItems.TOASTED_BUN);
        operationLookupTable.put(FoodItems.RAW_PATTY, FoodItems.COOKED_PATTY);
    }
}
