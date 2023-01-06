package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Menu extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture settingBlackImage, settingGreenImage, playRedImage, playGreenImage;
	private Rectangle setting, play;
	int viewportHeight;
	private OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		settingBlackImage = new Texture("Menu/settingBlack5.png");
		settingGreenImage = new Texture("Menu/settingGreen5.png");
		setting = new Rectangle(680,375,settingBlackImage.getWidth(), settingBlackImage.getHeight());
		playRedImage = new Texture("Menu/playRed80.png");
		playGreenImage = new Texture("Menu/playGreen80.png");
		play = new Rectangle(800/2-playRedImage.getWidth()/2, 480/2-playRedImage.getHeight()/2, playRedImage.getWidth(), playRedImage.getHeight());

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
		if (Gdx.input.getX()>setting.x && Gdx.input.getX()<setting.x+setting.width && Gdx.input.getY()<viewportHeight-setting.y && Gdx.input.getY()>viewportHeight-setting.y-setting.height) {
			batch.draw(settingGreenImage, setting.x, setting.y);
		} else {
			batch.draw(settingBlackImage, setting.x, setting.y);
		}
		if (Gdx.input.getX()>play.x && Gdx.input.getX()<play.x+play.width && Gdx.input.getY()<viewportHeight-play.y && Gdx.input.getY()>viewportHeight-play.y-play.height) {
			batch.draw(playGreenImage, play.x, play.y);
		} else {
			batch.draw(playRedImage, play.x, play.y);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		settingBlackImage.dispose();
		settingGreenImage.dispose();
		playRedImage.dispose();
		playGreenImage.dispose();
	}
}

//	class variables
//	private BitmapFont playFont;
//	private GlyphLayout play;
//		in create()
//		playFont = new BitmapFont(Gdx.files.internal("fonts/playfont.fnt"));
//		play = new GlyphLayout(playFont,"PLAY");
//		playFont.draw(batch, "PLAY", 400, 240 + playFont.getCapHeight()/2);   in render()