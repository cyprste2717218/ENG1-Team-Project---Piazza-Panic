package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

/**
 * The type Chef.
 */
public class Chef implements IInteractable, IGridEntity {
    private final Sprite chefSprite;
    private Stack<Food> foodStack;
    private final int squareSize = 32;
    /**
     * The Mouse click time.
     */
    long mouseClickTime = 0;
    /**
     * The Speed.
     */
    final float speed = 100;
    private boolean interactablePathEnd = false;
    private Facing finalFacing = Facing.UP;
    private Vector2 gridPosition;
    /**
     * The Pathfinding actor.
     */
    PathfindingActor pathfindingActor;
    private Texture[] movementTextures;

    /**
     * Instantiates a new Chef.
     *
     * @param textureSelecter the texture selecter
     */
    public Chef(int textureSelecter){
        setMovementTextures(selectChefTexture(textureSelecter));
        chefSprite = new Sprite(getMovementTextures()[0], 256, 256);
        this.chefSprite.setScale(2f, 1.5f);
        foodStack = new Stack<>();
        pathfindingActor = new PathfindingActor(null, null, null,null);
    }

    private Texture[] selectChefTexture(int textureSelecter){
        Texture[][] chefTextures = new Texture[][] {{new Texture("Chef_Assets/Chef_Dark_Up.png"),new Texture("Chef_Assets/Chef_Dark_Left.png"),new Texture("Chef_Assets/Chef_Dark_Down.png"),new Texture("Chef_Assets/Chef_Dark_Right.png")},
                {new Texture("Chef_Assets/Chef_Pink_Up.png"),new Texture("Chef_Assets/Chef_Pink_Left.png"),new Texture("Chef_Assets/Chef_Pink_Down.png"),new Texture("Chef_Assets/Chef_Pink_Right.png")}};
        return chefTextures[textureSelecter];
    }
    @Override
    public Sprite getSprite(){
        return chefSprite;
    }

    /**
     * Get pathfinding actor pathfinding actor.
     *
     * @return the pathfinding actor
     */
    public PathfindingActor getPathfindingActor(){
        return pathfindingActor;
    }


    /**
     * Move.
     *
     * @param tiledMap the tiled map
     * @param grid     the grid
     * @param camera   the camera
     * @param match    the match
     */
//A function to handle all movement of the chef
    public void move(TiledMap tiledMap, Node[][] grid, Camera camera, Match match){

        if(!Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) keyBoardMovement(tiledMap, grid);
        else mouseMovement(tiledMap, grid, camera);

        if(!pathfindingActor.getWorldPath().isEmpty()){
            pathfindingActor.drawPath(camera, chefSprite);
            pathfindingActor.followPath(chefSprite, speed, getMovementTextures());
            //Interacts with anything at the end of the path
            if(getPathfindingActor().getPathfindingCounter() == pathfindingActor.getWorldPath().size() && interactablePathEnd) {
                System.out.println("Interactable Path End");
                pathfindingActor.setFacing(chefSprite, getFinalFacing(), getMovementTextures());
                interact(grid, tiledMap, match);
                interactablePathEnd = false;
            }
        }
        if(interactablePathEnd && pathfindingActor.getWorldPath().isEmpty()){
            System.out.println("Interactable Path End");
            pathfindingActor.setFacing(chefSprite, getFinalFacing(), getMovementTextures());
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
                setFinalFacing(PathfindingUtils.calculateFinalFacing(penultimateNode, end));
            }
        }
    }

    //A function to handle movement with the keyboard
    private void keyBoardMovement(TiledMap tiledMap, Node[][] grid){
        float speed = 100f;
        Vector2 oldPos = new Vector2(chefSprite.getX(), chefSprite.getY());
        CollisionHandler collisionHandler = new CollisionHandler(grid, tiledMap, chefSprite, squareSize - 2);

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            chefSprite.translateY(speed * Gdx.graphics.getDeltaTime());
            pathfindingActor.setFacing(chefSprite, Facing.UP, getMovementTextures());
            pathfindingActor.getWorldPath().clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            chefSprite.translateY(-speed * Gdx.graphics.getDeltaTime());
            pathfindingActor.setFacing(chefSprite, Facing.DOWN, getMovementTextures());
            pathfindingActor.getWorldPath().clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            chefSprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            pathfindingActor.setFacing(chefSprite, Facing.LEFT, getMovementTextures());
            pathfindingActor.getWorldPath().clear();
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            chefSprite.translateX(speed * Gdx.graphics.getDeltaTime());
            pathfindingActor.setFacing(chefSprite, Facing.RIGHT, getMovementTextures());
            pathfindingActor.getWorldPath().clear();
        }

        if(collisionHandler.hasCollision()){
            chefSprite.setPosition(oldPos.x, oldPos.y);
        }


        //  for testing purposes, pressing o will remove a customer from the list of active customers.
        //  depending on whom the chef is interacting with, this will remove the corresponding customer from the list
        else if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            // if(chef interaction is with customer
            GameScreen.getCustomers().remove(0);   //  to be changed to remove correct customer from list
            // else {interact with station}
        }
    }

    /**
     * Set tile map position.
     *
     * @param mapPosX  the map pos x
     * @param mapPosY  the map pos y
     * @param grid     the grid
     * @param tiledMap the tiled map
     */
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


    /**
     * Interact.
     *
     * @param grid     the grid
     * @param tiledMap the tiled map
     * @param match    the match
     */
