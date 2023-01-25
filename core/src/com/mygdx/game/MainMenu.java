package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu implements Screen {
    PiazzaPanic game;
    private Texture settingBlackImage, settingGreenImage, playRedImage, playGreenImage;
    private Rectangle setting, play;
    int viewportHeight;
    private OrthographicCamera camera;

    public MainMenu(PiazzaPanic game) {
        this.game = game;
        settingBlackImage = new Texture("Menu/settingBlack5.png");
        settingGreenImage = new Texture("Menu/settingGreen5.png");
        setting = new Rectangle(1105F, 609.375F,settingBlackImage.getWidth()*1.625F, settingBlackImage.getHeight()*1.625F);
        playRedImage = new Texture("Menu/playRed80.png");
        playGreenImage = new Texture("Menu/playGreen80.png");
        play = new Rectangle(Gdx.graphics.getWidth()/2-playRedImage.getWidth()*1.625F/2, Gdx.graphics.getHeight()/2-playRedImage.getHeight()*1.625F/2, playRedImage.getWidth()*1.625F, playRedImage.getHeight()*1.625F);

        camera = new OrthographicCamera();
        viewportHeight = 780;
        camera.setToOrtho(false,1300,viewportHeight);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);		// rgba(227,247,252,1)
//        viewport.apply();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        if (Gdx.input.getX()>setting.x && Gdx.input.getX()<setting.x+setting.width && Gdx.input.getY()<viewportHeight-setting.y && Gdx.input.getY()>viewportHeight-setting.y-setting.height) {
            game.batch.draw(settingGreenImage, setting.x, setting.y, setting.width, setting.height);
            if (Gdx.input.isTouched()) {
                game.setScreen(new SettingScreen(game));
            }
        } else {
            game.batch.draw(settingBlackImage, setting.x, setting.y, setting.width, setting.height);
        }
        if (Gdx.input.getX()>play.x && Gdx.input.getX()<play.x+play.width && Gdx.input.getY()<viewportHeight-play.y && Gdx.input.getY()>viewportHeight-play.y-play.height) {
            game.batch.draw(playGreenImage, play.x, play.y, play.width, play.height);
            if (Gdx.input.isTouched()) {
                game.setScreen(new GameScreen(game));
            }
        } else {
            game.batch.draw(playRedImage, play.x, play.y, play.width, play.height);
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
        settingBlackImage.dispose();
        settingGreenImage.dispose();
        playRedImage.dispose();
        playGreenImage.dispose();
    }
}
