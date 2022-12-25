package com.mygdx.game.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Chef;
import com.mygdx.game.Node;

public interface IInteractable {
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap);
    public Vector2 getPreviousGridPosition();

    public void setCurrentGridPosition(Vector2 gridPos);
}
