package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Menu extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture settingBlackImage, settingGreenImage, playRedImage, playGreenImage,easyButtonImage,mediumButtonImage,hardButtonImage,expertButtonImage;
	private Rectangle setting, play,easyButton,mediumButton,hardButton,expertButton;
	int viewportHeight;
	private OrthographicCamera camera;

	DifficultyLevel difficulty = DifficultyLevel.EASY;

	@Override
	public void create () {
		batch = new SpriteBatch();

		settingBlackImage = new Texture("Menu/settingBlack5.png");
		settingGreenImage = new Texture("Menu/settingGreen5.png");
		easyButtonImage = new Texture("Menu/easyButton.png");
		mediumButtonImage = new Texture("Menu/mediumButton.png");
		hardButtonImage = new Texture("Menu/hardButton.png");
		expertButtonImage = new Texture("Menu/expertButton.png");

		easyButton = new Rectangle(200, 150, easyButtonImage.getWidth(), easyButtonImage.getHeight());
		mediumButton = new Rectangle(400, 150, mediumButtonImage.getWidth(), mediumButtonImage.getHeight());
		hardButton = new Rectangle(600, 150, hardButtonImage.getWidth(), hardButtonImage.getHeight());
		expertButton = new Rectangle(800, 150, expertButtonImage.getWidth(), expertButtonImage.getHeight());



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


		//linking the enum to the buttons
		if (difficulty == DifficultyLevel.EASY) {
			batch.draw(easyButtonImage, easyButton.x, easyButton.y);
		} else if (difficulty == DifficultyLevel.MEDIUM) {
			batch.draw(mediumButtonImage, mediumButton.x, mediumButton.y);
		} else if (difficulty == DifficultyLevel.HARD) {
			batch.draw(hardButtonImage, hardButton.x, hardButton.y);
		} else if (difficulty == DifficultyLevel.EXPERT) {
			batch.draw(expertButtonImage, expertButton.x, expertButton.y);
		}
		//player to click on the difficulty
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if (easyButton.contains(touchPos.x, touchPos.y)) {
				difficulty = DifficultyLevel.EASY;
			} else if (mediumButton.contains(touchPos.x, touchPos.y)) {
				difficulty = DifficultyLevel.MEDIUM;
			} else if (hardButton.contains(touchPos.x, touchPos.y)) {
				difficulty = DifficultyLevel.HARD;
			} else if (expertButton.contains(touchPos.x, touchPos.y)) {
				difficulty = DifficultyLevel.EXPERT;
			}
			System.out.println("Difficulty level: " + difficulty.name());
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
