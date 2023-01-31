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
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.utils.CollisionHandler;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.Stack;
import com.mygdx.game.enums.Facing;

public class Chef implements IInteractable, IGridEntity {

    private static final int CHEF_SIZE = 256;
    private final Sprite chefSprite;
    public Stack<Food> foodStack;
    private final int squareSize = 32;
    long mouseClickTime = 0;
    final float speed = 100;
    private boolean interactablePathEnd = false;
    public Facing finalFacing = Facing.UP;
    private Vector2 gridPosition;
    PathfindingActor pathfindingActor;

    Texture[] movementTextures;

    public Chef(){
        movementTextures = new Texture[] {new Texture("CustomerAssets/Customer_Pink_Up.png"),new Texture("CustomerAssets/Customer_Pink_Left.png"),new Texture("CustomerAssets/Customer_Pink_Down.png"),new Texture("CustomerAssets/Customer_Pink_Right.png")};
        chefSprite = new Sprite(movementTextures[0], 256, 256);
        this.chefSprite.setScale(2f);
        foodStack = new Stack<>();
        pathfindingActor = new PathfindingActor(null, null, null,null);
    }

    @Override
    public Sprite getSprite(){
        return chefSprite;
    }

    public PathfindingActor getPathfindingActor(){
        return pathfindingActor;
    }


    //A function to handle all movement of the chef
    public void move(TiledMap tiledMap, Node[][] grid, Camera camera, Match match){

        if(!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) keyBoardMovement(tiledMap, grid);
        else mouseMovement(tiledMap, grid, camera);

        if(!pathfindingActor.getWorldPath().isEmpty()){
            pathfindingActor.drawPath(camera, chefSprite);
            pathfindingActor.followPath(chefSprite, speed, movementTextures);
            //Interacts with anything at the end of the path
            if(getPathfindingActor().getPathfindingCounter() == pathfindingActor.getWorldPath().size() && interactablePathEnd) {
                System.out.println("Interactable Path End");
                pathfindingActor.setFacing(chefSprite, finalFacing, movementTextures);
                interact(grid, tiledMap, match);
                interactablePathEnd = false;
            }
        }
        if(interactablePathEnd && pathfindingActor.getWorldPath().isEmpty()){
            System.out.println("Interactable Path End");
            pathfindingActor.setFacing(chefSprite, finalFacing, movementTextures);
            interact(grid, tiledMap, match);
            interactablePathEnd = false;
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
        pathfindingActor = new PathfindingActor(start, end, grid, tiledMap);
        pathfindingActor.createThreadAndPathfind();
        if(pathfindingActor.getWorldPath().size() == 0) return;
        pathfindingActor.setPathfindingCounter(0);
        //Check if there is something to interact with at the end of the path
        interactablePathEnd = end.isInteractable();
        if(interactablePathEnd){
            //Fix for a path of 1
            if(pathfindingActor.getWorldPath().size() > 0){
                Node penultimateNode = PathfindingUtils.findBestInteractingNode(start, end, grid);
                finalFacing = PathfindingUtils.calculateFinalFacing(penultimateNode, end);
            }
        }
    }

    //A function to handle movement with the keyboard
    private void keyBoardMovement(TiledMap tiledMap, Node[][] grid){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        int tileWidth = layer.getTileWidth();
        float speed = 100f;
        Vector2 oldPos = new Vector2(chefSprite.getX(), chefSprite.getY());
        CollisionHandler collisionHandler = new CollisionHandler(tileWidth, grid, tiledMap, chefSprite, squareSize - 2, speed);

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            chefSprite.translateY(speed * Gdx.graphics.getDeltaTime());
            pathfindingActor.setFacing(chefSprite, Facing.UP, movementTextures);
            pathfindingActor.getWorldPath().clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            chefSprite.translateY(-speed * Gdx.graphics.getDeltaTime());
            pathfindingActor.setFacing(chefSprite, Facing.DOWN, movementTextures);
            pathfindingActor.getWorldPath().clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            chefSprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            pathfindingActor.setFacing(chefSprite, Facing.LEFT, movementTextures);
            pathfindingActor.getWorldPath().clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            chefSprite.translateX(speed * Gdx.graphics.getDeltaTime());
            pathfindingActor.setFacing(chefSprite, Facing.RIGHT, movementTextures);
            pathfindingActor.getWorldPath().clear();
        }

        if(collisionHandler.hasCollision()){
            chefSprite.setPosition(oldPos.x, oldPos.y);
        }


        //  for testing purposes, pressing o will remove a customer from the list of active customers.
        //  depending on whom the chef is interacting with, this will remove the corresponding customer from the list
        else if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            // if(chef interaction is with customer
            GameScreen.customers.remove(0);   //  to be changed to remove correct customer from list
            // else {interact with station}
        }
    }

    public void setTileMapPosition(int mapPosX, int mapPosY, Node[][] grid, TiledMap tiledMap){
        if(!PathfindingUtils.isValidNode(mapPosX, mapPosY, grid)) return;
        grid[mapPosX][mapPosY].setNodeType(NodeType.CHEF);
        grid[mapPosX][mapPosY].setGridEntity(this);
        grid[mapPosX][mapPosY].setInteractable(this);
        chefSprite.setPosition(TileMapUtils.coordToPosition(mapPosX, tiledMap), TileMapUtils.coordToPosition(mapPosY, tiledMap));
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
    public void interact(Node[][] grid, TiledMap tiledMap, Match match){
        Node interactedNode = getInteractedNode(grid, tiledMap);
        if(interactedNode.getInteractable() != null){
            IInteractable interactableEntity = interactedNode.getInteractable();
            interactableEntity.onInteract(this, interactedNode, tiledMap, grid, match);
            System.out.println("Found Interactable");
            return;
        }
        else if(foodStack.isEmpty() || interactedNode.isCollidable()){
            SoundUtils.getFailureSound().play();
            return;
        }
        else{
            Food currentFood = new Food(this.foodStack.pop());
            currentFood.getSprite().setPosition(TileMapUtils.coordToPosition(interactedNode.getGridX(), tiledMap), TileMapUtils.coordToPosition(interactedNode.getGridY(), tiledMap));
            GameScreen.RENDERED_FOODS.add(currentFood);
            System.out.println("Interacting with Nothing");
            SoundUtils.getItemPickupSound().play();
        }
    }

    //This function allows your chef to give the interacted chef some food
    //Keep in mind that the parameter chef refers to the chef who is giving, and interactedChef refers to the chef who is receiving
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        if(chef.foodStack.isEmpty()) return;
        Chef interactedChef = (Chef)interactedNode.getGridEntity();
        if(chef.foodStack.isEmpty()){
            SoundUtils.getFailureSound().play();
            return;
        }
        interactedChef.foodStack.push(chef.foodStack.pop());
        System.out.println("Interacting with a chef");
        SoundUtils.getItemPickupSound().play();

    }

    private Node getInteractedNode(Node[][] grid, TiledMap tiledMap){
        return TileMapUtils.getNodeAtFacing(pathfindingActor.getFacing(), grid, grid[TileMapUtils.positionToCoord(chefSprite.getX(), tiledMap)][TileMapUtils.positionToCoord(chefSprite.getY(),tiledMap)]);
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
