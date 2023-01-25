package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    PiazzaPanic game;
    private BitmapFont font;
    public GameScreen(PiazzaPanic game){
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"));
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);
        font.getData().setScale(2.5F,2.5F);
        GlyphLayout layout = new GlyphLayout(font,"In-Game");
        float x = Gdx.graphics.getWidth()/2-layout.width/2;
        float y = Gdx.graphics.getHeight()/2+layout.height/2;
        font.draw(game.batch,layout,x,y);
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
        font.dispose();
    }
}
