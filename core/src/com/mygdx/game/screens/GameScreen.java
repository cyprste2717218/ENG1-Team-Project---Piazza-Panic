package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
import com.mygdx.game.utils.TimerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Game screen.
 */
public class GameScreen implements Screen {
    /**
     * The Game.
     */
    PiazzaPanic game;
    private MainMenu mainMenu;
    private Texture arrowBlack,arrowGreen, tutorialBlack, tutorialGreen, recipiesBlack, recipiesGreen;
    private Rectangle arrowButton, tutorialButton, recipiesButton;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMap tiledMap;
    private Node[][] grid;
    private static List<Customer> customers;	// array of active customers
    private Long lastCustomerTime;
    private Chef[] chefs;
    private int selectedChef = 0;
    private static List<Food> RENDERED_FOODS;
    private static List<TimerUtils> TIMER_USERS;
    private static List<ServingStation> availableServingStations;
    private BitmapFont customerSevedFont, reputationPointsFont, moneyGainedFont;
    private boolean canPressBackButton;
    private long buttonPressTime;
    private Match match;
    /**
     * The Customer served text.
     */
    GlyphLayout customerServedText;
    /**
     * The Reputation points text.
     */
    GlyphLayout reputationPointsText;
    /**
     * The Money gained text.
     */
    GlyphLayout moneyGainedText;
    private static boolean canSpawnCustomers;
    /**
     * The Recipe screen.
     */
    RecipeScreen recipeScreen;
    /**
     * The Tutorial screen.
     */
    TutorialScreen tutorialScreen;

    /**
     * Instantiates a new Game screen.
     *
     * @param game     the game
     * @param mainMenu the main menu
     */
    public GameScreen(PiazzaPanic game, MainMenu mainMenu){
        this.game = game;
        this.setMainMenu(mainMenu);
    }

    /**
     * Gets customers.
     *
     * @return the customers
     */
    public static List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Sets customers.
     *
     * @param customers the customers
     */
    public static void setCustomers(List<Customer> customers) {
        GameScreen.customers = customers;
    }

    /**
     * Gets rendered foods.
     *
     * @return the rendered foods
     */
    public static List<Food> getRenderedFoods() {
        return RENDERED_FOODS;
    }

    /**
     * Sets rendered foods.
     *
     * @param renderedFoods the rendered foods
     */
    public static void setRenderedFoods(List<Food> renderedFoods) {
        RENDERED_FOODS = renderedFoods;
    }

    /**
     * Gets timer users.
     *
     * @return the timer users
     */
    public static List<TimerUtils> getTimerUsers() {
        return TIMER_USERS;
    }

    /**
     * Sets timer users.
     *
     * @param timerUsers the timer users
     */
    public static void setTimerUsers(List<TimerUtils> timerUsers) {
        TIMER_USERS = timerUsers;
    }

    /**
     * Gets available serving stations.
     *
     * @return the available serving stations
     */
    public static List<ServingStation> getAvailableServingStations() {
        return availableServingStations;
    }

    /**
     * Sets available serving stations.
     *
     * @param availableServingStations the available serving stations
     */
    public static void setAvailableServingStations(List<ServingStation> availableServingStations) {
        GameScreen.availableServingStations = availableServingStations;
    }

    /**
     * Is can spawn customers boolean.
     *
     * @return the boolean
     */
    public static boolean isCanSpawnCustomers() {
        return canSpawnCustomers;
    }

    /**
     * Sets can spawn customers.
     *
     * @param canSpawnCustomers the can spawn customers
     */
    public static void setCanSpawnCustomers(boolean canSpawnCustomers) {
        GameScreen.canSpawnCustomers = canSpawnCustomers;
    }

