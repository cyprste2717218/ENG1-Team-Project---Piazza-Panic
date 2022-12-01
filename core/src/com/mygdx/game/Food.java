package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Food {

    private String name;
    private Texture foodSprite;
    private boolean isFryable;
    private boolean isChoppable;
    private boolean isBakeable;

    public Food(FoodBuilder builder){
        name = builder.name;
        foodSprite = builder.foodSprite;
        isFryable = builder.isFryable;
        isBakeable = builder.isBakeable;
        isChoppable = builder.isChoppable;
    }

    public void onInteract(){

    }

    private static class FoodBuilder{
        private String name;
        private Texture foodSprite;
        private boolean isFryable = false;
        private boolean isChoppable = false;
        private boolean isBakeable = false;

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

        public Food build(){
            return new Food(this);
        }
    }

}
