package com.mygdx.game.foodClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Food items.
 */
public class FoodItems {
    /**
     * //A list of finished foods, e.g. Burger, which has any Food with a reward automatically added to it.
     */
    public static List<Food> finishedFoods = new ArrayList<>();

    public static Food SALAD = new Food("Salad", new Texture("Food_Assets/Salad.png"),false, 50);
    public static Food LETTUCE = new Food("Lettuce", new Texture("Food_Assets/Lettuce.png"),false, 0);
    public static Food CHOPPED_LETTUCE = new Food("Chopped Lettuce", new Texture("Food_Assets/ChoppedLettuce.png"), true, 0);
    public static Food TOMATO = new Food("Tomato", new Texture("Food_Assets/Tomato.png"), false, 0);
    public static Food CHOPPED_TOMATO = new Food("Chopped Tomato", new Texture("Food_Assets/ChoppedTomato.png"), true, 0);
    public static Food ONION = new Food("Onion", new Texture("Food_Assets/Onion.png"), false, 0);
    public static Food CHOPPED_ONION = new Food("Chopped Onion", new Texture("Food_Assets/ChoppedOnion.png"), true, 0);
    public static Food BURGER = new Food("Burger", new Texture("Food_Assets/Burger.png"), false, 25);
    public static Food BEEF_MINCE = new Food("Beef Mince", new Texture("Food_Assets/BeefMince.png"), false, 0);
    public static Food RAW_PATTY = new Food("Raw Patty", new Texture("Food_Assets/Patty.png"), false, 0);
    public static Food COOKED_PATTY = new Food("Cooked Patty", new Texture("Food_Assets/Cooked Patty.png"), true, 0);
    public static Food BUN = new Food("Bun", new Texture("Food_Assets/Buns.png"), true, 0);
    public static Food TOASTED_BUN = new Food("Toasted Bun", new Texture("Food_Assets/Toasted Bun.png"), true, 0);
    public static Food CHEESE = new Food("Cheese", new Texture("Food_Assets/Cheese.png"), false, 0);
    public static Food SLICED_CHEESE = new Food("Sliced Cheese", new Texture("Food_Assets/CheeseSlice.png"), true, 0);

    /**
     * The constant WATER_BUCKET - a special foodItem used to clear the forming station.
     */
    public static Food WATER_BUCKET = new Food("Water Bucket", new Texture("Food_Assets/water_bucket.png"), true, 0);


    //Not creatable yet
    //public static Food JACKET_POTATO = new Food("Jacket Potato", new Texture("badlogic.jpg"), false, 10);
    //public static Food RAW_POTATO = new Food("Raw Potato", new Texture("badlogic.jpg"), false, 0);
    //public static Food PIZZA = new Food("Pizza", new Texture("badlogic.jpg"), false, 50);

}
