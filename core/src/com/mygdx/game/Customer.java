package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enums.Facing;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.interfaces.IPathfinder;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.threads.PathfindingRunnable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Customer implements IInteractable, ITimer, IPathfinder {
    boolean beenServed;
    private Sprite customerSprite;
    Food order;
    float orderTimer;
    private static int CUSTOMER_SIZE = 256;
    private Vector2 gridPosition;
    private List<Vector2> worldPath = new ArrayList<>();
    private int pathfindingCounter = 0;
    private Facing facing = Facing.UP;
    public Sprite orderSprite;


    public Customer(Texture customerTexture, float orderTimer){
        customerSprite = new Sprite(customerTexture, CUSTOMER_SIZE, CUSTOMER_SIZE);
        this.customerSprite.setScale(0.125f);
        beenServed = false;
        order = getRandomOrder();
        this.orderTimer = orderTimer;

        orderSprite = new Sprite(order.getSprite().getTexture(), CUSTOMER_SIZE, CUSTOMER_SIZE);
        orderSprite.setScale(0.0625f);
        orderSprite.setPosition(customerSprite.getX() + 16, customerSprite.getY() + 16);
    }

    private Food getRandomOrder() {
        Random rnd = new Random();
        int orderIndex = rnd.nextInt(FoodItems.finishedFoods.size()-1);
        return FoodItems.finishedFoods.get(orderIndex);
    }


    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid) {
        if(!chef.foodStack.isEmpty()) {
            if (chef.foodStack.peek() == order) {
                PiazzaPanic.CUSTOMER_SERVED_COUNTER++;
                //Have customer leave by pathfinding from their current position to the bottom of the screen and then being deleted

                Node start = grid[TileMapUtils.positionToCoord(customerSprite.getX(), tiledMap)]
                        [TileMapUtils.positionToCoord(customerSprite.getY(), tiledMap)];
                Node end = grid[8][1];

                //Set up thread to do pathfinding
                PathfindingRunnable pathfindingObj = new PathfindingRunnable(start, end, grid);
                Thread pathfindingThread = new Thread(pathfindingObj);
                pathfindingThread.start();
                //Stalls the main thread until the pathfinding is complete
                while (pathfindingThread.isAlive()){}
                //Gets the path from the thread in grid co-ordinates
                Vector2[] gridPath = pathfindingObj.getGridPath();
                worldPath = PathfindingUtils.convertGridPathToWorld(gridPath, tiledMap);
                beenServed = true;
            }
        }
    }

    public void onSpawn(Node[][] grid, TiledMap tiledMap){
        Node start = grid[8][1];
        Node end = getValidSpawningNode(grid);

        if(end == null) return;

        //Set up thread to do pathfinding
        PathfindingRunnable pathfindingObj = new PathfindingRunnable(start, end, grid);
        Thread pathfindingThread = new Thread(pathfindingObj);
        pathfindingThread.start();
        //Stalls the main thread until the pathfinding is complete
        while (pathfindingThread.isAlive()){}
        //Gets the path from the thread in grid co-ordinates
        Vector2[] gridPath = pathfindingObj.getGridPath();
        worldPath = PathfindingUtils.convertGridPathToWorld(gridPath, tiledMap);
    }

    private Node getValidSpawningNode(Node[][] grid){
        Node[] spawningLocations = {grid[12][4], grid[12][2]};
        List<Node> validLocations = new ArrayList<>();

        for(Node n : spawningLocations){
            if(n.getNodeType() == NodeType.EMPTY){
                validLocations.add(n);
            }
        }

        if(validLocations.isEmpty()){
            //Need to do something better when there isn't a valid spawning location
            PiazzaPanic.customers.remove(this);
            customerSprite.setPosition(1000,1000);
            return null;
        }
        Random rnd = new Random();
        return validLocations.get(rnd.nextInt(validLocations.size()));

    }


    public void moveCustomer(){
        if(worldPath.isEmpty()) return;
        PathfindingUtils.followPath(customerSprite, worldPath, 100f, this);
        orderSprite.setPosition(customerSprite.getX() + 16, customerSprite.getY() + 20);
        if(getPathCounter() == worldPath.size()){
            if(beenServed){
                PiazzaPanic.customers.remove(this);
                customerSprite.setPosition(1000,1000);
            }
            else{
                worldPath.clear();
                setPathCounter(0);
            }
        }
    }

    @Override
    public Sprite getSprite() {
        return customerSprite;
    }

    @Override
    public Vector2 getPreviousGridPosition() {
        return gridPosition;
    }

    @Override
    public void setCurrentGridPosition(Vector2 gridPos) {
        gridPosition = gridPos;
    }

    @Override
    public float runTimer(float timerValue) {
        if(timerValue == 0){
            //FAILURE CONDITION
        }
        return timerValue--;
    }

    @Override
    public void setPathCounter(int counter) {
        pathfindingCounter = counter;
    }

    @Override
    public int getPathCounter() {
        return pathfindingCounter;
    }

    @Override
    public void setFacing(Facing direction) {
        facing = direction;
    }

    @Override
    public Facing getFacing() {
        return facing;
    }
}
