package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.stations.ServingStation;
import com.mygdx.game.stations.Stations;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PiazzaPanic extends Game {
    SpriteBatch batch;

    public PiazzaPanic() {
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenu(this));
    }
}
