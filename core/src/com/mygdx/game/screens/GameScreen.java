package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Chef;
import com.mygdx.game.Customer;
import com.mygdx.game.Match;
import com.mygdx.game.Node;
import com.mygdx.game.enums.DifficultyLevel;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import com.mygdx.game.interfaces.IGridEntity;
import com.mygdx.game.interfaces.IInteractable;
import com.mygdx.game.stations.ServingStation;
import com.mygdx.game.stations.Stations;
import com.mygdx.game.utils.SoundUtils;
import com.mygdx.game.utils.TileMapUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen implements Screen {
    PiazzaPanic game;
    public MainMenu mainMenu;
    private Texture arrowBlack,arrowGreen, tutorialBlack, tutorialGreen, recipiesBlack, recipiesGreen;
    private Rectangle arrowButton, tutorialButton, recipiesButton;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMap tiledMap;
    private Node[][] grid;
    public static List<Customer> customers;	// array of active customers
    private Long lastCustomerTime;
    private Chef[] chefs;
    private int selectedChef = 0;
    public static List<Food> RENDERED_FOODS;
    public static List<ServingStation> availableServingStations;
    private BitmapFont customerSevedFont, reputationPointsFont;
    public boolean canPressBackButton;
    public long buttonPressTime;
    private Match match;
    GlyphLayout customerServedText;
    GlyphLayout reputationPointsText;
    public static boolean canSpawnCustomers;

    public GameScreen(PiazzaPanic game, MainMenu mainMenu){
        this.game = game;
        this.mainMenu = mainMenu;
    }

    @Override
    public void show() {
        if(mainMenu.createNewMatch){
            mainMenu.createNewMatch = false;
            this.match = new Match(5);
            selectedChef = 0;

            arrowBlack = new Texture("Menu/arrowBlack65.png");
            arrowGreen = new Texture("Menu/arrowGreen65.png");
            arrowButton = new Rectangle(-106, 400f,arrowBlack.getWidth()*1.15F,arrowBlack.getHeight()*1.15F);

            recipiesBlack = new Texture("Menu/RecipiesBlack.png");
            recipiesGreen = new Texture("Menu/RecipiesGreen.png");
            recipiesButton = new Rectangle(560, 50, recipiesBlack.getWidth() * 1.15f, recipiesBlack.getHeight()*1.15f);

            tutorialBlack = new Texture("Menu/tutorialBlack.png");
            tutorialGreen = new Texture("Menu/tutorialGreen.png");
            tutorialButton = new Rectangle(560, 25, tutorialBlack.getWidth() * 1.15f, tutorialBlack.getHeight()*1.15f);


            customerSevedFont = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"),false);
            customerSevedFont.getData().setScale(0.5F,0.5F);
            reputationPointsFont = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"),false);
            reputationPointsFont.getData().setScale(0.5F,0.5F);

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

            Stations.clearServingStations();
            Stations.createAllStations(grid, tiledMap);

            // Customer spawning
            canSpawnCustomers = true;
            customers = new ArrayList<>();
            lastCustomerTime = TimeUtils.nanoTime();
        }
        canPressBackButton = false;
        buttonPressTime = TimeUtils.millis();
        mainMenu.camera.zoom = 0.65f;
        mainMenu.camera.translate(-375,-135);
        game.batch.setProjectionMatrix(mainMenu.camera.combined);
    }

    public void startGame(DifficultyLevel difficultyLevel){
        match.setDifficultyLevel(difficultyLevel);
        game.setScreen(this);
    }

    public void handleChefs(){
        chefs[selectedChef].move(tiledMap, grid, mainMenu.camera, match);
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            chefs[selectedChef].interact(grid, tiledMap, match);
        }
        swapChef();
    }

    private void spawnChefs(){
        chefs = new Chef[2];
        Texture chefTexture = new Texture("chefSprite.png");
        chefs[0] = new Chef(chefTexture);

        Texture chefTexture2 = new Texture("chefSprite.png");
        chefs[1] = new Chef(chefTexture2);

        chefs[0].setTileMapPosition(3,9,grid,tiledMap);
        chefs[1].setTileMapPosition(7,10,grid,tiledMap);
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
        game.batch.setProjectionMatrix(mainMenu.camera.combined);
        orthogonalTiledMapRenderer.render();
        game.batch.end();
        game.batch.begin();
        game.batch.setProjectionMatrix(mainMenu.camera.combined);
        mainMenu.screenUIUtils.createScreenChangingButton(tutorialButton, tutorialGreen, tutorialBlack, new TutorialScreen(this));
        mainMenu.screenUIUtils.createScreenChangingButton(recipiesButton, recipiesGreen, recipiesBlack, new RecipeScreen(this));

        //Adds a delay before the back button can be pressed so that it won't be triggered while trying to exit other screens
        mainMenu.screenUIUtils.createDisablableScreenChangingButton(arrowButton, arrowGreen, arrowBlack, mainMenu, canPressBackButton);
        if(!canPressBackButton && TimeUtils.millis() - 250L >= buttonPressTime){
            canPressBackButton = true;
            System.out.println("Can press button");
        }


        customerServedText = new GlyphLayout(customerSevedFont,"Customers Served: " + match.getCustomerServed());
        reputationPointsText = new GlyphLayout(reputationPointsFont, "Reputation Points: " + match.getReputationPoints());
        customerSevedFont.draw(game.batch, customerServedText, 525, 500);
        reputationPointsFont.draw(game.batch, reputationPointsText, 525, 475);
        for(Chef chef: chefs){chef.getSprite().draw(game.batch);}
        for(Food food : RENDERED_FOODS){game.batch.draw(food.getSprite().getTexture(), food.getSprite().getX() + 96, food.getSprite().getY() + 96);}

        for(Customer customer: new ArrayList<>(customers)){
            customer.getSprite().draw(game.batch);
            customer.moveCustomer();
        }
        Stations.renderAllStations(game.batch);
        game.batch.end();
        handleChefs();
        //System.out.println(TileMapUtils.tileMapToString(grid));

        //	customer spawning - used a maximum of 5 for number of concurrent customers with 10 seconds delay
        if(customers.size() < 5 && canSpawnCustomers) {
            if (TimeUtils.nanoTime() - lastCustomerTime > 1000000000L) {
                lastCustomerTime = TimeUtils.nanoTime();
                spawnCustomer();
                System.out.println("Spawning customer: " + customers.size());
            }
        }
        if(match.getCustomerServed() == 5){
            game.setScreen(new WinScreen(this, match.getTimer()));
        }
    }

    private void updateGridEntities(Chef[] chefs, List<Food> renderedFoods, List<Customer> customers){
        List<IGridEntity> gridEntities = new ArrayList<>();
        gridEntities.addAll(Arrays.asList(chefs));
        gridEntities.addAll(renderedFoods);
        gridEntities.addAll(customers);

        for(IGridEntity gridEntity : gridEntities){
            if(gridEntity.getPreviousGridPosition() != null){
                if(gridEntity.getPreviousGridPosition().equals(new Vector2(gridEntity.getSprite().getX(), gridEntity.getSprite().getY()))) continue;
            }
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
        mainMenu.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        mainMenu.camera.translate(375,135);
        mainMenu.camera.zoom = 1f;
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        arrowBlack.dispose();
        arrowGreen.dispose();
        for(Chef chef : chefs){
            chef.getSprite().getTexture().dispose();
        }
        tiledMap.dispose();
        orthogonalTiledMapRenderer.dispose();
    }
    public Match getMatch() {
        return match;
    }
}
