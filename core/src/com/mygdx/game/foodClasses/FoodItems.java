package com.mygdx.game.foodClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodItems {
    //A list of finished foods, e.g. Burger, which has any Food with a reward automatically added to it
    public static List<Food> finishedFoods = new ArrayList<>();

    public static Food SALAD = new Food("Salad", new Texture("badlogic.jpg"),false, 50);
    public static Food LETTUCE = new Food("Lettuce", new Texture("badlogic.jpg"),false, 0);
    public static Food CHOPPED_LETTUCE = new Food("Chopped Lettuce", new Texture("badlogic.jpg"), true, 0);
    public static Food TOMATO = new Food("Tomato", new Texture("badlogic.jpg"), false, 0);
    public static Food CHOPPED_TOMATO = new Food("Chopped Tomato", new Texture("badlogic.jpg"), true, 0);
    public static Food ONION = new Food("Onion", new Texture("badlogic.jpg"), false, 0);
    public static Food CHOPPED_ONION = new Food("Chopped Onion", new Texture("badlogic.jpg"), true, 0);

    public static Food BURGER = new Food("Burger", new Texture("badlogic.jpg"), false, 25);
    public static Food BEEF_MINCE = new Food("Beef Mince", new Texture("badlogic.jpg"), false, 0);
    public static Food RAW_PATTY = new Food("Raw Patty", new Texture("badlogic.jpg"), false, 0);
    public static Food COOKED_PATTY = new Food("Cooked Patty", new Texture("badlogic.jpg"), true, 0);
    public static Food BUN = new Food("Bun", new Texture("badlogic.jpg"), true, 0);
    public static Food TOASTED_BUN = new Food("Toasted Bun", new Texture("badlogic.jpg"), true, 0);
    public static Food CHEESE = new Food("Cheese", new Texture("badlogic.jpg"), false, 0);
    public static Food SLICED_CHEESE = new Food("Sliced Cheese", new Texture("badlogic.jpg"), true, 0);

    //A foodItem used to clear the Forming station
    public static Food WATER_BUCKET = new Food("Water Bucket", new Texture("badlogic.jpg"), true, 0);


    //Not creatable yet
    public static Food JACKET_POTATO = new Food("Jacket Potato", new Texture("badlogic.jpg"), false, 10);
    public static Food RAW_POTATO = new Food("Raw Potato", new Texture("badlogic.jpg"), false, 0);
    public static Food PIZZA = new Food("Pizza", new Texture("badlogic.jpg"), false, 50);

}
