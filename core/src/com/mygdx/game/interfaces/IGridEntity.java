package com.mygdx.game.interfaces;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public interface IGridEntity {
    //These two functions are used to keep track of where all grid entities are on the grid
    Vector2 getPreviousGridPosition();
    void setCurrentGridPosition(Vector2 gridPos);
    //A function that returns the sprite of the object
    public Sprite getSprite();
}
