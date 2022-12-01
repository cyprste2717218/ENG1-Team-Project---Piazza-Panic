package com.mygdx.game.FoodClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class FoodItems {
    public static List<Food> finishedFoods = new ArrayList<>();

    public static Food SALAD = new Food(new Food.FoodBuilder("Salad",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(50));

    public static Food BURGER = new Food(new Food.FoodBuilder("Burger",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(25));

}
