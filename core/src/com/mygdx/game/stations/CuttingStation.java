package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.foodClasses.Food;

import java.util.Stack;

public class CuttingStation extends CookingStation{
    public CuttingStation(Stack<Food> inventory, Sprite stationSprite, float operationTimer, boolean canLeaveUnattended) {
        //Set sprite, timer and canLeaveUnattended Here
        super(inventory, stationSprite, operationTimer, canLeaveUnattended);
    }
}