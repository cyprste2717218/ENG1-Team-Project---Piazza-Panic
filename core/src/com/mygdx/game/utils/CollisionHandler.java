package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Node;
import com.mygdx.game.enums.Facing;

public class CollisionHandler {

    //This class handles collisions of moving objects
    private final int tileWidth;
    private final Node[][] grid;
    private final TiledMap tiledMap;
    private final Sprite sprite;
    private final int squareSize;
    private final float speed;
    //private final Rectangle spriteRectangle;

    public CollisionHandler(int tileWidth, Node[][] grid, TiledMap tiledMap, Sprite sprite, int squareSize, float speed){
        this.tileWidth = tileWidth;
        this.grid = grid;
        this.tiledMap = tiledMap;
        this.sprite = sprite;
        this.squareSize = squareSize;
        this.speed = speed;
        //spriteRectangle = new Rectangle(0,0,sprite.getWidth(), sprite.getHeight());
    }


    public boolean hasCollision(){
        return hasCollisionVertical() || hasCollisionHorizontal();
    }

    public boolean hasCollisionVertical(){
        for(int step = -squareSize/2; step <= squareSize/2; step++){
            int playerX = TileMapUtils.positionToCoord(sprite.getX() + step, tiledMap);
            int playerY = TileMapUtils.positionToCoord(sprite.getY(), tiledMap);

            if(grid[playerX][playerY].isCollidable()) return true;
        }
        return false;
    }

    public boolean hasCollisionHorizontal(){
        for(int step = -squareSize/2; step <= squareSize/2; step++){
            int playerX = TileMapUtils.positionToCoord(sprite.getX(), tiledMap);
            int playerY = TileMapUtils.positionToCoord(sprite.getY() + step, tiledMap);

            if(grid[playerX][playerY].isCollidable()) return true;
        }
        return false;
    }

    //We need a new function that checks every part of the grid that the player is in
}
