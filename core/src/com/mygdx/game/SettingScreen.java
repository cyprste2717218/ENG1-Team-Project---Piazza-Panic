package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.enums.DifficultyLevel;

public class SettingScreen implements Screen {
    PiazzaPanic game;
    MainMenu mainMenu;
    DifficultyLevel difficulty = DifficultyLevel.EASY;
    private Texture easyBlack,easyGreen,mediumBlack,mediumGreen,hardBlack,hardGreen,arrowBlack,arrowGreen;
    private Rectangle easyButton,mediumButton,hardButton,arrowButton;
    private float width, height;
    public SettingScreen(PiazzaPanic game, MainMenu mainMenu){
        this.game = game;
        this.mainMenu = mainMenu;
        initialise();
    }

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
        difficulty = mainMenu.screenUIUtils.createDifficultyButton(easyButton, easyGreen, easyBlack, DifficultyLevel.EASY, difficulty);
        difficulty = mainMenu.screenUIUtils.createDifficultyButton(mediumButton, mediumGreen, mediumBlack, DifficultyLevel.MEDIUM, difficulty);
        difficulty = mainMenu.screenUIUtils.createDifficultyButton(hardButton, hardGreen, hardBlack, DifficultyLevel.HARD, difficulty);

        mainMenu.screenUIUtils.createScreenChangingButton(arrowButton, arrowGreen, arrowBlack, mainMenu);
        game.batch.end();
    }

    private void createDifficultyButton(){

    }

    @Override
    public void resize(int width, int height) {

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
