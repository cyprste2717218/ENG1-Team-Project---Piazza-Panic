package com.mygdx.game.stations;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Node;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Stations {
    static Texture choppingBoardTexture = new Texture("stationSprite.png");
    static Texture fryerTexture = new Texture("stationSprite.png");
    static Texture prepAreaTexture = new Texture("stationSprite.png");
    static Texture lettuceTexture = new Texture("stationSprite.png");
    static Texture tomatoTexture = new Texture("stationSprite.png");
    static Texture onionTexture = new Texture("stationSprite.png");
    static Texture beefMinceTexture = new Texture("stationSprite.png");
    static Texture burgerBunTexture = new Texture("stationSprite.png");
    static Texture cheeseTexture = new Texture("stationSprite.png");


    public static CuttingStation CHOPPING_BOARD = new CuttingStation(500, true);
    public static CookingStation FRYER = new CuttingStation(500, true);
    public static FormingStation PREP_AREA = new FormingStation(500, true);
    public static IngredientStation LETTUCE_STATION = new IngredientStation(FoodItems.LETTUCE);
 /*
    public static IngredientStation TOMATO_STATION = new IngredientStation(FoodItems.TOMATO);
    public static IngredientStation ONION_STATION = new IngredientStation(FoodItems.LETTUCE);
    public static IngredientStation BEEF_MINCE_STATION = new IngredientStation(FoodItems.BEEF_MINCE);
    public static IngredientStation BURGER_BUN_STATION = new IngredientStation(FoodItems.BUN);
    public static IngredientStation CHEESE_STATION = new IngredientStation(FoodItems.CHEESE);

     */


    //creates the lookup tables for each station
    private static void setUpStations(){
        // finished food ingredients
        List<Food> burgerIngredients = Arrays.asList(FoodItems.TOASTED_BUN, FoodItems.COOKED_PATTY);
        List<Food> saladIngredients = Arrays.asList(FoodItems.CHOPPED_LETTUCE, FoodItems.CHOPPED_TOMATO, FoodItems.CHOPPED_ONION);

        CHOPPING_BOARD.operationLookupTable.put(FoodItems.LETTUCE, FoodItems.CHOPPED_LETTUCE);
        CHOPPING_BOARD.operationLookupTable.put(FoodItems.ONION, FoodItems.CHOPPED_ONION);
        CHOPPING_BOARD.operationLookupTable.put(FoodItems.TOMATO, FoodItems.CHOPPED_TOMATO);
        CHOPPING_BOARD.operationLookupTable.put(FoodItems.CHEESE, FoodItems.SLICED_CHEESE);
        CHOPPING_BOARD.operationLookupTable.put(FoodItems.BEEF_MINCE, FoodItems.RAW_PATTY);

        FRYER.operationLookupTable.put(FoodItems.BUN, FoodItems.TOASTED_BUN);
        FRYER.operationLookupTable.put(FoodItems.RAW_PATTY, FoodItems.COOKED_PATTY);

        PREP_AREA.operationLookupTable_Forming.put(burgerIngredients, FoodItems.BURGER);
        PREP_AREA.operationLookupTable_Forming.put(saladIngredients, FoodItems.SALAD);

    }

    // function to apply texture to station, then position it onto the tile map
    public static void createAllStations(Node[][] walls, TiledMap tiledMap){
        setUpStations();

        CHOPPING_BOARD.stationTexture = choppingBoardTexture;
        CHOPPING_BOARD.setTileMapPosition(CHOPPING_BOARD.stationTexture, 2, 14, walls, tiledMap);


        FRYER.stationTexture = fryerTexture;
        FRYER.setTileMapPosition(FRYER.stationTexture, 6, 14, walls, tiledMap);

        PREP_AREA.stationTexture = prepAreaTexture;
        PREP_AREA.setTileMapPosition(PREP_AREA.stationTexture, 4,14, walls, tiledMap);

        //Ingredient Stations - to be placed in the pantry

        LETTUCE_STATION.stationTexture = lettuceTexture;
        LETTUCE_STATION.setTileMapPosition(LETTUCE_STATION.stationTexture, 8, 14, walls, tiledMap);
/*
        TOMATO_STATION.stationTexture = tomatoTexture;
        TOMATO_STATION.setTileMapPosition(TOMATO_STATION.stationTexture, 300, 23);

        ONION_STATION.stationTexture = onionTexture;
        ONION_STATION.setTileMapPosition(ONION_STATION.stationTexture, 300, 23);

        BEEF_MINCE_STATION.stationTexture = beefMinceTexture;
        BEEF_MINCE_STATION.setTileMapPosition(BEEF_MINCE_STATION.stationTexture, 300, 23);

        BURGER_BUN_STATION.stationTexture = burgerBunTexture;
        BURGER_BUN_STATION.setTileMapPosition(BURGER_BUN_STATION.stationTexture, 300, 23);

        CHEESE_STATION.stationTexture = cheeseTexture;
        CHEESE_STATION.setTileMapPosition(CHEESE_STATION.stationTexture, 300, 23);
        */


        System.out.print("Stations Created\n");


    }
    public static void renderAllStations(SpriteBatch batch)  {
        CHOPPING_BOARD.getSprite().draw(batch);
        FRYER.getSprite().draw(batch);
        LETTUCE_STATION.getSprite().draw(batch);
        PREP_AREA.getSprite().draw(batch);

        /*
        TOMATO_STATION.stationSprite.draw(batch);
        ONION_STATION.stationSprite.draw(batch);
        BEEF_MINCE_STATION.stationSprite.draw(batch);
        BURGER_BUN_STATION.stationSprite.draw(batch);
        CHEESE_STATION.stationSprite.draw(batch);

         */


    }

}
