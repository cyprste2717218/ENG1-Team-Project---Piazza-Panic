package com.mygdx.game.foodClasses;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Chef;
import com.mygdx.game.interfaces.IInteractable;

public class Food implements IInteractable {

    public String name;
    public Texture foodSprite;
    public boolean isFryable;
    public boolean isChoppable;
    public boolean isBakeable;
    public boolean isFormable;
    public boolean isToastable;
    public int reward;

    public Food(FoodBuilder builder){
        name = builder.name;
        foodSprite = builder.foodSprite;
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
    public void onInteract(Chef chef) {

    }


    public static class FoodBuilder{
        private String name;
        private Texture foodSprite;
        private boolean isFryable = false;
        private boolean isChoppable = false;
        private boolean isBakeable = false;
        private boolean isFormable = false;
        private boolean isToastable = false;
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
