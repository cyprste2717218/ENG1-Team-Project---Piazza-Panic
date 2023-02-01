package com.mygdx.game.interfaces;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The interface Grid entity - needed for any object represented on the pathfinding grid.
 */
public interface IGridEntity {
    /**
     * Gets previous grid position.
     *
     * @return the previous grid position
     */
//These two functions are used to keep track of where all grid entities are on the grid
    Vector2 getPreviousGridPosition();

    /**
     * Sets current grid position.
     *
     * @param gridPos the grid pos
     */
    void setCurrentGridPosition(Vector2 gridPos);

    /**
     * Gets sprite.
     *
     * @return the sprite
     */
//A function that returns the sprite of the object
    public Sprite getSprite();
}