    @Override
    public void show() {
        if(getMainMenu().isCreateNewMatch()){
            getMainMenu().setCreateNewMatch(false);
            this.match = new Match(5);
            match.setDifficultyLevel(getMainMenu().getStoredDifficultyLevel());
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
            moneyGainedFont = new BitmapFont(Gdx.files.internal("fonts/pixelFont.fnt"),false);
            moneyGainedFont.getData().setScale(0.5F,0.5F);

            RENDERED_FOODS = new ArrayList<>();
            TIMER_USERS = new ArrayList<>();

            tiledMap = new TmxMapLoader().load("test_kitchen.tmx");
            orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
            availableServingStations = new ArrayList<>();

            grid = TileMapUtils.tileMapToArray(tiledMap);
            System.out.println(TileMapUtils.tileMapToString(grid));

            spawnChefs();

            Stations.clearServingStations();
            Stations.createAllStations(grid, tiledMap);

            // Customer spawning
            setCanSpawnCustomers(true);
            customers = new ArrayList<>();
            lastCustomerTime = TimeUtils.nanoTime();

            recipeScreen = new RecipeScreen(this);
            tutorialScreen = new TutorialScreen(this);
        }
        setCanPressBackButton(false);
        setButtonPressTime(TimeUtils.millis());
        getMainMenu().getCamera().zoom = 0.65f;
        getMainMenu().getCamera().translate(-375,-135);
        game.batch.setProjectionMatrix(getMainMenu().getCamera().combined);
    }

    /**
     * Start game.
     */
    public void startGame(){
        game.setScreen(this);
    }

    /**
     * Handles the chef movements, inputs and switching
     */
    public void handleChefs(){
        chefs[selectedChef].move(tiledMap, grid, getMainMenu().getCamera(), match);
        for(Chef chef : chefs){
            if(chef == chefs[selectedChef]) continue;
            if(!chef.getPathfindingActor().getWorldPath().isEmpty()){
                chef.getPathfindingActor().followPath(chef.getSprite(), 100f, chef.getMovementTextures());
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            chefs[selectedChef].interact(grid, tiledMap, match);
        }
        swapChef();
    }

    private void spawnChefs(){
        chefs = new Chef[2];
        chefs[0] = new Chef(0);
        chefs[1] = new Chef(1);

        chefs[0].setTileMapPosition(3,9,grid,tiledMap);
        chefs[1].setTileMapPosition(7,10,grid,tiledMap);
    }

    private void swapChef(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            selectedChef = selectedChef == chefs.length - 1 ? 0 : selectedChef + 1;
            SoundUtils.getChefSwitchSound().play();
        }
    }

    private void spawnCustomer() {
        if(getAvailableServingStations().isEmpty()) return;
        Texture customerTexture = new Texture("badlogic.jpg");
        Customer customer = new Customer(50);
        customer.getSprite().setPosition(TileMapUtils.coordToPosition(8, tiledMap), TileMapUtils.coordToPosition(1, tiledMap));
        getCustomers().add(customer);
        customer.onSpawn(grid, tiledMap);
        System.out.println("Customer spawned with order: "+ customer.getOrder().getName());
        SoundUtils.getCustomerSpawnSound().play();
    }


