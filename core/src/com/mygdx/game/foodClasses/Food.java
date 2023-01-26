package com.mygdx.game.foodClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;

public class Food implements IInteractable {

    public String name;
    private Sprite foodSprite;
    public boolean isFormable;
    public int reward;

    private Vector2 gridPosition;

    public Food(String name, Texture foodTexture, boolean isFormable, int reward){
        name = this.name;
        foodSprite = new Sprite(foodTexture, 256, 256);
        foodSprite.setScale(0.125f);
        this.isFormable = isFormable;
        this.reward = reward;

        if(reward > 0){
            FoodItems.finishedFoods.add(this);
        }
    }

    public Food(Food foodSettings){
        name = foodSettings.name;
        foodSprite = new Sprite(foodSettings.foodSprite.getTexture(), 256, 256);
        foodSprite.setScale(0.125f);
        isFormable = foodSettings.isFormable;
        reward = foodSettings.reward;
    }
    public boolean equals(Food f) {
        return this.name == f.name;
    }

    @Override
    public Sprite getSprite() {
        return foodSprite;
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid) {
        SoundUtils.getItemPickupSound().play();
        PiazzaPanic.RENDERED_FOODS.remove(this);
        chef.foodStack.push(this);
        interactedNode.setInteractable(null);
        interactedNode.setNodeType(NodeType.EMPTY);
        System.out.println("Interacting with Food");
    }

    @Override
    public Vector2 getPreviousGridPosition() {
        return gridPosition;
    }

    @Override
    public void setCurrentGridPosition(Vector2 gridPos) {
        gridPosition = gridPos;
    }

    public void setTileMapPosition(int mapPosX, int mapPosY, Node[][] grid, TiledMap tiledMap)    {
        if(!PathfindingUtils.isValidNode(mapPosX, mapPosY, grid)) return;
        grid[mapPosX][mapPosY].setNodeType(NodeType.FOOD);
        grid[mapPosX][mapPosY].setInteractable(this);
        foodSprite.setPosition(TileMapUtils.coordToPosition(mapPosX, tiledMap), TileMapUtils.coordToPosition(mapPosY, tiledMap));
    }
}
