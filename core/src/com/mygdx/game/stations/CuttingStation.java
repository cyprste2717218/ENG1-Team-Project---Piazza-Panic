package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.utils.SoundUtils;

public class CuttingStation extends CookingStation{

    public CuttingStation(float operationTimer, boolean canLeaveUnattended, Texture stationTexture) {
        //Set sprite, timer and canLeaveUnattended Here
        super(operationTimer, canLeaveUnattended, stationTexture);
        operationLookupTable.put(FoodItems.LETTUCE.getName(), FoodItems.CHOPPED_LETTUCE);
        operationLookupTable.put(FoodItems.ONION.getName(), FoodItems.CHOPPED_ONION);
        operationLookupTable.put(FoodItems.TOMATO.getName(), FoodItems.CHOPPED_TOMATO);
        operationLookupTable.put(FoodItems.CHEESE.getName(), FoodItems.SLICED_CHEESE);
        operationLookupTable.put(FoodItems.BEEF_MINCE.getName(), FoodItems.RAW_PATTY);
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        super.onInteract(chef, interactedNode, tiledMap, grid, match);
        SoundUtils.getCuttingSound().stop();
    }
}