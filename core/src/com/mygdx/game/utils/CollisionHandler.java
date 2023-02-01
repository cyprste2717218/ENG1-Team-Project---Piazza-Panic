package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Node;
import com.mygdx.game.enums.Facing;

/**
 * The type Collision handler - manages the collision detection of the Chef class.
 */
public class CollisionHandler {

    //This class handles collisions of moving objects
    private final Node[][] grid;
    private final TiledMap tiledMap;
    private final Sprite sprite;
    private final int squareSize;

    /**
     * Instantiates a new Collision handler.
     *
     * @param grid       the grid
     * @param tiledMap   the tiled map
     * @param sprite     the sprite
     * @param squareSize the bounds of the are you want to detect the collisions of
     */
    public CollisionHandler(Node[][] grid, TiledMap tiledMap, Sprite sprite, int squareSize){
        this.grid = grid;
        this.tiledMap = tiledMap;
        this.sprite = sprite;
        this.squareSize = squareSize;
    }


    /**
     * Determines if the chef is colliding either vertically or horizontally
     *
     * @return the boolean regarding whether the above is true
     */
    public boolean hasCollision(){
        return hasCollisionVertical() || hasCollisionHorizontal();
    }

    /**
     * Vertical collision detection
     *
     * @return the boolean of whether the sprite collides above or below itself
     */
    public boolean hasCollisionVertical(){
        for(int step = -squareSize/2; step <= squareSize/2; step++){
            int playerX = TileMapUtils.positionToCoord(sprite.getX() + step, tiledMap);
            int playerY = TileMapUtils.positionToCoord(sprite.getY(), tiledMap);

            if(grid[playerX][playerY].isCollidable()) return true;
        }
        return false;
    }

    /**
     * Horizontal collision detection
     *
     * @return the boolean of whether the sprite collides to the left or the right
     */
    public boolean hasCollisionHorizontal(){
        for(int step = -squareSize/2; step <= squareSize/2; step++){
            int playerX = TileMapUtils.positionToCoord(sprite.getX(), tiledMap);
            int playerY = TileMapUtils.positionToCoord(sprite.getY() + step, tiledMap);

            if(grid[playerX][playerY].isCollidable()) return true;
        }
        return false;
    }
}
