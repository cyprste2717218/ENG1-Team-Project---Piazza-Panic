package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Chef;
import com.mygdx.game.Customer;
import com.mygdx.game.Node;
import com.mygdx.game.enums.DifficultyLevel;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.screens.MainMenu;
import com.mygdx.game.screens.PiazzaPanic;
import com.mygdx.game.stations.ServingStation;
import com.mygdx.game.stations.Stations;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen implements Screen {
    PiazzaPanic game;
    MainMenu mainMenu;
    private Texture arrowBlack,arrowGreen;
    private Rectangle arrowButton;
    private BitmapFont font;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMap tiledMap;
    private Node[][] grid;
    public static List<Customer> customers;	// array of active customers
    private Long lastCustomerTime;
    private Chef[] chefs;
    private int selectedChef = 0;
    public static List<Food> RENDERED_FOODS;
    public static List<ServingStation> availableServingStations;
    private DifficultyLevel difficultyLevel;


    public static int CUSTOMER_SERVED_COUNTER = 0;
    private BitmapFont CustomerServedText;



    public GameScreen(PiazzaPanic game, MainMenu mainMenu){
        this.game = game;
        this.mainMenu = mainMenu;
        SoundUtils.getBackgroundMusic().play();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        arrowBlack = new Texture("Menu/arrowBlack65.png");
        arrowGreen = new Texture("Menu/arrowGreen65.png");
        arrowButton = new Rectangle(width/9,height-height/7,arrowBlack.getWidth()*1.15F,arrowBlack.getHeight()*1.15F);

        CustomerServedText = new BitmapFont();
        CustomerServedText.setColor(Color.BLACK);

        RENDERED_FOODS = new ArrayList<>();

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



    @Override
    public void show() {

    }

    public void startGame(DifficultyLevel difficultyLevel){
        this.difficultyLevel = difficultyLevel;
        game.setScreen(this);
    }

    public void handleChefs(){
        chefs[selectedChef].move(tiledMap, grid, mainMenu.camera);
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            chefs[selectedChef].interact(grid, tiledMap);
        }
        swapChef();
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
        System.out.println("Customer spawned with order: "+ customer.getOrder().name);
        SoundUtils.getCustomerSpawnSound().play();
    }


    @Override
    public void render(float delta) {

        mainMenu.camera.update();
        Gdx.gl.glClearColor(0.89f,0.97f,0.99f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthogonalTiledMapRenderer.setView(mainMenu.camera);


        updateGridEntities(chefs, RENDERED_FOODS, customers);

        game.batch.begin();

        mainMenu.screenUIUtils.createScreenChangingButton(arrowButton, arrowGreen, arrowBlack, mainMenu);

        CustomerServedText.draw(game.batch, "Customer Served: " + CUSTOMER_SERVED_COUNTER, 35,450);
        for(Chef chef: chefs){chef.getSprite().draw(game.batch);}
        for(Food food : RENDERED_FOODS){game.batch.draw(food.getSprite().getTexture(), food.getSprite().getX() + 96, food.getSprite().getY() + 96);}

        for(Customer customer: new ArrayList<>(customers)){
            customer.getSprite().draw(game.batch);
            customer.moveCustomer();
        }
        Stations.renderAllStations(game.batch);
        game.batch.end();
        orthogonalTiledMapRenderer.render();
        handleChefs();
        //System.out.println(TileMapUtils.tileMapToString(grid));



        //	customer spawning - used a maximum of 5 for number of concurrent customers with 10 seconds delay
        if(customers.size() < 5) {
            if (TimeUtils.nanoTime() - lastCustomerTime > 10000000000L) {
                lastCustomerTime = TimeUtils.nanoTime();
                spawnCustomer();
                System.out.println("Spawning customer: " + customers.size());
            }
        }
    }

    private void updateGridEntities(Chef[] chefs, List<Food> renderedFoods, List<Customer> customers){
        List<IGridEntity> gridEntities = new ArrayList<>();
        gridEntities.addAll(Arrays.asList(chefs));
        gridEntities.addAll(renderedFoods);
        gridEntities.addAll(customers);

        for(IGridEntity gridEntity : gridEntities){
            resetGridEntityProperties(gridEntity);
            setGridEntityProperties(gridEntity);
        }
    }

    private void resetGridEntityProperties(IGridEntity gridEntity){
        if(gridEntity.getPreviousGridPosition() != null){
            Node oldNode = grid[(int)gridEntity.getPreviousGridPosition().x][(int)gridEntity.getPreviousGridPosition().y];
            oldNode.setGridEntity(null);
            oldNode.setInteractable(null);
            oldNode.setNodeType(NodeType.EMPTY);
        }
    }

    private void setGridEntityProperties(IGridEntity gridEntity){
        Node newNode = grid[TileMapUtils.positionToCoord(gridEntity.getSprite().getX(), tiledMap)][TileMapUtils.positionToCoord(gridEntity.getSprite().getY(), tiledMap)];
        if(gridEntity instanceof Chef) newNode.setNodeType(NodeType.CHEF);
        else if(gridEntity instanceof Food) newNode.setNodeType(NodeType.FOOD);
        else if(gridEntity instanceof Customer) newNode.setNodeType(NodeType.CUSTOMER);
        newNode.setGridEntity(gridEntity);
        if(newNode.isInteractable()) newNode.setInteractable((IInteractable) gridEntity);
        gridEntity.setCurrentGridPosition(new Vector2(newNode.getGridX(), newNode.getGridY()));
    }

    @Override
    public void resize(int width, int height) {
        mainMenu.viewport.update(width, height);
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
        for(Chef chef : chefs){
            chef.getSprite().getTexture().dispose();
        }
        tiledMap.dispose();
        orthogonalTiledMapRenderer.dispose();
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public DifficultyLevel getDifficultyLevel(){
        return difficultyLevel;
    }
}
