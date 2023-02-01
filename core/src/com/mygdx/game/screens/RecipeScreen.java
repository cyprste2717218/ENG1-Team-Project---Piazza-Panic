package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The type Recipe screen.
 */
public class RecipeScreen implements Screen {

    //We need a back button to Piazza Panic
    //We need a visual representation of the recipies

    /**
     * The Game screen.
     */
    GameScreen gameScreen;
    /**
     * The Arrow black.
     */
    Texture arrowBlack, /**
     * The Arrow green.
     */
    arrowGreen;
    /**
     * The Arrow button.
     */
    Rectangle arrowButton;
    /**
     * The Recipies sprite.
     */
    Sprite recipiesSprite;

    /**
     * Instantiates a new Recipe screen.
     *
     * @param gameScreen the game screen
     */
    public RecipeScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        arrowBlack = new Texture("Menu/arrowBlack65.png");
        arrowGreen = new Texture("Menu/arrowGreen65.png");
        arrowButton = new Rectangle(144.4444f,668.5714f,arrowBlack.getWidth()*1.15F,arrowBlack.getHeight()*1.15F);
        recipiesSprite = new Sprite(new Texture("RecipiesTexture.png"));
        recipiesSprite.setScale(0.75f);
        recipiesSprite.setPosition(25, 50);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        gameScreen.game.batch.begin();
        gameScreen.game.batch.setProjectionMatrix(gameScreen.getMainMenu().getCamera().combined);
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);
        gameScreen.getMainMenu().getScreenUIUtils().createScreenChangingButton(arrowButton,arrowGreen,arrowBlack,gameScreen);
        recipiesSprite.draw(gameScreen.game.batch);
        gameScreen.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gameScreen.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        arrowBlack.dispose();
        arrowGreen.dispose();
    }
}
