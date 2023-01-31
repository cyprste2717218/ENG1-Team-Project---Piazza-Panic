package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class WinScreen implements Screen {

    GameScreen gameScreen;
    float timeTaken;
    int reputationPoints;
    private BitmapFont font, fontRep;
    Texture arrowBlack, arrowGreen;
    Rectangle arrowButton;

    public WinScreen(GameScreen gameScreen, long startTime){
        this.gameScreen = gameScreen;
        this.reputationPoints = gameScreen.getMatch().getReputationPoints();
        timeTaken = (float)TimeUtils.timeSinceMillis(startTime)/1000f;
        font = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"),false);
        fontRep = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"),false);


        arrowBlack = new Texture("Menu/arrowBlack65.png");
        arrowGreen = new Texture("Menu/arrowGreen65.png");
        arrowButton = new Rectangle(575f,275f,arrowBlack.getWidth() * 1.15F, arrowBlack.getHeight() * 1.15F);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);
        gameScreen.game.batch.begin();
        gameScreen.game.batch.setProjectionMatrix(gameScreen.mainMenu.camera.combined);
        font.getData().setScale(1.3F,1.3F);
        GlyphLayout scoreLayout = new GlyphLayout(font,"Time Taken: " + String.format("%.2f",timeTaken) + "s");

        font.getData().setScale(2.5F,2.5F);
        GlyphLayout loseLayout = new GlyphLayout(font,"YOU LOSE");
        GlyphLayout winLayout = new GlyphLayout(font,"YOU WIN");

        if (reputationPoints <= 0) {
            font.draw(gameScreen.game.batch,loseLayout,442.5f,420f);
        } else {
            font.draw(gameScreen.game.batch,winLayout,442.5f,420f);
        }
        fontRep.draw(gameScreen.game.batch,scoreLayout,900,740);
        gameScreen.mainMenu.screenUIUtils.createScreenChangingButton(arrowButton,arrowGreen,arrowBlack,gameScreen.mainMenu);
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

    }
}
