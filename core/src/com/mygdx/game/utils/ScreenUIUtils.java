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
import com.mygdx.game.screens.MainMenu;
import com.mygdx.game.enums.DifficultyLevel;

/**
 * The type Screen ui utils.
 */
public class ScreenUIUtils {

    /**
     * The Game.
     */
    Game game;
    /**
     * The Batch.
     */
    SpriteBatch batch;
    /**
     * The Viewport.
     */
    Viewport viewport;
    /**
     * The Camera.
     */
    Camera camera;
    /**
     * The Main menu.
     */
    MainMenu mainMenu;

    /**
     * Instantiates a new Screen ui utils.
     *
     * @param game     the game
     * @param batch    the batch
     * @param viewport the viewport
     * @param camera   the camera
     * @param mainMenu the main menu
     */
    public ScreenUIUtils(Game game, SpriteBatch batch, Viewport viewport, Camera camera, MainMenu mainMenu){
        this.game = game;
        this.batch = batch;
        this.viewport = viewport;
        this.camera = camera;
        this.mainMenu = mainMenu;
    }

    /**
     * Creates a button that allows you to change to a new screen.
     *
     * @param button                 the button
     * @param hoveredButtonTexture   the hovered button texture
     * @param unHoveredButtonTexture the un hovered button texture
     * @param newScreen              the new screen
     */
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

    /**
     * Creates a button that allows you to change to a new screen that can be disabled and enabled.
     *
     * @param button                 the button
     * @param hoveredButtonTexture   the hovered button texture
     * @param unHoveredButtonTexture the un hovered button texture
     * @param newScreen              the new screen
     * @param canPress               the can press
     */
    public void createDisablableScreenChangingButton(Rectangle button, Texture hoveredButtonTexture, Texture unHoveredButtonTexture, Screen newScreen, boolean canPress){
        Vector3 clickPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        if(button.contains(new Vector2(clickPos.x, clickPos.y))) {
            batch.draw(hoveredButtonTexture, button.x, button.y, button.width, button.height);
            if (Gdx.input.isTouched() && canPress) {
                game.setScreen(newScreen);
            }
        } else {
            batch.draw(unHoveredButtonTexture, button.x, button.y, button.width, button.height);
        }
    }

    /**
     * Creates a button that lets you set the difficulty level
     *
     * @param button                 the button
     * @param hoveredButtonTexture   the hovered button texture
     * @param unHoveredButtonTexture the un hovered button texture
     * @param buttonDifficulty       the button difficulty
     * @param currentDifficulty      the current difficulty
     * @return the difficulty level
     */
    public DifficultyLevel createDifficultyButton(Rectangle button, Texture hoveredButtonTexture, Texture unHoveredButtonTexture, DifficultyLevel buttonDifficulty, DifficultyLevel currentDifficulty){
        Vector3 clickPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        if(button.contains(new Vector2(clickPos.x, clickPos.y))) {
            batch.draw(hoveredButtonTexture, button.x, button.y);
            if (Gdx.input.isTouched() && currentDifficulty!= buttonDifficulty) {
                //game.setScreen(mainMenu.getGameScreen());
                return buttonDifficulty;
            }
        }else if(currentDifficulty == buttonDifficulty){
            batch.draw(hoveredButtonTexture, button.x, button.y);
        }
        else{
            batch.draw(unHoveredButtonTexture, button.x, button.y);
        }
        return currentDifficulty;
    }
}
