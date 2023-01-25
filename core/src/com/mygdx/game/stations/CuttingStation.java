package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import java.util.Stack;

public class CuttingStation extends CookingStation{
    public CuttingStation(float operationTimer, boolean canLeaveUnattended) {
        //Set sprite, timer and canLeaveUnattended Here
        super(operationTimer, canLeaveUnattended);
    }
}