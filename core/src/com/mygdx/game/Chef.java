package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.FoodClasses.Food;

import java.util.Stack;

public class Chef {
    private final int chefSize = 16;
    public Sprite chefSprite;
    private Stack<Food> foodStack;

    public Chef(Texture chefTexture, int spawnPosX, int spawnPosY){
        this.chefSprite = new Sprite(chefTexture, spawnPosX, spawnPosY, chefSize, chefSize);
        foodStack = new Stack<>();
    }

    public void move(){
        final float speed = 100;
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            chefSprite.translateY(speed * Gdx.graphics.getDeltaTime());
            chefSprite.setRotation(0);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            chefSprite.translateY(-speed * Gdx.graphics.getDeltaTime());
            chefSprite.setRotation(180);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            chefSprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            chefSprite.setRotation(270);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            chefSprite.translateX(speed * Gdx.graphics.getDeltaTime());
            chefSprite.setRotation(90);
        }
    }

    public void interact(){
        //Get facing direction
        //Get tile in front of Chef
        //Check for what is in that tile
        //Perform action
    }

}
