package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Node;

public class CollisionHandler {

    private int tileWidth;
    private Node[][] walls;
    private TiledMap tiledMap;
    private Sprite sprite;
    private int squareSize;
    private int collisionBuffer;

    public CollisionHandler(int tileWidth, Node[][] walls, TiledMap tiledMap, Sprite sprite, int squareSize, int collisionBuffer){
        this.tileWidth = tileWidth;
        this.walls = walls;
        this.tiledMap = tiledMap;
        this.sprite = sprite;
        this.squareSize = squareSize;
        this.collisionBuffer = collisionBuffer;
    }

    public boolean hasCollisionUp(){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + step, sprite.getY() + squareSize + collisionBuffer, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    public boolean hasCollisionDown(){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + step, sprite.getY() - collisionBuffer, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    public boolean hasCollisionLeft(){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() - collisionBuffer, sprite.getY() + step, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    public boolean hasCollisionRight(){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getCollisionAtSprite(sprite.getX() + squareSize + collisionBuffer, sprite.getY() + step, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
}
