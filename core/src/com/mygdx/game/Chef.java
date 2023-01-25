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
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.interfaces.IPathfinder;
import com.mygdx.game.threads.PathfindingRunnable;
import com.mygdx.game.utils.CollisionHandler;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.mygdx.game.enums.Facing;

public class Chef implements IPathfinder, IInteractable {
    private static final int CHEF_SIZE = 256;
    private final Sprite chefSprite;
    public Stack<Food> foodStack;
    private final int squareSize = 32;

    private List<Vector2> worldPath = new ArrayList<>();
    long mouseClickTime = 0;
    final float speed = 100;
    private int pathfindingCounter = 0;
    private boolean interactablePathEnd = false;

    public Facing finalFacing = Facing.UP;

    private Vector2 gridPosition;
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


    //A function to handle all movement of the chef
    public void move(TiledMap tiledMap, Node[][] grid, Camera camera){

        if(!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) keyBoardMovement(tiledMap, grid);
        else mouseMovement(tiledMap, grid, camera);

        if(!worldPath.isEmpty()){
            PathfindingUtils.drawPath(worldPath,camera, chefSprite, this);
            PathfindingUtils.followPath(chefSprite, worldPath, speed, this);
            //Interacts with anything at the end of the path
            if(pathfindingCounter == worldPath.size() && interactablePathEnd) {
                setFacing(finalFacing);
                interact(grid, tiledMap);
                interactablePathEnd = false;
            }
        }

    }
    
    //A function to handle movement with the mouse
    private void mouseMovement(TiledMap tiledMap, Node[][] grid, Camera camera){

        //Adds a delay between how often you can pathfind
        if(TimeUtils.millis() - 250 < mouseClickTime) return;
        mouseClickTime = TimeUtils.millis();

        //Convert world co-ords to grid co-ords
        Node start = setStartCoords(tiledMap, grid);
        Node end = setEndCoords(tiledMap, grid, camera);

        if(start == null || end == null) return;

        //Set up thread to do pathfinding
        PathfindingRunnable pathfindingObj = new PathfindingRunnable(start, end, grid);
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
        //Check if there is something to interact with at the end of the path
        interactablePathEnd = end.isInteractable();
        if(interactablePathEnd){
            //Fix for a path of 1
            if(gridPath.length > 1){
                Node penultimateNode = PathfindingUtils.findBestInteractingNode(start, end, grid);
                finalFacing = PathfindingUtils.calculateFinalFacing(penultimateNode, end);
            }
        }
    }

    //A function to handle movement with the keyboard
    private void keyBoardMovement(TiledMap tiledMap, Node[][] grid){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        int collisionBuffer = 2;
        CollisionHandler collisionHandler = new CollisionHandler(tileWidth, grid, tiledMap, chefSprite, squareSize, collisionBuffer);

        if(Gdx.input.isKeyPressed(Input.Keys.W) && !collisionHandler.hasCollisionUp()){
            chefSprite.translateY(speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.UP);
            worldPath.clear();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) && !collisionHandler.hasCollisionDown()){
            chefSprite.translateY(-speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.DOWN);
            worldPath.clear();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && !collisionHandler.hasCollisionLeft()){
            chefSprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.LEFT);
            worldPath.clear();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && !collisionHandler.hasCollisionRight()) {
            chefSprite.translateX(speed * Gdx.graphics.getDeltaTime());
            setFacing(Facing.RIGHT);
            worldPath.clear();
        }


        //  for testing purposes, pressing o will remove a customer from the list of active customers.
        //  depending on whom the chef is interacting with, this will remove the corresponding customer from the list
        else if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            // if(chef interaction is with customer
            PiazzaPanic.customers.remove(0);   //  to be changed to remove correct customer from list
            // else {interact with station}
        }
    }

    private Node setStartCoords(TiledMap tiledMap, Node[][] grid){
        int startGridX = TileMapUtils.positionToCoord(chefSprite.getX(), tiledMap);
        int startGridY = TileMapUtils.positionToCoord(chefSprite.getY(), tiledMap);
        if (!PathfindingUtils.isValidNode(startGridX, startGridY, grid)) return null;
        return grid[startGridX][startGridY];
    }

    private Node setEndCoords(TiledMap tiledMap, Node[][] grid, Camera camera){
        Vector3 unprojectedCoord = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        float endWorldX = unprojectedCoord.x - 128;
        float endWorldY = unprojectedCoord.y - 128;
        int endGridX = TileMapUtils.positionToCoord(endWorldX, tiledMap);
        int endGridY = TileMapUtils.positionToCoord(endWorldY, tiledMap);
        if (!PathfindingUtils.isValidNode(endGridX, endGridY, grid)) return null;
        return grid[endGridX][endGridY];
    }



    //Used to interact with other objects
    public void interact(Node[][] grid, TiledMap tiledMap){
        Node interactedNode = getInteractedNode(grid, tiledMap);
        if(interactedNode.getInteractable() != null){
            interactedNode.getInteractable().onInteract(this, interactedNode, tiledMap);
            System.out.println("Found Interactable");
        }
        else if(!foodStack.isEmpty()){
            Food currentFood = this.foodStack.pop();
            currentFood.foodSprite.setPosition(TileMapUtils.coordToPosition(interactedNode.getGridX(), tiledMap), TileMapUtils.coordToPosition(interactedNode.getGridY(), tiledMap));
            PiazzaPanic.RENDERED_FOODS.add(currentFood);
            System.out.println("Interacting with Nothing");
        }
    }

    //This function allows your chef to give the interacted chef some food
    //Keep in mind that the parameter chef refers to the chef who is giving, and interactedChef refers to the chef who is receiving
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap) {
        if(chef.foodStack.isEmpty()) return;
        Chef interactedChef = (Chef)interactedNode.getInteractable();
        interactedChef.foodStack.push(chef.foodStack.pop());
        System.out.println("Interacting with a chef");
    }

    private Node getInteractedNode(Node[][] grid, TiledMap tiledMap){
        return TileMapUtils.getNodeAtFacing(facing, grid, grid[TileMapUtils.positionToCoord(chefSprite.getX(), tiledMap)][TileMapUtils.positionToCoord(chefSprite.getY(),tiledMap)]);
    }


    @Override
    public Vector2 getPreviousGridPosition() {
        return gridPosition;
    }

    @Override
    public void setCurrentGridPosition(Vector2 gridPos) {
        gridPosition = gridPos;
    }

}
