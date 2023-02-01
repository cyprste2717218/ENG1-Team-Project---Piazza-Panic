package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.*;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.utils.SoundUtils;

/**
 * The type Serving station - customers sit at these tables and wait to be served.
 */
public class ServingStation extends Station{

    private Customer currentCustomer;
    private Sprite orderSprite;

    /**
     * Instantiates a new Serving station.
     *
     * @param stationTexture the station texture
     */
    public ServingStation(Texture stationTexture){
        super(null, stationTexture);
        currentCustomer = null;
        GameScreen.getAvailableServingStations().add(this);
        orderSprite = null;
        Stations.servingStations.add(this);
    }

    /**
     * Get current customer customer.
     *
     * @return the customer waiting to be served
     */
    public Customer getCurrentCustomer(){
        return currentCustomer;
    }

    /**
     * Set current customer.
     *
     * @param customer the customer
     */
    public void setCurrentCustomer(Customer customer){
        currentCustomer = customer;
        Texture orderTexture = customer.getOrder().getSprite().getTexture();
        orderSprite = new Sprite(orderTexture, STATION_SIZE, STATION_SIZE);
        orderSprite.setScale(0.0625f);
        orderSprite.setPosition(getSprite().getX(), getSprite().getY() + 7);
    }

    /**
     * Clears the station's data.
     */
    public void clearStation(){
        currentCustomer = null;
        orderSprite = null;
        if(!GameScreen.getAvailableServingStations().contains(this)){
            GameScreen.getAvailableServingStations().add(this);
        }
        if(!Stations.servingStations.contains(this)){
            Stations.servingStations.add(this);
        }
    }

    /**
     * Get the sprite of the food item the customer is ordering.
     *
     * @return the sprite
     */
    public Sprite getOrderSprite(){
        return orderSprite;
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        super.onInteract(chef, interactedNode, tiledMap, grid, match);
        if(currentCustomer == null || chef.getFoodStack().isEmpty()){
            SoundUtils.getFailureSound().play();
            return;
        }
        if(currentCustomer.getOrder().equals(chef.getFoodStack().peek())){
            completeCustomerOrder(chef, grid, tiledMap, match);
        }
        else SoundUtils.getFailureSound().play();
    }

    /**
     * When the customer is given the correct order, they will leave and the player's various scores will be incremented
     *
     * @param chef
     * @param grid
     * @param tiledMap
     * @param match
     */
    private void completeCustomerOrder(Chef chef, Node[][] grid, TiledMap tiledMap, Match match){
        match.incrementMoneyGained(chef.getFoodStack().peek().getReward());
        chef.getFoodStack().pop();
        SoundUtils.getCorrectOrderSound().play();
        match.incrementCustomerServed();
        match.incrementReputationPoints();
        currentCustomer.setBeenServed(true);
        currentCustomer.customerLeave(grid, tiledMap);
        currentCustomer = null;
        orderSprite = null;
        GameScreen.getAvailableServingStations().add(this);
        GameScreen.setCanSpawnCustomers(true);
    }


    //A customer will spawn in and pathfind to one of these
    //This station will then display their order
    //You can interact with this station to serve customers
    //If your order is correct, the station will empty and the customer will leave happily
}
