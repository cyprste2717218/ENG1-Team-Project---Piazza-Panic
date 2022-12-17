package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IPathfinder;
import com.mygdx.game.threads.PathfindingRunnable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.mygdx.game.Facing;

public class Chef implements IPathfinder {
    private static final int CHEF_SIZE = 256;
    private Sprite chefSprite;
    private Stack<Food> foodStack;
    private int squareSize = 32;
    private static final int COLLISION_BUFFER = 2;
    private List<Vector2> worldPath = new ArrayList<>();
    long mouseClickTime = 0;
    final float speed = 100;

    private int pathfindingCounter = 0;
    @Override
    public void setPathCounter(int counter) {
        pathfindingCounter = counter;
    }

    @Override
    public int getPathCounter() {
        return pathfindingCounter;
    }

    private Facing facing = Facing.UP;

    @Override
    public void setFacing(Facing direction) {
        facing = direction;
        chefSprite.setRotation(90f * getFacing().ordinal());
    }

    @Override
    public Facing getFacing() {
        return facing;
    }


    public Chef(Texture chefTexture){
        chefSprite = new Sprite(chefTexture, CHEF_SIZE, CHEF_SIZE);
        this.chefSprite.setScale(0.125f);
        foodStack = new Stack<>();
    }

    public Sprite getChefSprite(){
        return chefSprite;
    }


    public void move(TiledMap tiledMap, Node[][] walls, boolean isMouseMovement, Camera camera){

        if(!isMouseMovement) keyBoardMovement(tiledMap, walls);
        else{
            mouseMovement(tiledMap, walls, camera);
            if(worldPath != null){
                PathfindingUtils.followPath(chefSprite, worldPath, speed, this);
                //PathfindingUtils.drawPath(worldPath, camera);
            }
        }
    }

    private void mouseMovement(TiledMap tiledMap, Node[][] walls, Camera camera){
        //Get position of mouse in world on click
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(TimeUtils.millis() - 250 < mouseClickTime) return;

            mouseClickTime = TimeUtils.millis();
            float endWorldX = Gdx.input.getX();
            float endWorldY = Gdx.input.getY();
            Vector3 unprojectedCoord = camera.unproject(new Vector3(endWorldX, endWorldY, 0));
            endWorldX = unprojectedCoord.x - 120;
            endWorldY = unprojectedCoord.y - 120;

            System.out.println("End Coord: (" + endWorldX + "," + endWorldY + ")");

            float startWorldX = chefSprite.getX();
            float startWorldY = chefSprite.getY();

            //Convert world co-ords to grid co-ords
            int endGridX = TileMapUtils.positionToCoord(endWorldX, tiledMap);
            int endGridY = TileMapUtils.positionToCoord(endWorldY, tiledMap);
            Node end = walls[endGridX][endGridY];

            int startGridX = TileMapUtils.positionToCoord(startWorldX, tiledMap);
            int startGridY = TileMapUtils.positionToCoord(startWorldY, tiledMap);
            Node start = walls[startGridX][startGridY];

            //Pathfind between chef and destination co-ords
            PathfindingRunnable pathfindingObj = new PathfindingRunnable(start, end, walls);
            Thread pathfindingThread = new Thread(pathfindingObj);
            pathfindingThread.start();
            while (pathfindingThread.isAlive()){
                System.out.println("Finding path");
            }
            Vector2[] gridPath = pathfindingObj.gridPath;

            if(gridPath == null) return;
            //Convert grid path to world path
            worldPath = PathfindingUtils.convertGridPathToWorld(gridPath, tiledMap);
            pathfindingCounter = 0;
        }
    }

    private void keyBoardMovement(TiledMap tiledMap, Node[][] walls){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        int tileWidth = layer.getTileWidth();

        if(Gdx.input.isKeyPressed(Input.Keys.W) && !hasCollisionUp(tileWidth, walls, tiledMap)){
            chefSprite.translateY(speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.UP);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && !hasCollisionDown(tileWidth, walls, tiledMap) && TileMapUtils.positionToCoord(chefSprite.getY() + squareSize - 3, tiledMap) > 0){
            chefSprite.translateY(-speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.DOWN);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A) && !hasCollisionLeft(tileWidth, walls, tiledMap)){
            chefSprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.LEFT);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && !hasCollisionRight(tileWidth, walls, tiledMap)) {
            chefSprite.translateX(speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.RIGHT);
        }
    }

    private boolean hasCollisionUp(int tileWidth, Node[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() + step, chefSprite.getY() + squareSize + COLLISION_BUFFER, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionDown(int tileWidth, Node[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() + step, chefSprite.getY() - COLLISION_BUFFER, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionLeft(int tileWidth, Node[][] walls, TiledMap tiledMap){
        boolean collides = false;
        for (float step = 0; step <= squareSize; step += (double)tileWidth/4){
            collides = TileMapUtils.getWallAtSprite(chefSprite.getX() - COLLISION_BUFFER, chefSprite.getY() + step, tiledMap, walls);
            if(collides) return collides;
        }
        return collides;
    }
    private boolean hasCollisionRight(int tileWidth, Node[][] walls, TiledMap tiledMap){
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
