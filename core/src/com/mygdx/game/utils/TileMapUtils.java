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
// import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Node;

public class TileMapUtils {

    public final static int tileMapSize = 16;
    private static final int ppm = 32;

    //Function that takes the tileMap and converts it to a 2d array showing where the walls are
    public static Node[][] tileMapToArray(TiledMap tiledMap){

        Node[][] arrMap = new Node[16][16];
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        for (int y = 0; y < layer.getHeight(); y++){
            for (int x = 0; x < layer.getWidth(); x++){
                Cell currentCell = layer.getCell(x,y);
                if(currentCell != null) arrMap[x][y] = new Node(x,y,true);    //This isn't a scuffed solution at all don't worry about it
                else arrMap[x][y] = new Node(x,y,false);
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
                output += arrMap[x][y].getWall() == true ? "X" : " ";
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

    public static boolean getWallAtSprite(Sprite sprite, TiledMap tiledMap, Node[][] arrMap){
        return arrMap[positionToCoord(sprite.getX(),tiledMap)][positionToCoord(sprite.getY(), tiledMap)].getWall();
    }

    public static boolean getWallAtSprite(float x, float y, TiledMap tiledMap, Node[][] arrMap){

        //System.out.println("(" + x + "," + y+ "):(" +positionToCoord(x,tiledMap) + "," + positionToCoord(y, tiledMap) + ")");
        return arrMap[positionToCoord(x,tiledMap)][positionToCoord(y, tiledMap)].getWall();
    }
}
