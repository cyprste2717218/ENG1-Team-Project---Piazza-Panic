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
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.stations.Stations;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;


import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


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

		grid = TileMapUtils.tileMapToArray(tiledMap);
		System.out.println(TileMapUtils.tileMapToString(grid));

		spawnChefs();

		Food pizza = new Food(FoodItems.PIZZA);
		pizza.setTileMapPosition(2,2, grid, tiledMap);
		RENDERED_FOODS.add(pizza);

		Food pizza2 = new Food(FoodItems.BURGER);
		pizza2.setTileMapPosition(6,6, grid, tiledMap);
		RENDERED_FOODS.add(pizza2);

		Stations.createAllStations(grid, tiledMap);

		// Customer spawning
		customers = new ArrayList<>();
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
			selectedChef = selectedChef == chefs.length - 1 ? 0 : selectedChef + 1;
			SoundUtils.getChefSwitchSound().play();
		}
	}

	private void spawnCustomer() {
		Texture customerTexture = new Texture("badlogic.jpg");
		Customer customer = new Customer(customerTexture, 50);
		customer.getSprite().setPosition(TileMapUtils.coordToPosition(8, tiledMap), TileMapUtils.coordToPosition(1, tiledMap));
		System.out.println("Customer spawned with order: "+ customer.order.name);
		customers.add(customer);
		customer.onSpawn(grid, tiledMap);
		//	code here for adding the sprite...
		lastCustomerTime = TimeUtils.nanoTime();
		SoundUtils.getCustomerSpawnSound().play();
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
			chef.getSprite().draw(batch);
		}
		for(Food food : RENDERED_FOODS){
			food.getSprite().draw(batch);
		}

		List<Customer> copy = new ArrayList<>(customers);
		for(Customer customer: copy){
			customer.getSprite().draw(batch);
			customer.orderSprite.draw(batch);
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
				spawnCustomer();
				System.out.println("Spawning customer: " + customers.size());
			}
		}
		//TileMapUtils.displayGrid(grid, camera, tiledMap);
	}

	private void updateGridInteractables(Chef[] chefs, List<Food> renderedFoods, List<Customer> customers){
		List<IInteractable> interactables = new ArrayList<>();
		interactables.addAll(Arrays.asList(chefs));
		interactables.addAll(renderedFoods);
		interactables.addAll(customers);

		for(IInteractable interactable : interactables){
			if(interactable.getPreviousGridPosition() != null){
				Node oldNode = grid[(int)interactable.getPreviousGridPosition().x][(int)interactable.getPreviousGridPosition().y];
				oldNode.setInteractable(null);
				oldNode.setNodeType(NodeType.EMPTY);
			}
			Node newNode = grid[TileMapUtils.positionToCoord(interactable.getSprite().getX(), tiledMap)][TileMapUtils.positionToCoord(interactable.getSprite().getY(), tiledMap)];
			if(interactable instanceof Chef) newNode.setNodeType(NodeType.CHEF);
			else if(interactable instanceof Food) newNode.setNodeType(NodeType.FOOD);
			else if(interactable instanceof Customer) newNode.setNodeType(NodeType.CUSTOMER);
			newNode.setInteractable(interactable);
			interactable.setCurrentGridPosition(new Vector2(newNode.getGridX(), newNode.getGridY()));
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
