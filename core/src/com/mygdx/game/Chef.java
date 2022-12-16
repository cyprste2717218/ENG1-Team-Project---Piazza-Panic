package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.stations.Station;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.utils.TileMapUtils;

import java.util.Stack;

public class Chef{

    public enum Facing{
        UP,
        LEFT,
        DOWN,
        RIGHT;
    }
    private Facing facing = Facing.UP;
    private final int chefSize = 256;
    public Sprite chefSprite;
    private Stack<Food> foodStack;

    private int squareSize = 32;

    public Chef(Texture chefTexture){
        chefSprite = new Sprite(chefTexture, chefSize, chefSize);
        this.chefSprite.setScale(0.125f);
        foodStack = new Stack<>();
    }

    //TODO: Fix player getting stuck on walls
    public void move(TiledMap tiledMap, boolean[][] walls){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        final float speed = 100 * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W) && !hasCollisionUp(tileWidth, walls, tiledMap)){
            chefSprite.translateY(speed);
            facing = facing.UP;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && !hasCollisionDown(tileWidth, walls, tiledMap) && TileMapUtils.positionToCoord(chefSprite.getY(), tiledMap) > 0){
            chefSprite.translateY(-speed);
            facing = facing.DOWN;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A) && !hasCollisionLeft(tileWidth, walls, tiledMap)){
            chefSprite.translateX(-speed);
            facing = facing.LEFT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && !hasCollisionRight(tileWidth, walls, tiledMap)) {
            chefSprite.translateX(speed);
            facing = facing.RIGHT;
        }
        chefSprite.setRotation(90 * facing.ordinal());
    }

    //SLIGHTLY LESS BAD COLLISION DETECTION
    private boolean hasCollisionUp(int tileWidth, boolean[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += tileWidth/2){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() + step, chefSprite.getY() + squareSize, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionDown(int tileWidth, boolean[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += tileWidth/2){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() + step, chefSprite.getY(), tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionLeft(int tileWidth, boolean[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += tileWidth/2){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX(), chefSprite.getY() + step, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionRight(int tileWidth, boolean[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += tileWidth/2){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() + squareSize, chefSprite.getY() + step, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }






    public void interact(){
        //Get facing direction
        //Get tile in front of Chef
        //Check for what is in that tile
        //Perform action
    }

}
