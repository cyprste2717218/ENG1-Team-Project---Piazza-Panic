package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import jdk.tools.jmod.Main;

public class SettingScreen implements Screen {
    PiazzaPanic game;
    MainMenu mainMenu;
    DifficultyLevel difficulty;
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

        float easyY = height-easyButton.y;
        if (Gdx.input.getX()>=easyButton.x && Gdx.input.getX()<=easyButton.x+easyButton.width && Gdx.input.getY()<=easyY && Gdx.input.getY()>=easyY-easyButton.height){
            game.batch.draw(easyGreen, easyButton.x, easyButton.y);
            if (Gdx.input.isTouched() && difficulty!=DifficultyLevel.EASY) {
                difficulty = DifficultyLevel.EASY;
                System.out.println(difficulty);
            }
        }else{
            game.batch.draw(easyBlack, easyButton.x, easyButton.y);
        }

        float mediumY = height-mediumButton.y;
        if (Gdx.input.getX()>=mediumButton.x && Gdx.input.getX()<=mediumButton.x+mediumButton.width && Gdx.input.getY()<=mediumY && Gdx.input.getY()>=mediumY-mediumButton.height){
            game.batch.draw(mediumGreen, mediumButton.x, mediumButton.y);
            if (Gdx.input.isTouched() && difficulty!=DifficultyLevel.MEDIUM) {
                difficulty = DifficultyLevel.MEDIUM;
                System.out.println(difficulty);
            }
        }else{
            game.batch.draw(mediumBlack, mediumButton.x, mediumButton.y);
        }

        float hardY = height-hardButton.y;
        if (Gdx.input.getX()>=hardButton.x && Gdx.input.getX()<=hardButton.x+hardButton.width && Gdx.input.getY()<=hardY && Gdx.input.getY()>=hardY-hardButton.height){
            game.batch.draw(hardGreen, hardButton.x, hardButton.y);
            if (Gdx.input.isTouched() && difficulty!=DifficultyLevel.HARD) {
                difficulty = DifficultyLevel.HARD;
                System.out.println(difficulty);
            }
        }else{
            game.batch.draw(hardBlack, hardButton.x, hardButton.y);
        }

        float arrowY = height-arrowButton.y;
        if (Gdx.input.getX()>=arrowButton.x && Gdx.input.getX()<=arrowButton.x+arrowButton.width && Gdx.input.getY()<=arrowY && Gdx.input.getY()>=arrowY-arrowButton.height){
            game.batch.draw(arrowGreen, arrowButton.x, arrowButton.y, arrowButton.width,arrowButton.height);
            if (Gdx.input.isTouched()) {
                game.setScreen(mainMenu);
            }
        }else{
            game.batch.draw(arrowBlack, arrowButton.x, arrowButton.y, arrowButton.width,arrowButton.height);
        }

        game.batch.end();
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
