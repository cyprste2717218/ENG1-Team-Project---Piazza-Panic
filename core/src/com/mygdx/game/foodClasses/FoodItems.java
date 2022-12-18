package com.mygdx.game.foodClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodItems {
    public static List<Food> finishedFoods = new ArrayList<>();


    public static Food SALAD = new Food(new Food.FoodBuilder("Salad",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(50));

    public static Food LETTUCE = new Food(new Food.FoodBuilder("Lettuce",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setChoppable());

    public static Food TOMATO = new Food(new Food.FoodBuilder("Tomato",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setChoppable());

    public static Food ONION = new Food(new Food.FoodBuilder("Onion",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setChoppable());

    public static Food BURGER = new Food(new Food.FoodBuilder("Burger",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(25));

    //Maybe rename to beef mince - alternatively we can create just a patty item that is formable and fryable and do our logical checks later
    public static Food UNFORMED_PATTY = new Food(new Food.FoodBuilder("Unformed Patty",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFormable());

    public static Food PATTY = new Food(new Food.FoodBuilder("Patty",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFryable());

    public static Food BUN = new Food(new Food.FoodBuilder("Bun",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setToastable());

    public static Food JACKET_POTATO = new Food(new Food.FoodBuilder("Jacket Potato",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(10));

    public static Food RAW_POTATO = new Food(new Food.FoodBuilder("Raw Potato",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setBakeable().setChoppable());

    public static Food PIZZA = new Food(new Food.FoodBuilder("Pizza",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(50));

    //We need tomatoes, cheese, dough, flour

    //  menu for food orders
    public static List<Food> menu = new ArrayList<>(Arrays.asList(SALAD,BURGER));
}
