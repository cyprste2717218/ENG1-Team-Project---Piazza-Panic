package com.mygdx.game.foodClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Chef;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.Node;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;

public class Food implements IInteractable, IGridEntity {

    public String name; //The name of the food
    private Sprite foodSprite; //The sprite of the food
    public boolean isFormable; //Whether the food can be used in the FormingStation
    public int reward; //The monetary reward for making the food
    private Vector2 gridPosition; //Used to keep track of the gridPosition of the food for the updateGridEntities() method in PiazzaPanic

    public Food(String name, Texture foodTexture, boolean isFormable, int reward){
        this.name = name;
        foodSprite = new Sprite(foodTexture);
        this.isFormable = isFormable;
        this.reward = reward;

        //If the food has a monetary reward, it is automatically added to the list of foodItems that the customers can used
        if(reward > 0){
            FoodItems.finishedFoods.add(this);
            System.out.println(name + " was added to finished Foods");
        }
    }

    //A secondary constructor to create foodItems from the pre-established settings in the FoodItems class
    public Food(Food foodSettings){
        name = foodSettings.name;
        TextureRegion textureRegion = new TextureRegion(foodSettings.foodSprite.getTexture(), 0,0,64,64);
        foodSprite = new Sprite(textureRegion.getTexture());
        foodSprite.setSize(64,64);
        foodSprite.setScale(0.75f);
        isFormable = foodSettings.isFormable;
        reward = foodSettings.reward;
    }

    //A function to compare two food items
    public boolean equals(Food f) {
        return name == f.name;
    }

    @Override
    public Sprite getSprite() {
        return foodSprite;
    }

    //Caused the foodItem to be picked up by the chef when it is interacted with
    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid) {
        SoundUtils.getItemPickupSound().play();
        GameScreen.RENDERED_FOODS.remove(this);
        chef.foodStack.push(this);
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

    //Places the foodItems down on the grid
    public void setTileMapPosition(int mapPosX, int mapPosY, Node[][] grid, TiledMap tiledMap)    {
        if(!PathfindingUtils.isValidNode(mapPosX, mapPosY, grid)) return;
        grid[mapPosX][mapPosY].setNodeType(NodeType.FOOD);
        grid[mapPosX][mapPosY].setGridEntity(this);
        foodSprite.setPosition(TileMapUtils.coordToPosition(mapPosX, tiledMap), TileMapUtils.coordToPosition(mapPosY, tiledMap));
    }
}
