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

    public static List<ServingStation> servingStations = new ArrayList<>();
    public static CuttingStation CHOPPING_BOARD = new CuttingStation(10, true, new Texture("FoodStations/choppingBoard.png"));
    public static FryingStation FRYER = new FryingStation(10, true, new Texture("FoodStations/fryer.png"));
    public static FormingStation PREP_AREA = new FormingStation(10, true, new Texture("FoodStations/prepArea.png"));
    public static IngredientStation LETTUCE_STATION = new IngredientStation(FoodItems.LETTUCE, new Texture("FoodStations/lettuceStation.png"));
    public static IngredientStation TOMATO_STATION = new IngredientStation(FoodItems.TOMATO, new Texture("FoodStations/TomatoStation.png"));
    public static IngredientStation ONION_STATION = new IngredientStation(FoodItems.ONION, new Texture("FoodStations/OnionStation.png"));
    public static IngredientStation BEEF_MINCE_STATION = new IngredientStation(FoodItems.BEEF_MINCE, new Texture("FoodStations/beefMinceStation.png"));
    public static IngredientStation BURGER_BUN_STATION = new IngredientStation(FoodItems.BUN, new Texture("FoodStations/bunsStation.png"));
    public static IngredientStation CHEESE_STATION = new IngredientStation(FoodItems.CHEESE, new Texture("stationSprite.png"));
    public static IngredientStation WATER_BUCKET_STATION = new IngredientStation(FoodItems.WATER_BUCKET, new Texture("FoodItems/waterBucket.png"));
    public static ServingStation SERVING_STATION_1 = new ServingStation(new Texture("FoodStations/serving_station.png"));
    public static ServingStation SERVING_STATION_2 = new ServingStation(new Texture("FoodStations/serving_station.png"));
    public static ServingStation SERVING_STATION_3 = new ServingStation(new Texture("FoodStations/serving_station.png"));
    public static ServingStation SERVING_STATION_4 = new ServingStation(new Texture("FoodStations/serving_station.png"));
    public static ServingStation SERVING_STATION_5 = new ServingStation(new Texture("FoodStations/serving_station.png"));
    private static void createAllServingStations(Node[][] grid, TiledMap tiledMap){
        SERVING_STATION_1.setTileMapPosition(12, 4, grid, tiledMap);
        SERVING_STATION_2.setTileMapPosition(12, 2, grid, tiledMap);
        SERVING_STATION_3.setTileMapPosition(3, 4, grid, tiledMap);
        SERVING_STATION_4.setTileMapPosition(3, 2, grid, tiledMap);
        SERVING_STATION_5.setTileMapPosition(6, 2, grid, tiledMap);
    }

    public static void clearServingStations(){
        SERVING_STATION_1.clearStation();
        SERVING_STATION_2.clearStation();
        SERVING_STATION_3.clearStation();
        SERVING_STATION_4.clearStation();
        SERVING_STATION_5.clearStation();
    }


    // function to apply texture to station, then position it onto the tile map
    public static void createAllStations(Node[][] grid, TiledMap tiledMap){
        CHOPPING_BOARD.setTileMapPosition(2, 14, grid, tiledMap);
        FRYER.setTileMapPosition(4, 14, grid, tiledMap);
        PREP_AREA.setTileMapPosition(6,14, grid, tiledMap);

        //Ingredient Stations - to be placed in the pantry
        LETTUCE_STATION.setTileMapPosition(12, 6, grid, tiledMap);
        TOMATO_STATION.setTileMapPosition(14, 14, grid, tiledMap);
        ONION_STATION.setTileMapPosition(14, 12, grid, tiledMap);
        BEEF_MINCE_STATION.setTileMapPosition(14, 10, grid, tiledMap);
        BURGER_BUN_STATION.setTileMapPosition(14, 8, grid, tiledMap);
        CHEESE_STATION.setTileMapPosition(14, 6, grid, tiledMap);
        WATER_BUCKET_STATION.setTileMapPosition(8, 14, grid, tiledMap);

        createAllServingStations(grid, tiledMap);

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
        //CHEESE_STATION.getSprite().draw(batch);
        WATER_BUCKET_STATION.getSprite().draw(batch);

        for(ServingStation servingStation: servingStations){
            servingStation.getSprite().draw(batch);
            if(servingStation.getOrderSprite() != null){
                batch.draw(servingStation.getOrderSprite().getTexture(), servingStation.getOrderSprite().getX() + 96, servingStation.getOrderSprite().getY() + 96);
                servingStation.getOrderSprite().draw(batch);
            }
        }
    }

}
