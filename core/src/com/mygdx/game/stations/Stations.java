package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;

import java.util.Stack;

public class Stations {
    static Texture testTexture = new Texture("badlogic.jpg");
    static Sprite testSprite = new Sprite(testTexture);

    public static CookingStation TEST_STATION = new CookingStation(new Stack<Food>(), testSprite, 500, false);

    private static void setUpTestStation(){
        TEST_STATION.operationLookupTable.put(FoodItems.BURGER, FoodItems.SALAD);
        TEST_STATION.operationLookupTable.put(FoodItems.SALAD, FoodItems.BURGER);
    }

    public static void setUpStations(){
        setUpTestStation();
    }
}
