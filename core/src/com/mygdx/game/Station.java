package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.FoodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;

import java.util.Stack;

public class Station implements IInteractable {

    public Stack<Food> inventory;
    public Sprite stationSprite;

    public Station(Stack<Food> inventory, Sprite stationSprite){
        this.inventory = inventory;
        this.stationSprite = stationSprite;
    }

    @Override
    public void onInteract() {

    }
}
