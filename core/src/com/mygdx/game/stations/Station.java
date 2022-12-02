package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.foodClasses.Food;
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
