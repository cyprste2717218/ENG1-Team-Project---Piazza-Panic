package com.mygdx.game.FoodClasses;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.interfaces.IInteractable;

public class Food implements IInteractable {

    public String name;
    public Texture foodSprite;
    public boolean isFryable;
    public boolean isChoppable;
    public boolean isBakeable;
    public int reward;

    public Food(FoodBuilder builder){
        name = builder.name;
        foodSprite = builder.foodSprite;
        isFryable = builder.isFryable;
        isBakeable = builder.isBakeable;
        isChoppable = builder.isChoppable;
        reward = builder.reward;

        if(reward > 0){
            FoodItems.finishedFoods.add(this);
        }
    }

    @Override
    public void onInteract() {

    }


    public static class FoodBuilder{
        private String name;
        private Texture foodSprite;
        private boolean isFryable = false;
        private boolean isChoppable = false;
        private boolean isBakeable = false;

        private int reward = 0;

        public FoodBuilder(String name, Texture foodSprite){
            this.name = name;
            this.foodSprite = foodSprite;
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

        public FoodBuilder setReward(int reward){
            this.reward = reward;
            return this;
        }


        public Food build(){
            return new Food(this);
        }
    }

}