//Used to interact with other objects
    public void interact(Node[][] grid, TiledMap tiledMap, Match match){
        Node interactedNode = getInteractedNode(grid, tiledMap);
        if(interactedNode.getInteractable() != null){
            IInteractable interactableEntity = interactedNode.getInteractable();
            interactableEntity.onInteract(this, interactedNode, tiledMap, grid, match);
            System.out.println("Found Interactable");
            return;
        }
        else if(getFoodStack().isEmpty() || interactedNode.isCollidable()){
            SoundUtils.getFailureSound().play();
            return;
        }
        else{
            Food currentFood = new Food(this.getFoodStack().pop());
            currentFood.getSprite().setPosition(TileMapUtils.coordToPosition(interactedNode.getGridX(), tiledMap), TileMapUtils.coordToPosition(interactedNode.getGridY(), tiledMap));
            GameScreen.getRenderedFoods().add(currentFood);
            System.out.println("Interacting with Nothing");
            SoundUtils.getItemPickupSound().play();
        }
    }

    /**
     * Draw sprites.
     *
     * @param batch the batch
     */
    public void drawSprites(SpriteBatch batch){
        if(getFoodStack().isEmpty()){
            chefSprite.draw(batch);
            return;
        }
        switch (pathfindingActor.getFacing()){
            case UP:
                batch.draw(getFoodStack().peek().getSprite().getTexture(), chefSprite.getX() + 96, chefSprite.getY() + 96);
                chefSprite.draw(batch);
                break;
            case DOWN:
                chefSprite.draw(batch);
                batch.draw(getFoodStack().peek().getSprite().getTexture(), chefSprite.getX() + 96, chefSprite.getY() + 96);
                break;
            case RIGHT:
                chefSprite.draw(batch);
                batch.draw(getFoodStack().peek().getSprite().getTexture(), chefSprite.getX() + 111, chefSprite.getY() + 85);
                break;
            case LEFT:
                chefSprite.draw(batch);
                batch.draw(getFoodStack().peek().getSprite().getTexture(), chefSprite.getX() + 81, chefSprite.getY() + 85);
                break;
        }
    }

    //This function allows your chef to give the interacted chef some food
    //Keep in mind that the parameter chef refers to the chef who is giving, and interactedChef refers to the chef who is receiving
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        if(chef.getFoodStack().isEmpty()) return;
        Chef interactedChef = (Chef)interactedNode.getGridEntity();
        if(chef.getFoodStack().isEmpty()){
            SoundUtils.getFailureSound().play();
            return;
        }
        interactedChef.getFoodStack().push(chef.getFoodStack().pop());
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

    /**
     * Gets food stack.
     *
     * @return the food stack
     */
    public Stack<Food> getFoodStack() {
        return foodStack;
    }

    /**
     * Sets food stack.
     *
     * @param foodStack the food stack
     */
    public void setFoodStack(Stack<Food> foodStack) {
        this.foodStack = foodStack;
    }

    /**
     * Gets final facing.
     *
     * @return the final facing
     */
    public Facing getFinalFacing() {
        return finalFacing;
    }

    /**
     * Sets final facing.
     *
     * @param finalFacing the final facing
     */
    public void setFinalFacing(Facing finalFacing) {
        this.finalFacing = finalFacing;
    }

    /**
     * Get movement textures texture [ ].
     *
     * @return the texture [ ]
     */
    public Texture[] getMovementTextures() {
        return movementTextures;
    }

    /**
     * Sets movement textures.
     *
     * @param movementTextures the movement textures
     */
    public void setMovementTextures(Texture[] movementTextures) {
        this.movementTextures = movementTextures;
    }
}
