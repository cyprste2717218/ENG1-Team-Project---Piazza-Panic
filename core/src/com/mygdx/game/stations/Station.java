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

/**
 * The type Station.
 */
public class Station implements IInteractable, IGridEntity {
    /**
     * The constant STATION_SIZE used for defining the size of the sprite.
     */
    protected static final int STATION_SIZE = 256;
    /**
     * The Food item stored within the station
     */
    protected Food stock;
    private Sprite stationSprite;

    /**
     * Instantiates a new Station.
     *
     * @param stock      the item stored within the station
     * @param stationTexture the station texture
     */
    public Station(Food stock, Texture stationTexture){
        this.stock = stock;
        this.stationSprite = new Sprite(stationTexture, STATION_SIZE, STATION_SIZE);
        this.stationSprite.setScale(0.125f);
    }

    /**
     * Sets tile map position.
     *
     * @param mapPosX  the map pos x
     * @param mapPosY  the map pos y
     * @param grid    the pathfinding grid
     * @param tiledMap the tiled map
     */
// sets coords for the sprite on the tile map
    public void setTileMapPosition(int mapPosX, int mapPosY, Node[][] grid, TiledMap tiledMap)    {
        if(!PathfindingUtils.isValidNode(mapPosX, mapPosY, grid)) return;
        grid[mapPosX][mapPosY].setNodeType(NodeType.STATION);
        grid[mapPosX][mapPosY].setGridEntity(this);
        grid[mapPosX][mapPosY].setInteractable(this);
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
