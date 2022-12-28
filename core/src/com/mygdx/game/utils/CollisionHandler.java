package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.Node;

public class CollisionHandler {

    //This class handles collisions of moving objects
    private final int tileWidth;
    private final Node[][] grid;
    private final TiledMap tiledMap;
    private final Sprite sprite;
    private final int squareSize;
    private final int collisionBuffer;

    public CollisionHandler(int tileWidth, Node[][] grid, TiledMap tiledMap, Sprite sprite, int squareSize, int collisionBuffer){
        this.tileWidth = tileWidth;
        this.grid = grid;
        this.tiledMap = tiledMap;
        this.sprite = sprite;
        this.squareSize = squareSize;
        this.collisionBuffer = collisionBuffer;
    }

    //These methods check for a collision on a specific side of the sprite
    public boolean hasCollisionUp(){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + step, sprite.getY() + squareSize + collisionBuffer, tiledMap, grid);
            if(collides) return collides;
        }
        return collides;
    }
    public boolean hasCollisionDown(){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + step, sprite.getY() - collisionBuffer, tiledMap, grid);
            if(collides) return collides;
        }
        return collides;
    }
    public boolean hasCollisionLeft(){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() - collisionBuffer, sprite.getY() + step, tiledMap, grid);
            if(collides) return collides;
        }
        return collides;
    }
    public boolean hasCollisionRight(){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + squareSize + collisionBuffer, sprite.getY() + step, tiledMap, grid);
            if(collides) return collides;
        }
        return collides;
    }
}
