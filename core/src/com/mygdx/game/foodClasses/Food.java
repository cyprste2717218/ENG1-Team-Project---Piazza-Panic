package com.mygdx.game.foodClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.utils.PathfindingUtils;
import com.mygdx.game.utils.TileMapUtils;

public class Food implements IInteractable {

    public String name;
    public Sprite foodSprite;
    public boolean isFryable;
    public boolean isChoppable;
    public boolean isBakeable;
    public boolean isFormable;
    public boolean isToastable;
    public int reward;

    private Vector2 gridPosition;

    public Food(FoodBuilder builder){
        name = builder.name;
        foodSprite = new Sprite(builder.foodTexture, 256, 256);
        foodSprite.setScale(0.125f);
        isFryable = builder.isFryable;
        isBakeable = builder.isBakeable;
        isChoppable = builder.isChoppable;
        isFormable = builder.isFormable;
        isToastable = builder.isToastable;
        reward = builder.reward;

        if(reward > 0){
            FoodItems.finishedFoods.add(this);
        }
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap) {
        PiazzaPanic.RENDERED_FOODS.remove(this);
        chef.foodStack.push(this);
        interactedNode.setInteractable(null);
        interactedNode.setFood(false);
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

    public void setTileMapPosition(int mapPosX, int mapPosY, Node[][] walls, TiledMap tiledMap)    {
        if(!PathfindingUtils.isValidNode(mapPosX, mapPosY, walls)) return;
        walls[mapPosX][mapPosY].setFood(true);
        walls[mapPosX][mapPosY].setInteractable(this);
        foodSprite.setPosition(TileMapUtils.coordToPosition(mapPosX, tiledMap), TileMapUtils.coordToPosition(mapPosY, tiledMap));
    }


    public static class FoodBuilder{
        private String name;
        private Texture foodTexture;
        private boolean isFryable = false;
        private boolean isChoppable = false;
        private boolean isBakeable = false;
        private boolean isFormable = false;
        private boolean isToastable = false;
        private int reward = 0;

        public FoodBuilder(String name, Texture foodTexture){
            this.name = name;
            this.foodTexture = foodTexture;
        }

        public FoodBuilder setFryable(){
            isFryable = true;
            return this;
        }

        public FoodBuilder setChoppable(){
            isChoppable = true;
            return this;
        }

        public FoodBuilder setBakeable(){
            isBakeable = true;
            return this;
        }

        public FoodBuilder setFormable(){
            isFormable = true;
            return this;
        }

        public FoodBuilder setToastable(){
            isToastable = true;
            return this;
        }

        public FoodBuilder setReward(int reward){
            this.reward = reward;
            return this;
        }

        public Food build(){
            return new Food(this);
        }
    }

}
