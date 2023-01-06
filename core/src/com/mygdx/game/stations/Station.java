package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.Stack;

public class Station implements IInteractable {
    private static final int STATION_SIZE = 256;
    private static final int COLLISION_BUFFER = 6;
    public Stack<Food> inventory;
    private Sprite stationSprite;
    public Texture stationTexture;

    public Station(Stack<Food> inventory){
        this.inventory = inventory;
    }

    // sets coords for the sprite on the tile map
    public void setTileMapPosition(Texture stationTexture, int mapPosX, int mapPosY, Node[][] walls, TiledMap tiledMap)    {
        this.stationSprite = new Sprite(stationTexture, STATION_SIZE, STATION_SIZE);
        this.stationSprite.setScale(0.125f);
        if(!PathfindingUtils.isValidNode(mapPosX, mapPosY, walls)) return;
        walls[mapPosX][mapPosY].setNodeType(NodeType.STATION);
        walls[mapPosX][mapPosY].setInteractable(this);
        stationSprite.setPosition(TileMapUtils.coordToPosition(mapPosX, tiledMap), TileMapUtils.coordToPosition(mapPosY, tiledMap));
    }
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid) {
        System.out.println("Interacting with a station");
    }

    @Override
    public Sprite getSprite() {
        return stationSprite;
    }

    @Override
    public Vector2 getPreviousGridPosition() {
        return null;
    }

    @Override
    public void setCurrentGridPosition(Vector2 gridPos) {

    }
}
