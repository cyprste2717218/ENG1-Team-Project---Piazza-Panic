package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.enums.DifficultyLevel;
import com.mygdx.game.utils.ScreenUIUtils;
import com.mygdx.game.utils.SoundUtils;

/**
 * The type Main menu.
 */
public class MainMenu implements Screen {
    /**
     * The Game.
     */
    PiazzaPanic game;
    /**
     * The Setting screen.
     */
    SettingScreen settingScreen;
    private GameScreen gameScreen;
    private Texture settingBlackImage, settingGreenImage, playRedImage, playGreenImage;
    private Rectangle setting, play;
    private OrthographicCamera camera;
    private ScreenUIUtils screenUIUtils;
    /**
     * The Viewport.
     */
    Viewport viewport;
    private boolean createNewMatch;
    private DifficultyLevel storedDifficultyLevel = DifficultyLevel.EASY;


    /**
     * Instantiates a new Main menu.
     *
     * @param game the game
     */
    public MainMenu(PiazzaPanic game) {
        this.game = game;

        initialise();
    }

    /**
     * Initialises the main menu screen.
     */
    public void initialise(){
        SoundUtils.getBackgroundMusic().play();
        settingBlackImage = new Texture("Menu/settingBlack5.png");
        settingGreenImage = new Texture("Menu/settingGreen5.png");
        setting = new Rectangle(1105F, 609.375F,settingBlackImage.getWidth()*1.625F, settingBlackImage.getHeight()*1.625F);
        playRedImage = new Texture("Menu/playRed80.png");
        playGreenImage = new Texture("Menu/playGreen80.png");
        play = new Rectangle(Gdx.graphics.getWidth()/2-playRedImage.getWidth()*1.625F/2, Gdx.graphics.getHeight()/2-playRedImage.getHeight()*1.625F/2, playRedImage.getWidth()*1.625F, playRedImage.getHeight()*1.625F);

        setCamera(new OrthographicCamera());
        getCamera().setToOrtho(false);
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), getCamera());
        viewport.apply();
        game.batch.setProjectionMatrix(getCamera().combined);

        setGameScreen(new GameScreen(game,this));
        settingScreen = new SettingScreen(game,this);

        setScreenUIUtils(new ScreenUIUtils(game, game.batch, viewport, getCamera(), this));
        System.out.println("Cam pos x: " + getCamera().position.x + "    Cam pos y: " + getCamera().position.y);
    }

    @Override
    public void show() {
        setCreateNewMatch(true);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.setProjectionMatrix(getCamera().combined);
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);		// rgba(227,247,252,1)
        getScreenUIUtils().createScreenChangingButton(setting, settingGreenImage, settingBlackImage, settingScreen);
        getScreenUIUtils().createScreenChangingButton(play, playGreenImage, playRedImage, getGameScreen());
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

    /**
     * Gets game screen.
     *
     * @return the game screen
     */
    public GameScreen getGameScreen() {
        return gameScreen;
    }

    /**
     * Sets game screen.
     *
     * @param gameScreen the game screen
     */
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Gets camera.
     *
     * @return the camera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Sets camera.
     *
     * @param camera the camera
     */
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Gets screen ui utils.
     *
     * @return the screen ui utils
     */
    public ScreenUIUtils getScreenUIUtils() {
        return screenUIUtils;
    }

    /**
     * Sets screen ui utils.
     *
     * @param screenUIUtils the screen ui utils
     */
    public void setScreenUIUtils(ScreenUIUtils screenUIUtils) {
        this.screenUIUtils = screenUIUtils;
    }

    /**
     * Is create new match boolean.
     *
     * @return the boolean
     */
    public boolean isCreateNewMatch() {
        return createNewMatch;
    }

    /**
     * Sets create new match.
     *
     * @param createNewMatch the create new match
     */
    public void setCreateNewMatch(boolean createNewMatch) {
        this.createNewMatch = createNewMatch;
    }

    /**
     * Gets stored difficulty level.
     *
     * @return the stored difficulty level
     */
    public DifficultyLevel getStoredDifficultyLevel() {
        return storedDifficultyLevel;
    }

    /**
     * Sets stored difficulty level.
     *
     * @param storedDifficultyLevel the stored difficulty level
     */
    public void setStoredDifficultyLevel(DifficultyLevel storedDifficultyLevel) {
        this.storedDifficultyLevel = storedDifficultyLevel;
    }
}
