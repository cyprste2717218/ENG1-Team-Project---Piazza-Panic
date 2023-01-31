package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.*;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.utils.SoundUtils;

public class ServingStation extends Station{

    private Customer currentCustomer;
    private Sprite orderSprite;

    public ServingStation(Texture stationTexture){
        super(null, stationTexture);
        currentCustomer = null;
        GameScreen.availableServingStations.add(this);
        orderSprite = null;
        Stations.servingStations.add(this);
    }

    public Customer getCurrentCustomer(){
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer customer){
        currentCustomer = customer;
        Texture orderTexture = customer.getOrder().getSprite().getTexture();
        orderSprite = new Sprite(orderTexture, STATION_SIZE, STATION_SIZE);
        orderSprite.setScale(0.0625f);
        orderSprite.setPosition(getSprite().getX(), getSprite().getY() + 7);
    }

    public void clearStation(){
        currentCustomer = null;
        orderSprite = null;
        if(!GameScreen.availableServingStations.contains(this)){
            GameScreen.availableServingStations.add(this);
        }
        if(!Stations.servingStations.contains(this)){
            Stations.servingStations.add(this);
        }
    }

    public Sprite getOrderSprite(){
        return orderSprite;
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid, Match match) {
        super.onInteract(chef, interactedNode, tiledMap, grid, match);
        if(currentCustomer == null || chef.foodStack.isEmpty()){
            SoundUtils.getFailureSound().play();
            return;
        }
        if(currentCustomer.getOrder().equals(chef.foodStack.peek())){
            completeCustomerOrder(chef, grid, tiledMap, match);
        }
        else SoundUtils.getFailureSound().play();
    }

    private void completeCustomerOrder(Chef chef, Node[][] grid, TiledMap tiledMap, Match match){
        match.incrementMoneyGained(chef.foodStack.peek().reward);
        chef.foodStack.pop();
        SoundUtils.getCorrectOrderSound().play();
        match.incrementCustomerServed();
        match.incrementReputationPoints();
        currentCustomer.setBeenServed(true);
        currentCustomer.customerLeave(grid, tiledMap);
        currentCustomer = null;
        orderSprite = null;
        GameScreen.availableServingStations.add(this);
        GameScreen.canSpawnCustomers = true;
    }


    //A customer will spawn in and pathfind to one of these
    //This station will then display their order
    //You can interact with this station to serve customers
    //If your order is correct, the station will empty and the customer will leave happily
}
