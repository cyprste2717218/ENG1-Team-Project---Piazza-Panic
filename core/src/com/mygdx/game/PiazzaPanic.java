package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PiazzaPanic extends ApplicationAdapter {
	private SpriteBatch batch;
	private Chef chef;
	private OrthographicCamera camera;
	@Override
	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		Texture chefTexture = new Texture("badlogic.jpg");
		chef = new Chef(chefTexture, 200, 20);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		chef.chefSprite.draw(batch);
		batch.end();
		chef.move();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		chef.chefSprite.getTexture().dispose();
	}
}
