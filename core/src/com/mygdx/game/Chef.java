package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.foodClasses.Food;
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
    private static final int CHEF_SIZE = 256;
    private Sprite chefSprite;
    private Stack<Food> foodStack;
    private int squareSize = 32;
    private static final int COLLISION_BUFFER = 2;

    public Chef(Texture chefTexture){
        chefSprite = new Sprite(chefTexture, CHEF_SIZE, CHEF_SIZE);
        this.chefSprite.setScale(0.125f);
        foodStack = new Stack<>();
    }

    public Sprite getChefSprite(){
        return chefSprite;
    }
    public void move(TiledMap tiledMap, boolean[][] walls){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        final float speed = 100 * Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W) && !hasCollisionUp(tileWidth, walls, tiledMap)){
            chefSprite.translateY(speed);
            facing = Facing.UP;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && !hasCollisionDown(tileWidth, walls, tiledMap) && TileMapUtils.positionToCoord(chefSprite.getY() + squareSize - 3, tiledMap) > 0){
            chefSprite.translateY(-speed);
            facing = Facing.DOWN;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A) && !hasCollisionLeft(tileWidth, walls, tiledMap)){
            chefSprite.translateX(-speed);
            facing = Facing.LEFT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && !hasCollisionRight(tileWidth, walls, tiledMap)) {
            chefSprite.translateX(speed);
            facing = Facing.RIGHT;
        }
        chefSprite.setRotation(90f * facing.ordinal());
    }

    private boolean hasCollisionUp(int tileWidth, boolean[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() + step, chefSprite.getY() + squareSize + COLLISION_BUFFER, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionDown(int tileWidth, boolean[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() + step, chefSprite.getY() - COLLISION_BUFFER, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionLeft(int tileWidth, boolean[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() - COLLISION_BUFFER, chefSprite.getY() + step, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionRight(int tileWidth, boolean[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() + squareSize + COLLISION_BUFFER, chefSprite.getY() + step, tiledMap, walls);
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
