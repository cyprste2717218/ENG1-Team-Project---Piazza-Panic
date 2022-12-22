package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.interfaces.IPathfinder;
import com.mygdx.game.threads.PathfindingRunnable;
import com.mygdx.game.utils.CollisionHandler;
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


    public void move(TiledMap tiledMap, Node[][] walls, Camera camera){

        if(!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) keyBoardMovement(tiledMap, walls);
        else mouseMovement(tiledMap, walls, camera);

        if(!worldPath.isEmpty()){
            PathfindingUtils.drawPath(worldPath,camera, chefSprite, this);
            PathfindingUtils.followPath(chefSprite, worldPath, speed, this);
        }

    }

    private void mouseMovement(TiledMap tiledMap, Node[][] walls, Camera camera){

        //Adds a delay between how often you can pathfind
        if(TimeUtils.millis() - 250 < mouseClickTime) return;
        mouseClickTime = TimeUtils.millis();

        //Convert world co-ords to grid co-ords
        Node start = setStartCoords(tiledMap, walls);
        Node end = setEndCoords(tiledMap, walls, camera);

        //Set up thread to do pathfinding
        PathfindingRunnable pathfindingObj = new PathfindingRunnable(start, end, walls);
        Thread pathfindingThread = new Thread(pathfindingObj);
        pathfindingThread.start();
        //Stalls the main thread until the pathfinding is complete
        while (pathfindingThread.isAlive()){}
        //Gets the path from the thread in grid co-ordinates
        Vector2[] gridPath = pathfindingObj.getGridPath();

        if(gridPath.length == 0) return;
        //Convert grid co-ordinates to world co-ordinates
        worldPath = PathfindingUtils.convertGridPathToWorld(gridPath, tiledMap);
        pathfindingCounter = 0;
    }

    private void keyBoardMovement(TiledMap tiledMap, Node[][] walls){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        int collisionBuffer = 2;
        CollisionHandler collisionHandler = new CollisionHandler(tileWidth, walls, tiledMap, chefSprite, squareSize, collisionBuffer);

        if(Gdx.input.isKeyPressed(Input.Keys.W) && !collisionHandler.hasCollisionUp()){
            chefSprite.translateY(speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.UP);
            worldPath.clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && !collisionHandler.hasCollisionDown() && TileMapUtils.positionToCoord(chefSprite.getY() + squareSize - 3, tiledMap) > 0){
            chefSprite.translateY(-speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.DOWN);
            worldPath.clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A) && !collisionHandler.hasCollisionLeft()){
            chefSprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.LEFT);
            worldPath.clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && !collisionHandler.hasCollisionRight()) {
            chefSprite.translateX(speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.RIGHT);
            worldPath.clear();
        }


        //  for testing purposes, pressing o will remove a customer from the list of active customers.
        //  depending on whom the chef is interacting with, this will remove the corresponding customer from the list
        else if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            // if(chef interaction is with customer
            PiazzaPanic.customers.removeIndex(0);   //  to be changed to remove correct customer from list
            // else {interact with station}
        }
    }

    private Node setStartCoords(TiledMap tiledMap, Node[][] walls){
        int startGridX = TileMapUtils.positionToCoord(chefSprite.getX(), tiledMap);
        int startGridY = TileMapUtils.positionToCoord(chefSprite.getY(), tiledMap);
        return walls[startGridX][startGridY];
    }

    private Node setEndCoords(TiledMap tiledMap, Node[][] walls, Camera camera){
        Vector3 unprojectedCoord = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        float endWorldX = unprojectedCoord.x - 128;
        float endWorldY = unprojectedCoord.y - 128;
        int endGridX = TileMapUtils.positionToCoord(endWorldX, tiledMap);
        int endGridY = TileMapUtils.positionToCoord(endWorldY, tiledMap);

        return walls[endGridX][endGridY];
    }



    public void interact(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            Rectangle collisionDetector = new Rectangle(chefSprite.getX(),chefSprite.getY(), squareSize, squareSize);
        }
        //Get facing direction
        //Get tile in front of Chef
        //Check for what is in that tile
        //Perform action
    }
}