    @Override
    public void render(float delta) {
        //System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
        getMainMenu().getCamera().update();
        Gdx.gl.glClearColor(0.89f,0.97f,0.99f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthogonalTiledMapRenderer.setView(getMainMenu().getCamera());
        updateGridEntities(chefs, getRenderedFoods(), getCustomers());

        game.batch.begin();
        game.batch.setProjectionMatrix(getMainMenu().getCamera().combined);
        orthogonalTiledMapRenderer.render();
        game.batch.end();
        game.batch.begin();
        game.batch.setProjectionMatrix(getMainMenu().getCamera().combined);
        getMainMenu().getScreenUIUtils().createScreenChangingButton(tutorialButton, tutorialGreen, tutorialBlack, tutorialScreen);
        getMainMenu().getScreenUIUtils().createScreenChangingButton(recipiesButton, recipiesGreen, recipiesBlack, recipeScreen);

        //Adds a delay before the back button can be pressed so that it won't be triggered while trying to exit other screens
        getMainMenu().getScreenUIUtils().createDisablableScreenChangingButton(arrowButton, arrowGreen, arrowBlack, getMainMenu(), isCanPressBackButton());
        if(!isCanPressBackButton() && TimeUtils.millis() - 250L >= getButtonPressTime()){
            setCanPressBackButton(true);
            System.out.println("Can press button");
        }


        customerServedText = new GlyphLayout(customerSevedFont,"Customers Served: " + match.getCustomerServed());
        reputationPointsText = new GlyphLayout(reputationPointsFont, "Reputation Points: " + match.getReputationPoints());
        moneyGainedText = new GlyphLayout(moneyGainedFont, "Money Gained: $" + match.getMoneyGained());
        customerSevedFont.draw(game.batch, customerServedText, 525, 500);
        reputationPointsFont.draw(game.batch, reputationPointsText, 525, 475);
        moneyGainedFont.draw(game.batch, moneyGainedText, 525,450);
        for(Chef chef: chefs){
            //chef.getSprite().draw(game.batch);
            //chef.drawFoodOnStack(game.batch);
            chef.drawSprites(game.batch);
        }
        for(Food food : getRenderedFoods()){game.batch.draw(food.getSprite().getTexture(), food.getSprite().getX() + 96, food.getSprite().getY() + 96);}

        for(Customer customer: new ArrayList<>(getCustomers())){
            customer.getSprite().draw(game.batch);
            customer.moveCustomer();
        }
        Stations.renderAllStations(game.batch);
        game.batch.end();
        handleChefs();
        //System.out.println(TileMapUtils.tileMapToString(grid));

        //	customer spawning - used a maximum of 5 for number of concurrent customers with 10 seconds delay
        if(getCustomers().size() < 5 && isCanSpawnCustomers()) {
            if (TimeUtils.nanoTime() - lastCustomerTime > 10000000000L) {
                lastCustomerTime = TimeUtils.nanoTime();
                spawnCustomer();
                System.out.println("Spawning customer: " + getCustomers().size());
            }
        }
        else{
            lastCustomerTime = TimeUtils.nanoTime() - 5000000000L;
        }
        if(match.getCustomerServed() == match.getDifficultyLevel().getCustomerTarget()){
            game.setScreen(new WinScreen(this, match.getTimer()));
        }
        runTimers();
    }

    private void runTimers(){
        for(TimerUtils timerUser: getTimerUsers()){
            timerUser.runTimer(chefs[selectedChef]);
            timerUser.renderTimer(game.batch);
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
        getMainMenu().resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        getMainMenu().getCamera().translate(375,135);
        getMainMenu().getCamera().zoom = 1f;
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

    /**
     * Gets match.
     *
     * @return the match
     */
    public Match getMatch() {
        return match;
    }

    /**
     * Gets main menu.
     *
     * @return the main menu
     */
    public MainMenu getMainMenu() {
        return mainMenu;
    }

    /**
     * Sets main menu.
     *
     * @param mainMenu the main menu
     */
    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    /**
     * Is can press back button boolean.
     *
     * @return the boolean
     */
    public boolean isCanPressBackButton() {
        return canPressBackButton;
    }

    /**
     * Sets can press back button.
     *
     * @param canPressBackButton the can press back button
     */
    public void setCanPressBackButton(boolean canPressBackButton) {
        this.canPressBackButton = canPressBackButton;
    }

    /**
     * Gets button press time.
     *
     * @return the button press time
     */
    public long getButtonPressTime() {
        return buttonPressTime;
    }

    /**
     * Sets button press time.
     *
     * @param buttonPressTime the button press time
     */
    public void setButtonPressTime(long buttonPressTime) {
        this.buttonPressTime = buttonPressTime;
    }
}
