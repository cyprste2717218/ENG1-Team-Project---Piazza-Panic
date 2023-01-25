package com.mygdx.game.foodClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodItems {
    //A list of finished foods, e.g. Burger, which has any Food with a reward automatically added to it
    public static List<Food> finishedFoods = new ArrayList<>();


    public static Food SALAD = new Food(new Food.FoodBuilder("Salad",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(50));

    public static Food LETTUCE = new Food(new Food.FoodBuilder("Lettuce",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setChoppable());

    public static Food CHOPPED_LETTUCE = new Food(new Food.FoodBuilder("Chopped Lettuce",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFormable());

    public static Food TOMATO = new Food(new Food.FoodBuilder("Tomato",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setChoppable());

    public static Food CHOPPED_TOMATO = new Food(new Food.FoodBuilder("Chopped Tomato",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFormable());

    public static Food ONION = new Food(new Food.FoodBuilder("Onion",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setChoppable());

    public static Food CHOPPED_ONION = new Food(new Food.FoodBuilder("Chopped Onion",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFormable());

    public static Food BURGER = new Food(new Food.FoodBuilder("Burger",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(25));

    //renamed to beef mince - so can be chopped into burgers
    //if set to formable instead, then it could've been made into burger before cooked
    // could be worked around
    public static Food BEEF_MINCE = new Food(new Food.FoodBuilder("Beef Mince",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setChoppable());

    public static Food RAW_PATTY = new Food(new Food.FoodBuilder("Raw Patty",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFryable());

    public static Food COOKED_PATTY = new Food(new Food.FoodBuilder("Cooked Patty",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFormable());

    public static Food BUN = new Food(new Food.FoodBuilder("Bun",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setToastable().setFormable());

    public static Food TOASTED_BUN = new Food(new Food.FoodBuilder("Toasted Bun",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFormable());

    public static Food CHEESE = new Food(new Food.FoodBuilder("Cheese",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setChoppable());

    public static Food SLICED_CHEESE = new Food(new Food.FoodBuilder("Sliced Cheese",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFormable());

    public static Food JACKET_POTATO = new Food(new Food.FoodBuilder("Jacket Potato",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(10));

    public static Food RAW_POTATO = new Food(new Food.FoodBuilder("Raw Potato",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setBakeable().setChoppable());

    public static Food PIZZA = new Food(new Food.FoodBuilder("Pizza",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setReward(50));

    public static Food WATER_BUCKET = new Food(new Food.FoodBuilder("Water Bucket",
            new Texture(Gdx.files.internal("badlogic.jpg"))).setFormable());

    //We need tomatoes, dough, flour

}
