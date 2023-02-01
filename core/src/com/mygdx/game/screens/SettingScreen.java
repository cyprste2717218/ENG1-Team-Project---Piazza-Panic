package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Match;
import com.mygdx.game.enums.DifficultyLevel;

/**
 * The type Setting screen.
 */
public class SettingScreen implements Screen {
    /**
     * The Game.
     */
    PiazzaPanic game;
    /**
     * The Main menu.
     */
    MainMenu mainMenu;
    /**
     * The Difficulty.
     */
    DifficultyLevel difficulty = DifficultyLevel.EASY;
    private Texture easyBlack,easyGreen,mediumBlack,mediumGreen,hardBlack,hardGreen,arrowBlack,arrowGreen;
    private Rectangle easyButton,mediumButton,hardButton,arrowButton;
    private float width, height;

    /**
     * Instantiates a new Setting screen.
     *
     * @param game     the game
     * @param mainMenu the main menu
     */
    public SettingScreen(PiazzaPanic game, MainMenu mainMenu){
        this.game = game;
        this.mainMenu = mainMenu;
        initialise();
    }

    /**
     * Initialises the SettingScreen.
     */
    public void initialise(){
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        easyBlack = new Texture("Menu/easyButtonBlack80.png");
        easyGreen = new Texture("Menu/easyButtonGreen80.png");
        easyButton = new Rectangle(width/2-easyBlack.getWidth()/2, height-(height/4+easyBlack.getHeight()/2), easyBlack.getWidth(), easyBlack.getHeight());

        mediumBlack = new Texture("Menu/mediumButtonBlack80.png");
        mediumGreen = new Texture("Menu/mediumButtonGreen80.png");
        mediumButton = new Rectangle(width/2-mediumBlack.getWidth()/2, height-(height/2+mediumBlack.getHeight()/2), mediumBlack.getWidth(), mediumBlack.getHeight());

        hardBlack = new Texture("Menu/hardButtonBlack80.png");
        hardGreen = new Texture("Menu/hardButtonGreen80.png");
        hardButton = new Rectangle(width/2-hardBlack.getWidth()/2, height-(height*3/4+hardBlack.getHeight()/2), hardBlack.getWidth(), hardBlack.getHeight());

        arrowBlack = new Texture("Menu/arrowBlack65.png");
        arrowGreen = new Texture("Menu/arrowGreen65.png");
        arrowButton = new Rectangle(width/9,height-height/7,arrowBlack.getWidth()*1.15F,arrowBlack.getHeight()*1.15F);
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);
        mainMenu.setStoredDifficultyLevel(mainMenu.getScreenUIUtils().createDifficultyButton(mediumButton, mediumGreen, mediumBlack, DifficultyLevel.MEDIUM, mainMenu.getStoredDifficultyLevel()));
        mainMenu.setStoredDifficultyLevel(mainMenu.getScreenUIUtils().createDifficultyButton(hardButton, hardGreen, hardBlack, DifficultyLevel.HARD, mainMenu.getStoredDifficultyLevel()));
        mainMenu.setStoredDifficultyLevel(mainMenu.getScreenUIUtils().createDifficultyButton(easyButton, easyGreen, easyBlack, DifficultyLevel.EASY, mainMenu.getStoredDifficultyLevel()));
        mainMenu.getScreenUIUtils().createScreenChangingButton(arrowButton, arrowGreen, arrowBlack, mainMenu);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mainMenu.resize(width, height);
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
        easyBlack.dispose();
        easyGreen.dispose();
        mediumBlack.dispose();
        mediumGreen.dispose();
        hardBlack.dispose();
        hardGreen.dispose();
        arrowBlack.dispose();
        arrowGreen.dispose();
    }
}
