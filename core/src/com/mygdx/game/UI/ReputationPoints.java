package com.mygdx.game.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Match;

public class ReputationPoints extends Game {
    private SpriteBatch batch;
    private BitmapFont fontRep;
    private BitmapFont fontWinLose;
    Match match;
    int score;
    private int viewportHeight;
    private OrthographicCamera camera;

    public ReputationPoints(Match match){
        this.match = match;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        fontRep = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"),false);
        fontWinLose = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"),false);
        score = match.getReputationPoints();
        camera = new OrthographicCamera();
        viewportHeight = 480;
        camera.setToOrtho(false,800,viewportHeight);
    }
    @Override
    public void render () {
        ScreenUtils.clear(0.89f,0.97f,0.99f,1);		// rgba(227,247,252,1)
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        fontRep.getData().setScale(1.3F,1.3F);
        GlyphLayout scoreLayout = new GlyphLayout(fontRep,""+score);
        float xScore = (float) (Gdx.graphics.getWidth()-scoreLayout.width-1.25*scoreLayout.height);
        float yScore = (float) (Gdx.graphics.getHeight()-1.25*scoreLayout.height);

        fontWinLose.getData().setScale(2.5F,2.5F);
        GlyphLayout loseLayout = new GlyphLayout(fontWinLose,"YOU LOSE");
        GlyphLayout winLayout = new GlyphLayout(fontWinLose,"YOU WIN");
        float xWinLose = Gdx.graphics.getWidth()/2-loseLayout.width/2;
        float yWinLose = Gdx.graphics.getHeight()/2+loseLayout.height/2;

        if (!match.isWin()) {
            fontWinLose.draw(batch,loseLayout,xWinLose,yWinLose);
        } else if (match.isWin()) {
            fontWinLose.draw(batch,winLayout,xWinLose,yWinLose);
        } else {
            fontRep.draw(batch,scoreLayout,xScore,yScore);
        }

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        fontRep.dispose();
        fontWinLose.dispose();
    }
}
