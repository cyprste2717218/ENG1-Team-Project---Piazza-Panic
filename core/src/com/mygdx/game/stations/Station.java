package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.TileMapUtils;

public class Station implements IInteractable, IGridEntity {
    protected static final int STATION_SIZE = 256;
    public Food stock;
    private Sprite stationSprite;

    public Station(Food inventory, Texture stationTexture){
        this.stock = inventory;
        this.stationSprite = new Sprite(stationTexture, STATION_SIZE, STATION_SIZE);
        this.stationSprite.setScale(0.125f);
    }
    public Station() {}

    // sets coords for the sprite on the tile map
    public void setTileMapPosition(int mapPosX, int mapPosY, Node[][] walls, TiledMap tiledMap)    {
        if(!PathfindingUtils.isValidNode(mapPosX, mapPosY, walls)) return;
        walls[mapPosX][mapPosY].setNodeType(NodeType.STATION);
        walls[mapPosX][mapPosY].setGridEntity(this);
        walls[mapPosX][mapPosY].setInteractable(this);
        stationSprite.setPosition(TileMapUtils.coordToPosition(mapPosX, tiledMap), TileMapUtils.coordToPosition(mapPosY, tiledMap));
    }
    
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
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
