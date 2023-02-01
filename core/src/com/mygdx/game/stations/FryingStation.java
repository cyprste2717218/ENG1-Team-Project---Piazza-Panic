package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.utils.SoundUtils;

public class FryingStation extends CookingStation{

    public FryingStation(float operationTimer, boolean canLeaveUnattended, Texture stationTexture) {
        super(operationTimer, canLeaveUnattended, stationTexture);
        operationLookupTable.put(FoodItems.BUN.name, FoodItems.TOASTED_BUN);
        operationLookupTable.put(FoodItems.RAW_PATTY.name, FoodItems.COOKED_PATTY);
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        super.onInteract(chef, interactedNode, tiledMap, grid, match);

    }

    @Override
    public void finishedTimer(Chef chef) {
        super.finishedTimer(chef);
    }
}
