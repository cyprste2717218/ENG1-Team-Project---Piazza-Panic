package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.stations.ServingStation;
import com.mygdx.game.stations.Stations;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;


import java.util.*;


public class PiazzaPanic extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	private TiledMap tiledMap;
	private Node[][] grid;
	public static List<Customer> customers;	// array of active customers
	private Long lastCustomerTime;
	private Chef[] chefs;
	private int selectedChef = 0;
	public static List<Food> RENDERED_FOODS;
	public static List<ServingStation> availableServingStations;
	public static String gameMode = "Easy";
	private static float difficultyMultiplier;
	public static int CUSTOMER_SERVED_COUNTER = 0;
	private BitmapFont CustomerServedText;

	@Override
	public void create() {
		SoundUtils.getBackgroundMusic().play();
		CustomerServedText = new BitmapFont();
		CustomerServedText.setColor(Color.BLACK);
		RENDERED_FOODS = new ArrayList<>();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 2f, camera.position.z);
		batch = new SpriteBatch();

		tiledMap = new TmxMapLoader().load("test_kitchen.tmx");
		orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		availableServingStations = new ArrayList<>();

		grid = TileMapUtils.tileMapToArray(tiledMap);
		System.out.println(TileMapUtils.tileMapToString(grid));

		spawnChefs();

		Food salad = new Food(FoodItems.SALAD);
		salad.setTileMapPosition(2,2, grid, tiledMap);
		RENDERED_FOODS.add(salad);

		Food burger = new Food(FoodItems.BURGER);
		burger.setTileMapPosition(6,6, grid, tiledMap);
		RENDERED_FOODS.add(burger);

		Stations.createAllStations(grid, tiledMap);

		// Customer spawning
		customers = new ArrayList<>();
		lastCustomerTime = TimeUtils.nanoTime();
		spawnCustomer();

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
			chefs[selectedChef].getPathfindingActor().getWorldPath().clear();
			selectedChef = selectedChef == chefs.length - 1 ? 0 : selectedChef + 1;
			SoundUtils.getChefSwitchSound().play();
		}
	}

	private void spawnCustomer() {
		if(availableServingStations.isEmpty()) return;
		Texture customerTexture = new Texture("badlogic.jpg");
		Customer customer = new Customer(customerTexture, 50);
		customer.getSprite().setPosition(TileMapUtils.coordToPosition(8, tiledMap), TileMapUtils.coordToPosition(1, tiledMap));
		customers.add(customer);
		customer.onSpawn(grid, tiledMap);
		customer.runCustomerTimer();
		System.out.println("Customer spawned with order: "+ customer.order.name);
		SoundUtils.getCustomerSpawnSound().play();
		//	code here for adding the sprite...
	}




	@Override
	public void render () {

		gameMode = "Easy"; // will need to have this updated by data passed on from menu when clicking difficulty mode
		camera.update();
		Gdx.gl.glClearColor(1f,1f,1f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateGridEntities(chefs, RENDERED_FOODS, customers);

		orthogonalTiledMapRenderer.setView(camera);

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		CustomerServedText.draw(batch,"Customer Served: " + CUSTOMER_SERVED_COUNTER, 35,450);
		for(Chef chef: chefs){
			chef.getSprite().draw(batch);
		}
		for(Food food : RENDERED_FOODS){
			food.getSprite().draw(batch);
		}

		List<Customer> copy = new ArrayList<>(customers);
		for(Customer customer: copy){
			customer.getSprite().draw(batch);
			customer.moveCustomer();
		}
		Stations.renderAllStations(batch);
		batch.end();

		orthogonalTiledMapRenderer.render();

		chefs[selectedChef].move(tiledMap, grid, camera);
		if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
			chefs[selectedChef].interact(grid, tiledMap);
		}
		swapChef();
		//System.out.println(TileMapUtils.tileMapToString(grid));



		//	customer spawning - used a maximum of 5 for number of concurrent customers with 10 seconds delay
		if(customers.size() < 5) {
			if (TimeUtils.nanoTime() - lastCustomerTime > 10000000000L) {
				lastCustomerTime = TimeUtils.nanoTime();
				spawnCustomer();
				System.out.println("Spawning customer: " + customers.size());
			}
		}
		//TileMapUtils.displayGrid(grid, camera, tiledMap);
	}

	private void updateGridEntities(Chef[] chefs, List<Food> renderedFoods, List<Customer> customers){
		List<IGridEntity> gridEntities = new ArrayList<>();
		gridEntities.addAll(Arrays.asList(chefs));
		gridEntities.addAll(renderedFoods);
		gridEntities.addAll(customers);

		for(IGridEntity gridEntity : gridEntities){
			if(gridEntity.getPreviousGridPosition() != null){
				Node oldNode = grid[(int)gridEntity.getPreviousGridPosition().x][(int)gridEntity.getPreviousGridPosition().y];
				oldNode.setGridEntity(null);
				oldNode.setInteractable(null);
				oldNode.setNodeType(NodeType.EMPTY);
			}
			Node newNode = grid[TileMapUtils.positionToCoord(gridEntity.getSprite().getX(), tiledMap)][TileMapUtils.positionToCoord(gridEntity.getSprite().getY(), tiledMap)];
			if(gridEntity instanceof Chef) newNode.setNodeType(NodeType.CHEF);
			else if(gridEntity instanceof Food) newNode.setNodeType(NodeType.FOOD);
			else if(gridEntity instanceof Customer) newNode.setNodeType(NodeType.CUSTOMER);
			newNode.setGridEntity(gridEntity);
			if(newNode.isInteractable()){
				newNode.setInteractable((IInteractable) gridEntity);
				//System.out.println("Set Interactable of" + gridEntity.getClass());
			}
			gridEntity.setCurrentGridPosition(new Vector2(newNode.getGridX(), newNode.getGridY()));
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		for(Chef chef : chefs){
			chef.getSprite().getTexture().dispose();
		}
		tiledMap.dispose();
		orthogonalTiledMapRenderer.dispose();
	}
}
