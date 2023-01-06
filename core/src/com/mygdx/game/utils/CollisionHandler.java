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

    public CollisionHandler(int tileWidth, Node[][] grid, TiledMap tiledMap, Sprite sprite, int squareSize, float speed){
        this.tileWidth = tileWidth;
        this.grid = grid;
        this.tiledMap = tiledMap;
        this.sprite = sprite;
        this.squareSize = squareSize;
        this.speed = speed;
    }

    public boolean hasCollision(){
        return hasCollisionUp() || hasCollisionDown() || hasCollisionRight() || hasCollisionLeft();
    }

    //These methods check for a collision on a specific side of the sprite
    //They do this by checking where they would be next frame at every 4 pixels along the side

    //Cant go left or right with something above
    public boolean hasCollisionUp(){

        boolean collides = false;
        for (int i = -16; i < squareSize; i += 4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + i, sprite.getY()  + (squareSize/2f + (speed * Gdx.graphics.getDeltaTime())), tiledMap, grid);
            if(collides) return collides;
        }
        return collides;
    }
    public boolean hasCollisionDown(){
        boolean collides = false;
        for (int i = -16; i < squareSize; i += 4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + i, sprite.getY() - (squareSize/2f + (speed * Gdx.graphics.getDeltaTime())), tiledMap, grid);
            if(collides) return collides;
        }
        return collides;
    }
    public boolean hasCollisionLeft(){
        boolean collides = false;
        for (int i = -16; i < squareSize; i += 4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() - (squareSize/2f + (speed * Gdx.graphics.getDeltaTime())), sprite.getY() + i, tiledMap, grid);
            if(collides) return collides;
        }
        return collides;
    }
    //Cant go up and down with something on the right
    public boolean hasCollisionRight(){
        boolean collides = false;
        for (int i = -16; i < squareSize; i += 4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + (speed * Gdx.graphics.getDeltaTime()), sprite.getY() + i, tiledMap, grid);
            if(collides) return collides;
        }
        return collides;
    }

    //We need a new function that checks every part of the grid that the player is in
}
