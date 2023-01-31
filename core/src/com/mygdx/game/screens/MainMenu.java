package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.utils.ScreenUIUtils;
import com.mygdx.game.utils.SoundUtils;

public class MainMenu implements Screen {
    PiazzaPanic game;
    SettingScreen settingScreen;
    public GameScreen gameScreen;
    private Texture settingBlackImage, settingGreenImage, playRedImage, playGreenImage;
    private Rectangle setting, play;
    public OrthographicCamera camera;
    public ScreenUIUtils screenUIUtils;
    Viewport viewport;
    public boolean createNewMatch;


    public MainMenu(PiazzaPanic game) {
        this.game = game;

        initialise();
    }
    public void initialise(){
        SoundUtils.getBackgroundMusic().play();
        settingBlackImage = new Texture("Menu/settingBlack5.png");
        settingGreenImage = new Texture("Menu/settingGreen5.png");
        setting = new Rectangle(1105F, 609.375F,settingBlackImage.getWidth()*1.625F, settingBlackImage.getHeight()*1.625F);
        playRedImage = new Texture("Menu/playRed80.png");
        playGreenImage = new Texture("Menu/playGreen80.png");
        play = new Rectangle(Gdx.graphics.getWidth()/2-playRedImage.getWidth()*1.625F/2, Gdx.graphics.getHeight()/2-playRedImage.getHeight()*1.625F/2, playRedImage.getWidth()*1.625F, playRedImage.getHeight()*1.625F);

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);

        gameScreen = new GameScreen(game,this);
        settingScreen = new SettingScreen(game,this);

        screenUIUtils = new ScreenUIUtils(game, game.batch, viewport, camera, this);
        System.out.println("Cam pos x: " + camera.position.x + "    Cam pos y: " + camera.position.y);
    }

    @Override
    public void show() {
        createNewMatch = true;
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);		// rgba(227,247,252,1)
        screenUIUtils.createScreenChangingButton(setting, settingGreenImage, settingBlackImage, settingScreen);
        screenUIUtils.createScreenChangingButton(play, playGreenImage, playRedImage, gameScreen);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        game.batch.dispose();
        settingBlackImage.dispose();
        settingGreenImage.dispose();
        playRedImage.dispose();
        playGreenImage.dispose();
    }
}
