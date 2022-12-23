package com.mygdx.game.enums;

public enum TileMapLayerNames {
    Walls ("Walls"),
    Stations ("Stations"),
    Foods ("Foods");

    private String layerName;

    private TileMapLayerNames(String layerName){
        this.layerName = layerName;
    };

    @Override
    public String toString(){
        return layerName;
    }
}
