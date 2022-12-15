package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.stations.Station;

import java.util.Stack;

public class Chef {

    public enum Facing{
        UP,
        LEFT,
        DOWN,
        RIGHT;
    }

    private Facing facing = Facing.UP;
    private final int chefSize = 256;
    public Sprite chefSprite;
    private Stack<Food> foodStack;

    public Chef(Texture chefTexture){
        this.chefSprite = new Sprite(chefTexture, chefSize, chefSize);
        this.chefSprite.setScale(0.125f);
        foodStack = new Stack<>();
    }

    public void move(){
        final float speed = 100;
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            chefSprite.translateY(speed * Gdx.graphics.getDeltaTime());
            facing = facing.UP;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            chefSprite.translateY(-speed * Gdx.graphics.getDeltaTime());
            facing = facing.DOWN;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            chefSprite.translateX(-speed * Gdx.graphics.getDeltaTime());
            facing = facing.LEFT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            chefSprite.translateX(speed * Gdx.graphics.getDeltaTime());
            facing = facing.RIGHT;
        }
        chefSprite.setRotation(90 * facing.ordinal());
    }

    public void interact(){
        //Get facing direction
        //Get tile in front of Chef
        //Check for what is in that tile
        //Perform action
    }

}
