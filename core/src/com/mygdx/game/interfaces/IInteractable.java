package com.mygdx.game.interfaces;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;


//An interface used by any object in the game that can be interacted with by the chef
public interface IInteractable {
    //Performs the interaction operation when it is called
    void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid);

    //A function that returns the sprite of the object
    public Sprite getSprite();

    //These two functions are used to keep track of where all interactable objects are on the grid
    Vector2 getPreviousGridPosition();
    void setCurrentGridPosition(Vector2 gridPos);
}
