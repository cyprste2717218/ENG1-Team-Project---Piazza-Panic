package com.mygdx.game.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.Node;


/**
 * The interface Interactable.
 * An interface used by any object in the game that can be interacted with by the chef
 */
public interface IInteractable {
    /**
     * On interact.
     *
     * @param chef           the chef that interacted with the object
     * @param interactedNode the interacted node - the node which is being interacted with
     * @param tiledMap       the tiled map
     * @param grid           the grid
     * @param match          the match
     */
//Performs the interaction operation when it is called
    void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match);
}

