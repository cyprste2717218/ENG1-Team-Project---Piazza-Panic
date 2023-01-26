package com.mygdx.game.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Chef;
import com.mygdx.game.Customer;
import com.mygdx.game.Node;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.foodClasses.Food;

public class ServingStation extends Station{

    private Customer currentCustomer;

    private Sprite orderSprite;

    public ServingStation(Texture stationTexture){
        super(null, stationTexture);
        currentCustomer = null;
        PiazzaPanic.availableServingStations.add(this);
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
        orderSprite.setPosition(getSprite().getX(), getSprite().getY());
    }

    public Sprite getOrderSprite(){
        return orderSprite;
    }

    @Override
    public void onInteract(Chef chef, Node interactedNode, TiledMap tiledMap, Node[][] grid) {
        super.onInteract(chef, interactedNode, tiledMap, grid);
        if(stock != null){
            replaceFoodInStation(chef);
            return;
        }
        if(chef.foodStack.isEmpty()) return;
        stock = chef.foodStack.pop();
        if(currentCustomer == null) return;
        if(stock.equals(currentCustomer.getOrder())){
            completeCustomerOrder(grid, tiledMap);
        }
    }

    private void replaceFoodInStation(Chef chef){
        if(chef.foodStack.isEmpty()){
            chef.foodStack.push(stock);
            stock = null;
        }
        else{
            Food temp = chef.foodStack.pop();
            chef.foodStack.push(stock);
            stock = temp;
        }
    }

    private void completeCustomerOrder(Node[][] grid, TiledMap tiledMap){
        stock = null;
        PiazzaPanic.CUSTOMER_SERVED_COUNTER++;
        currentCustomer.customerLeave(grid, tiledMap);
        currentCustomer = null;
        orderSprite = null;
        PiazzaPanic.availableServingStations.add(this);
    }

    //A customer will spawn in and pathfind to one of these
    //This station will then display their order
    //You can interact with this station to serve customers
    //If your order is correct, the station will empty and the customer will leave happily
}
