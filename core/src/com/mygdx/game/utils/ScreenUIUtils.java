package com.mygdx.game.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.enums.DifficultyLevel;

public class ScreenUIUtils {

    Game game;
    SpriteBatch batch;
    Viewport viewport;
    Camera camera;

    public ScreenUIUtils(Game game, SpriteBatch batch, Viewport viewport, Camera camera){
        this.game = game;
        this.batch = batch;
        this.viewport = viewport;
        this.camera = camera;
    }

    public void updateViewport(Viewport v){
        viewport = v;
    }

    public void createScreenChangingButton(Rectangle button, Texture hoveredButtonTexture, Texture unHoveredButtonTexture, Screen newScreen){
        Vector3 clickPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        if(button.contains(new Vector2(clickPos.x, clickPos.y))) {
            batch.draw(hoveredButtonTexture, button.x, button.y, button.width, button.height);
            if (Gdx.input.isTouched()) {
                game.setScreen(newScreen);
            }
        } else {
            batch.draw(unHoveredButtonTexture, button.x, button.y, button.width, button.height);
        }
    }

    public DifficultyLevel createDifficultyButton(Rectangle button, Texture hoveredButtonTexture, Texture unHoveredButtonTexture, DifficultyLevel buttonDifficulty, DifficultyLevel currentDifficulty){
        Vector3 clickPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        if(button.contains(new Vector2(clickPos.x, clickPos.y))) {
            batch.draw(hoveredButtonTexture, button.x, button.y);
            if (Gdx.input.isTouched() && currentDifficulty!= buttonDifficulty) {
                System.out.println(buttonDifficulty);
                return buttonDifficulty;
            }
        }else{
            batch.draw(unHoveredButtonTexture, button.x, button.y);
        }
        return currentDifficulty;
    }
}
