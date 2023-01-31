package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enums.Facing;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.stations.ServingStation;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.Random;

public class Customer implements IGridEntity {
    boolean beenServed;
    private Sprite customerSprite;
    Food order;
    float orderTimer;
    private static int CUSTOMER_SIZE = 256;
    private Vector2 gridPosition;
    PathfindingActor pathfindingActor;
    Texture[] movementTextures;

    public Customer(float orderTimer){
        movementTextures = getRandomCustomerTextures();
        customerSprite = new Sprite(movementTextures[0] ,256, 256);
        this.customerSprite.setScale(2f);
        beenServed = false;
        order = getRandomOrder();
        this.orderTimer = orderTimer;
    }

    private Texture[] getRandomCustomerTextures(){
        Texture[][] customerTextures = new Texture[][] {{new Texture("CustomerAssets/Customer_Blue_Up.png"),new Texture("CustomerAssets/Customer_Blue_Left.png"),new Texture("CustomerAssets/Customer_Blue_Down.png"),new Texture("CustomerAssets/Customer_Blue_Right.png")},
        {new Texture("CustomerAssets/Customer_Orange_Up.png"),new Texture("CustomerAssets/Customer_Orange_Left.png"),new Texture("CustomerAssets/Customer_Orange_Down.png"),new Texture("CustomerAssets/Customer_Orange_Right.png")},
        {new Texture("CustomerAssets/Customer_Pink_Up.png"),new Texture("CustomerAssets/Customer_Pink_Left.png"),new Texture("CustomerAssets/Customer_Pink_Down.png"),new Texture("CustomerAssets/Customer_Pink_Right.png")}};
        Random rnd = new Random();
        int textureChoice = rnd.nextInt(customerTextures.length);
        return customerTextures[textureChoice];
    }

    private Food getRandomOrder() {
        Random rnd = new Random();
        int orderIndex = rnd.nextInt(FoodItems.finishedFoods.size());
        System.out.println(FoodItems.finishedFoods.get(orderIndex).getName());
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
        int stationIndex = rnd.nextInt(GameScreen.availableServingStations.size());
        ServingStation servingStation = GameScreen.availableServingStations.get(stationIndex);
        GameScreen.availableServingStations.remove(stationIndex);
        servingStation.setCurrentCustomer(this);
        //Get grid position of serving station
        int xPos = TileMapUtils.positionToCoord(servingStation.getSprite().getX(), tiledMap);
        int yPos = TileMapUtils.positionToCoord(servingStation.getSprite().getY(), tiledMap);
        //Choose a free node around the serving station
        int[] xMod = {-1, 1};
        for(int i = 0; i < xMod.length; i++){
            if(!PathfindingUtils.isValidNode(xPos + xMod[i], yPos, grid)) continue;
            if(grid[xPos + xMod[i]][yPos].getNodeType() != NodeType.EMPTY) continue;
            return grid[xPos + xMod[i]][yPos];
        }
        //Can't find a node to go
        //We should stop the customer spawning
        GameScreen.canSpawnCustomers = false;
        return grid[xPos][yPos];
    }


    public void moveCustomer(){
        if(pathfindingActor.getWorldPath().isEmpty()) return;
        pathfindingActor.followPath(customerSprite, 100f, movementTextures);
        if(pathfindingActor.getPathfindingCounter() == pathfindingActor.getWorldPath().size()){
            if(beenServed){
                GameScreen.customers.remove(this);
                customerSprite.setPosition(1000,1000);
            }
            else{
                pathfindingActor.setFacing(customerSprite, Facing.UP, movementTextures);
                pathfindingActor.getWorldPath().clear();
                pathfindingActor.setPathfindingCounter(0);
            }
        }
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
