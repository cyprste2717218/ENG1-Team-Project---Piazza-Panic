package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.utils.TileMapUtils;

import java.util.Random;

public class Customer implements IInteractable, ITimer {
    boolean beenServed;

    public Sprite customerSprite;
    Food order;
    float orderTimer;

    private Vector2 gridPosition;


    public Customer(float orderTimer){
        beenServed = false;
        order = getRandomOrder();
        this.orderTimer = orderTimer;
    }

    private Food getRandomOrder() {
        Random rnd = new Random();
        int orderIndex = rnd.nextInt(FoodItems.finishedFoods.size()-1);
        return FoodItems.finishedFoods.get(orderIndex);
    }


    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap) {
        if(!chef.foodStack.isEmpty()) {
            if (chef.foodStack.peek() == order) {
                PiazzaPanic.CUSTOMER_SERVED_COUNTER++;
            }
        }
    }

    @Override
    public Vector2 getPreviousGridPosition() {
        return gridPosition;
    }

    @Override
    public void setCurrentGridPosition(Vector2 gridPos) {
        gridPosition = gridPos;
    }

    @Override
    public float runTimer(float timerValue) {
        if(timerValue == 0){
            //FAILURE CONDITION
        }
        return timerValue--;
    }
}
