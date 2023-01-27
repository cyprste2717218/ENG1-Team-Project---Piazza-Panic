package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enums.Facing;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.stations.ServingStation;
import com.mygdx.game.threads.PathfindingRunnable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Customer implements ITimer, IGridEntity {
    boolean beenServed;
    private Sprite customerSprite;
    Food order;
    float orderTimer;
    private static int CUSTOMER_SIZE = 256;
    private Vector2 gridPosition;
    PathfindingActor pathfindingActor;

    public Customer(Texture customerTexture, float orderTimer){
        customerSprite = new Sprite(customerTexture, CUSTOMER_SIZE, CUSTOMER_SIZE);
        this.customerSprite.setScale(0.125f);
        beenServed = false;
        order = getRandomOrder();
        this.orderTimer = orderTimer;
    }

    private Food getRandomOrder() {
        Random rnd = new Random();
        int orderIndex = rnd.nextInt(FoodItems.finishedFoods.size());
        System.out.println(FoodItems.finishedFoods.get(orderIndex).name);
        return FoodItems.finishedFoods.get(orderIndex);
    }

    public Food getOrder(){
        return order;
    }

    public void setBeenServed(boolean hasBeenServed){
        beenServed = hasBeenServed;
    }


    //Have customer leave by pathfinding from their current position to the bottom of the screen and then being deleted
    public void customerLeave(Node[][] grid, TiledMap tiledMap){
        Node start = grid[TileMapUtils.positionToCoord(customerSprite.getX(), tiledMap)]
                [TileMapUtils.positionToCoord(customerSprite.getY(), tiledMap)];
        Node end = grid[8][1];

        pathfindingActor = new PathfindingActor(start, end, grid, tiledMap);
        pathfindingActor.createThreadAndPathfind();
    }

    public void onSpawn(Node[][] grid, TiledMap tiledMap){
        Node start = grid[8][1];
        Node end = getAvailableServingStation(grid, tiledMap);

        pathfindingActor = new PathfindingActor(start, end, grid, tiledMap);
        pathfindingActor.createThreadAndPathfind();
    }

    private Node getAvailableServingStation(Node[][] grid, TiledMap tiledMap){
        //Choose random available serving station
        Random rnd = new Random();
        ServingStation servingStation = PiazzaPanic.availableServingStations.get(rnd.nextInt(PiazzaPanic.availableServingStations.size()));
        PiazzaPanic.availableServingStations.remove(servingStation);
        servingStation.setCurrentCustomer(this);
        //Get grid position of serving station
        int xPos = TileMapUtils.positionToCoord(servingStation.getSprite().getX(), tiledMap);
        int yPos = TileMapUtils.positionToCoord(servingStation.getSprite().getY(), tiledMap);
        //Choose a free node around the serving station
        int[] xMod = {-1, 0, 0, 1};
        int[] yMod = {0, 1, -1, 0};
        for(int i = 0; i < xMod.length; i++){
            if(!PathfindingUtils.isValidNode(xPos + xMod[i], yPos + yMod[i], grid)) continue;
            if(grid[xPos + xMod[i]][yPos + yMod[i]].getNodeType() != NodeType.EMPTY) continue;
            return grid[xPos + xMod[i]][yPos + yMod[i]];
        }
        //Can't find a node to go
        //We should stop the customer spawning
        return grid[xPos][yPos];
    }


    public void moveCustomer(){
        if(pathfindingActor.getWorldPath().isEmpty()) return;
        pathfindingActor.followPath(customerSprite, 100f);
        if(pathfindingActor.getPathfindingCounter() == pathfindingActor.getWorldPath().size()){
            if(beenServed){
                PiazzaPanic.customers.remove(this);
                customerSprite.setPosition(1000,1000);
            }
            else{
                pathfindingActor.setFacing(customerSprite, Facing.UP);
                pathfindingActor.getWorldPath().clear();
                pathfindingActor.setPathfindingCounter(0);
            }
        }
    }

    @Override
    public float runTimer(float timerValue) {
        if(timerValue == 0){
            //FAILURE CONDITION
        }
        return timerValue--;
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
    public Sprite getSprite() {
        return customerSprite;
    }
}
