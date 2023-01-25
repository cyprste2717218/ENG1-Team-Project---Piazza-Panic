package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.stations.Stations;
import com.mygdx.game.utils.TileMapUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;


import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class PiazzaPanic extends ApplicationAdapter {

	private SpriteBatch batch;
	private Chef chef;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private TiledMap tiledMap;
	private Node[][] grid;
	public static List<Customer> customers;	// array of active customers
	private Long lastCustomerTime;
	private Chef[] chefs;
	private int selectedChef = 0;
	public static List<Food> RENDERED_FOODS;


	public static int CUSTOMER_SERVED_COUNTER = 0;

	public static int CUSTOMER_TARGET_SCORE = 0;

	private BitmapFont CustomerServedText;

	private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

	@Override
	public void create() {

		CustomerServedText = new BitmapFont();
		CustomerServedText.setColor(Color.BLACK);
		RENDERED_FOODS = new ArrayList<>();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 2f, camera.position.z);
		batch = new SpriteBatch();

		tiledMap = new TmxMapLoader().load("test_kitchen.tmx");
		orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		grid = TileMapUtils.tileMapToArray(tiledMap);
		System.out.println(TileMapUtils.tileMapToString(grid));

		spawnChefs();

		Food pizza = FoodItems.PIZZA;
		pizza.setTileMapPosition(2,2, grid, tiledMap);
		RENDERED_FOODS.add(pizza);

		Stations.createAllStations(grid, tiledMap);

		// Customer spawning
		customers = new ArrayList<>();
		spawnCustomer();


	}
	public void setDifficultyLevel(DifficultyLevel level){
		this.difficultyLevel = level;
	}

	public DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}

	private void spawnChefs(){
		chefs = new Chef[2];
		Texture chefTexture = new Texture("chefSprite.png");
		chefs[0] = new Chef(chefTexture);

		Texture chefTexture2 = new Texture("chefSprite.png");
		chefs[1] = new Chef(chefTexture2);
	}

	private void swapChef(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			selectedChef = selectedChef == chefs.length - 1 ? 0 : selectedChef + 1;
		}
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

		updateGridInteractables(chefs, RENDERED_FOODS, customers);

		orthogonalTiledMapRenderer.setView(camera);

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		CustomerServedText.draw(batch,"Customer Served: " + CUSTOMER_SERVED_COUNTER, 35,450);
		for(Chef chef: chefs){
			chef.getChefSprite().draw(batch);
		}
		for(Food food : RENDERED_FOODS){
			food.foodSprite.draw(batch);
		}
		Stations.renderAllStations(batch);
		batch.end();

		orthogonalTiledMapRenderer.render();

		chefs[selectedChef].move(tiledMap, grid, camera);
		if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
			chefs[selectedChef].interact(grid, tiledMap);
		}
		swapChef();
		System.out.println(TileMapUtils.tileMapToString(grid));






		//	customer spawning - used a maximum of 5 for number of concurrent customers with 5 seconds delay
		if(customers.size() < 5) {
			if (TimeUtils.nanoTime() - lastCustomerTime > 5000000000L) {
				spawnCustomer();
				System.out.println("Spawning customer: " + customers.size());
			}
		}
	}

	private void updateGridInteractables(Chef[] chefs, List<Food> renderedFoods, List<Customer> customers){
		for(Chef chef : chefs){
			if(chef.getPreviousGridPosition() != null){
				Node oldNode = grid[(int)chef.getPreviousGridPosition().x][(int)chef.getPreviousGridPosition().y];
				oldNode.setInteractable(null);
				oldNode.setChef(false);
			}
			Node newNode = grid[TileMapUtils.positionToCoord(chef.getChefSprite().getX(), tiledMap)][TileMapUtils.positionToCoord(chef.getChefSprite().getY(), tiledMap)];
			newNode.setChef(true);
			newNode.setInteractable(chef);
			chef.setCurrentGridPosition(new Vector2(newNode.getGridX(), newNode.getGridY()));
		}

		for(Food food : renderedFoods){
			if(food.getPreviousGridPosition() != null) {
				Node oldNode = grid[(int) food.getPreviousGridPosition().x][(int) food.getPreviousGridPosition().y];
				oldNode.setInteractable(null);
				oldNode.setFood(false);
			}
			Node newNode = grid[TileMapUtils.positionToCoord(food.foodSprite.getX(), tiledMap)][TileMapUtils.positionToCoord(food.foodSprite.getY(), tiledMap)];
			newNode.setFood(true);
			newNode.setInteractable(food);
			food.setCurrentGridPosition(new Vector2(newNode.getGridX(), newNode.getGridY()));
		}

		for(Customer customer: customers){
			if(customer.getPreviousGridPosition() != null){
				Node oldNode = grid[(int)customer.getPreviousGridPosition().x][(int)customer.getPreviousGridPosition().y];
				oldNode.setInteractable(null);
				oldNode.setCustomer(false);
			}
			//Node newNode = grid[TileMapUtils.positionToCoord(customer.customerSprite.getX(), tiledMap)][TileMapUtils.positionToCoord(customer.customerSprite.getY(), tiledMap)];
			//newNode.setCustomer(true);
			//newNode.setInteractable(customer);
			//customer.setCurrentGridPosition(new Vector2(newNode.getGridX(), newNode.getGridY()));
		}
	}


	@Override
	public void dispose () {
		batch.dispose();
		for(Chef chef : chefs){
			chef.getChefSprite().getTexture().dispose();
		}
		tiledMap.dispose();
		orthogonalTiledMapRenderer.dispose();
	}

}
