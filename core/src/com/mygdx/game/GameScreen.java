package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    PiazzaPanic game;
    MainMenu mainMenu;
    private Texture arrowBlack,arrowGreen;
    private Rectangle arrowButton;
    private BitmapFont font;
    public GameScreen(PiazzaPanic game, MainMenu mainMenu){
        this.game = game;
        this.mainMenu = mainMenu;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);

        font = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"));
        font.getData().setScale(2.5F,2.5F);
        GlyphLayout layout = new GlyphLayout(font,"In-Game");
        float x = Gdx.graphics.getWidth()/2-layout.width/2;
        float y = Gdx.graphics.getHeight()/2+layout.height/2;
        font.draw(game.batch,layout,x,y);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        arrowBlack = new Texture("Menu/arrowBlack65.png");
        arrowGreen = new Texture("Menu/arrowGreen65.png");
        arrowButton = new Rectangle(width/9,height-height/7,arrowBlack.getWidth()*1.15F,arrowBlack.getHeight()*1.15F);

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
        arrowBlack.dispose();
        arrowGreen.dispose();
        font.dispose();
    }
}
