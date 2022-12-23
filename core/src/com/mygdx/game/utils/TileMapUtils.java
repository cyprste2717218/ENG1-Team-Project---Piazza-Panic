package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
// import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Node;
import com.mygdx.game.enums.TileMapLayerNames;

public class TileMapUtils {

    public final static int tileMapSize = 16;

    //Function that takes the tileMap and converts it to a 2d array showing where the walls are
    public static Node[][] tileMapToArray(TiledMap tiledMap){

        Node[][] arrMap = new Node[16][16];
        //This applies to both the tileMap and the nodeProperties array
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Walls");
        for (int y = 0; y < arrMap.length; y++){
            for (int x = 0; x < arrMap.length; x++){
                Cell currentCell = layer.getCell(x,y);
                if(currentCell != null)  arrMap[x][y] = new Node(x,y, true);
                else arrMap[x][y] = new Node(x,y,false);;
            }
        }
        return arrMap;
    }

    //A debug function to draw what the array thinks the map looks like
    public static String tileMapToString(TiledMap tiledMap){
        String output = "";
        Node[][] arrMap = tileMapToArray(tiledMap);
        for (int y = arrMap.length - 1; y >= 0; y--){
            for (int x = 0; x < arrMap.length; x++){
                if(arrMap[x][y].getWall()) output += "X";
                else if(arrMap[x][y].getStation()) output += "S";
                else if(arrMap[x][y].getFood()) output += "F";
                else output += " ";
            }
            output += "\n";
        }
        return  output;
    }
    public static int positionToCoord(float spriteCoord, TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        return (int)(spriteCoord + 256)/layer.getTileWidth() - 4;
    }

    public static float coordToPosition(int coord, TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        return (coord + 4f) * layer.getTileWidth() - 240;
    }

    public static boolean getCollisionAtSprite(Sprite sprite, TiledMap tiledMap, Node[][] arrMap){
        return arrMap[positionToCoord(sprite.getX(),tiledMap)][positionToCoord(sprite.getY(), tiledMap)].getWall()|| arrMap[positionToCoord(sprite.getX(),tiledMap)][positionToCoord(sprite.getY(), tiledMap)].getStation();
    }

    public static boolean getCollisionAtSprite(float x, float y, TiledMap tiledMap, Node[][] arrMap){
        return arrMap[positionToCoord(x - 16,tiledMap)][positionToCoord(y - 16, tiledMap)].getWall() || arrMap[positionToCoord(x - 16,tiledMap)][positionToCoord(y - 16, tiledMap)].getStation();
    }
}
