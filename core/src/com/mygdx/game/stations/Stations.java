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
    public static CuttingStation CHOPPING_BOARD = new CuttingStation(500, true, new Texture("stationSprite.png"));
    public static FryingStation FRYER = new FryingStation(500, true, new Texture("stationSprite.png"));
    public static FormingStation PREP_AREA = new FormingStation(500, true, new Texture("stationSprite.png"));
    public static IngredientStation LETTUCE_STATION = new IngredientStation(FoodItems.LETTUCE, new Texture("stationSprite.png"));
    public static IngredientStation TOMATO_STATION = new IngredientStation(FoodItems.TOMATO, new Texture("stationSprite.png"));
    public static IngredientStation ONION_STATION = new IngredientStation(FoodItems.LETTUCE, new Texture("stationSprite.png"));
    public static IngredientStation BEEF_MINCE_STATION = new IngredientStation(FoodItems.BEEF_MINCE, new Texture("stationSprite.png"));
    public static IngredientStation BURGER_BUN_STATION = new IngredientStation(FoodItems.BUN, new Texture("stationSprite.png"));
    public static IngredientStation CHEESE_STATION = new IngredientStation(FoodItems.CHEESE, new Texture("stationSprite.png"));
    public static IngredientStation WATER_BUCKET_STATION = new IngredientStation(FoodItems.WATER_BUCKET, new Texture("stationSprite.png"));


    // function to apply texture to station, then position it onto the tile map
    public static void createAllStations(Node[][] walls, TiledMap tiledMap){
        CHOPPING_BOARD.setTileMapPosition(2, 14, walls, tiledMap);
        FRYER.setTileMapPosition(6, 14, walls, tiledMap);
        PREP_AREA.setTileMapPosition(4,14, walls, tiledMap);

        //Ingredient Stations - to be placed in the pantry
        LETTUCE_STATION.setTileMapPosition(12, 6, walls, tiledMap);
        TOMATO_STATION.setTileMapPosition(14, 14, walls, tiledMap);
        ONION_STATION.setTileMapPosition(14, 12, walls, tiledMap);
        BEEF_MINCE_STATION.setTileMapPosition(14, 10, walls, tiledMap);
        BURGER_BUN_STATION.setTileMapPosition(14, 8, walls, tiledMap);
        CHEESE_STATION.setTileMapPosition(14, 6, walls, tiledMap);
        WATER_BUCKET_STATION.setTileMapPosition(8, 14, walls, tiledMap);

        System.out.print("Stations Created\n");
    }
    public static void renderAllStations(SpriteBatch batch)  {
        CHOPPING_BOARD.getSprite().draw(batch);
        FRYER.getSprite().draw(batch);
        LETTUCE_STATION.getSprite().draw(batch);
        PREP_AREA.getSprite().draw(batch);

        TOMATO_STATION.getSprite().draw(batch);
        ONION_STATION.getSprite().draw(batch);
        BEEF_MINCE_STATION.getSprite().draw(batch);
        BURGER_BUN_STATION.getSprite().draw(batch);
        CHEESE_STATION.getSprite().draw(batch);
        WATER_BUCKET_STATION.getSprite().draw(batch);
    }

}
