package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.stations.Stations;
import com.mygdx.game.utils.TileMapUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;


import javax.swing.text.html.parser.Entity;
import java.util.Random;


public class PiazzaPanic extends ApplicationAdapter {

	private SpriteBatch batch;
	private Chef chef;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private TiledMap tiledMap;
	private Node[][] walls;
	public static Array<Customer> customers;	// array of active customers
	private Long lastCustomerTime;

	public static int CUSTOMER_SERVED_COUNTER = 0;

	public static int CUSTOMER_TARGET_SCORE = 0;

	private BitmapFont CustomerServedText;

	private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

	@Override
	public void create() {

		CustomerServedText = new BitmapFont();
		CustomerServedText.setColor(Color.BLACK);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 2, camera.position.z);
		//System.out.println("Camera pos: (" + camera.position.x + "," + camera.position.y + ")");
		batch = new SpriteBatch();
		Texture chefTexture = new Texture("chefSprite.png");
		chef = new Chef(chefTexture);

		for (Food food : FoodItems.finishedFoods) {
			System.out.println(food.name);
		}
		Stations.setUpStations();

		tiledMap = new TmxMapLoader().load("test_kitchen.tmx");
		orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		walls = TileMapUtils.tileMapToArray(tiledMap);
		//System.out.println(TileMapUtils.tileMapToString(tiledMap));


		// Customer spawning
		customers = new Array<Customer>();
		spawnCustomer();


	}
	public void setDifficultyLevel(DifficultyLevel level){
		this.difficultyLevel = level;
	}

	public DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}

	private void spawnCustomer() {
		Customer custom = new Customer(50);
		//	code here for adding the sprite...
		customers.add(custom);
		lastCustomerTime = TimeUtils.nanoTime();

	}

	@Override
	public void render () {
		camera.update();
		Gdx.gl.glClearColor(1f,1f,1f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		orthogonalTiledMapRenderer.setView(camera);

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		chef.getChefSprite().draw(batch);
		CustomerServedText.draw(batch,"Customer Served: " + CUSTOMER_SERVED_COUNTER, 35,450);
		batch.end();

		orthogonalTiledMapRenderer.render();
		chef.move(tiledMap, walls, camera);

		switch(difficultyLevel){
			case EASY:
				//easy difficulty settings
				//possibly set the CUSTOMER SERVED COUNTER == 5
				break;
			case MEDIUM:
				//medium difficulty settings
				break;
			case HARD:
				//hard difficulty settings
				break;
		}




	//	customer spawning - used a maximum of 5 for number of concurrent customers with 5 seconds delay
		if(customers.size < 5) {
			if (TimeUtils.nanoTime() - lastCustomerTime > 5000000000L) {
				spawnCustomer();
				System.out.println("Spawning customer: " + customers.size);
			}
		}

	}


	@Override
	public void dispose () {
		batch.dispose();
		chef.getChefSprite().getTexture().dispose();
		tiledMap.dispose();
		orthogonalTiledMapRenderer.dispose();
	}

}
