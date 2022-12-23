package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Chef;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;

import java.util.Stack;

public class Station implements IInteractable {
    private static final int STATION_SIZE = 256;
    private static final int COLLISION_BUFFER = 6;
    public Stack<Food> inventory;
    public Sprite stationSprite;
    public Texture stationTexture;

    public Station(Stack<Food> inventory){
        this.inventory = inventory;
    }
    // sets coords for the sprite on the tile map
    public void setTileMapPosition(Texture stationTexture, int mapPosX, int mapPosY)    {
        this.stationSprite = new Sprite(stationTexture, STATION_SIZE, STATION_SIZE);
        this.stationSprite.setScale(0.125f);
        this.stationSprite.setPosition(mapPosX - COLLISION_BUFFER,mapPosY - COLLISION_BUFFER);
    }
    @Override
    public void onInteract(Chef chef) {

    }
}
