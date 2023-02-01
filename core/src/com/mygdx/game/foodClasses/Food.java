package com.mygdx.game.foodClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.Node;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;

/**
 * The type Food.
 */
public class Food implements IInteractable, IGridEntity {

    private String name; //The name of the food
    private Sprite foodSprite; //The sprite of the food
    private boolean isFormable; //Whether the food can be used in the FormingStation
    private int reward; //The monetary reward for making the food
    private Vector2 gridPosition; //Used to keep track of the gridPosition of the food for the updateGridEntities() method in PiazzaPanic

    /**
     * Instantiates a new Food.
     *
     * @param name        the name of the food, e.g. Pizza
     * @param foodTexture the Texture of the food
     * @param isFormable  Whether the food can be used in a forming station
     * @param reward      the monetary reward the player gets for serving a customer the food.
     *                    If the food has a monetary reward, it is automatically added to the list of foodItems that the customers can used
     */
    public Food(String name, Texture foodTexture, boolean isFormable, int reward){
        this.setName(name);
        foodSprite = new Sprite(foodTexture);
        this.setFormable(isFormable);
        this.setReward(reward);

        if(reward > 0){
            FoodItems.finishedFoods.add(this);
            System.out.println(name + " was added to finished Foods");
        }
    }

    /**
     * Instantiates a new Food.
     *
     * @param foodSettings Allows a new Food object to be created using the pre-established foodItems in the FoodItems class
     */
    public Food(Food foodSettings){
        setName(foodSettings.getName());
        TextureRegion textureRegion = new TextureRegion(foodSettings.foodSprite.getTexture(), 0,0,64,64);
        foodSprite = new Sprite(textureRegion.getTexture());
        foodSprite.setSize(64,64);
        foodSprite.setScale(0.75f);
        setFormable(foodSettings.isFormable());
        setReward(foodSettings.getReward());
    }

    /**
     * Compares two foods to see if they are the same
     *
     * @param f the food you are comparing this food to
     * @return Whether or not the foods are of the same type
     */
//A function to compare two food items
    public boolean equals(Food f) {
        return getName() == f.getName();
    }

    @Override
    public Sprite getSprite() {
        return foodSprite;
    }

    //Caused the foodItem to be picked up by the chef when it is interacted with
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        SoundUtils.getItemPickupSound().play();
        GameScreen.getRenderedFoods().remove(this);
        chef.getFoodStack().push(this);
        interactedNode.setGridEntity(null);
        interactedNode.setInteractable(null);
        interactedNode.setNodeType(NodeType.EMPTY);
        System.out.println("Interacting with Food");
    }

    //Two helper functions for the updateGridEntities() method in PiazzaPanic
    @Override
    public Vector2 getPreviousGridPosition() {
        return gridPosition;
    }

    @Override
    public void setCurrentGridPosition(Vector2 gridPos) {
        gridPosition = gridPos;
    }

    /**
     * Sets tile map position.
     *
     * @param mapPosX  the map pos x
     * @param mapPosY  the map pos y
     * @param grid     the grid
     * @param tiledMap the tiled map
     */
//Places the foodItems down on the grid
    public void setTileMapPosition(int mapPosX, int mapPosY, Node[][] grid, TiledMap tiledMap)    {
        if(!PathfindingUtils.isValidNode(mapPosX, mapPosY, grid)) return;
        grid[mapPosX][mapPosY].setNodeType(NodeType.FOOD);
        grid[mapPosX][mapPosY].setGridEntity(this);
        foodSprite.setPosition(TileMapUtils.coordToPosition(mapPosX, tiledMap), TileMapUtils.coordToPosition(mapPosY, tiledMap));
    }

    /**
     * Gets name of the food.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the food.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Is formable boolean.
     *
     * @return the boolean
     */
    public boolean isFormable() {
        return isFormable;
    }

    /**
     * Sets formable.
     *
     * @param formable the formable
     */
    public void setFormable(boolean formable) {
        isFormable = formable;
    }

    /**
     * Gets reward.
     *
     * @return the reward
     */
    public int getReward() {
        return reward;
    }

    /**
     * Sets reward.
     *
     * @param reward the reward
     */
    public void setReward(int reward) {
        this.reward = reward;
    }
}
