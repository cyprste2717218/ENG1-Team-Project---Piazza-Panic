package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TileMapUtils {

    public final static int tileMapSize = 16;
    private static final int ppm = 32;

    //Function that takes the tileMap and converts it to a 2d array showing where the walls are
    public static boolean[][] tileMapToArray(TiledMap tiledMap){

        boolean[][] arrMap = new boolean[16][16];
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        for (int y = 0; y < layer.getHeight(); y++){
            for (int x = 0; x < layer.getWidth(); x++){
                Cell currentCell = layer.getCell(x,y);
                if(currentCell != null) arrMap[x][y] = true;    //This isn't a scuffed solution at all don't worry about it
                else arrMap[x][y] = false;
            }
        }
        return arrMap;
    }

    //A debug function to draw what the array thinks the map looks like
    public static String tileMapToString(TiledMap tiledMap){
        String output = "";
        boolean[][] arrMap = tileMapToArray(tiledMap);
        for (int y = arrMap.length - 1; y >= 0; y--){
            for (int x = 0; x < arrMap.length; x++){
                output += arrMap[x][y] == true ? "X" : " ";
            }
            output += "\n";
        }
        return  output;
    }
    public static int positionToCoord(float spriteCoord, TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        return (int)(spriteCoord + 240)/layer.getTileWidth() - 4;
    }

    public static float coordToPosition(int coord, TiledMap tiledMap, boolean isX){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        return (coord + 4f) * layer.getTileWidth();
    }

    public static boolean getWallAtSprite(Sprite sprite, TiledMap tiledMap, boolean[][] arrMap){
        return arrMap[positionToCoord(sprite.getX(),tiledMap)][positionToCoord(sprite.getY(), tiledMap)];
    }

    public static boolean getWallAtSprite(float x, float y, TiledMap tiledMap, boolean[][] arrMap){

        //System.out.println("(" + x + "," + y+ "):(" +positionToCoord(x,tiledMap) + "," + positionToCoord(y, tiledMap) + ")");
        return arrMap[positionToCoord(x,tiledMap)][positionToCoord(y, tiledMap)];
    }
}
