package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Node;
import com.mygdx.game.enums.Facing;
import com.mygdx.game.enums.NodeType;

/**
 * The utils class containing helper functions regarding the tile map.
 */
public class TileMapUtils {

    /**
     * Converts a TiledMap object into a 2d array of nodes
     * Tile map to array node[ ][ ].
     *
     * @param tiledMap the tiled map
     * @return the node [ ] [ ]
     */
//Function that takes the tileMap and converts it to a 2d array showing where the walls are
    public static Node[][] tileMapToArray(TiledMap tiledMap){

        //This applies to both the tileMap and the nodeProperties array
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Walls");

        Node[][] arrMap = new Node[layer.getWidth()][layer.getHeight()];

        for (int y = 0; y < layer.getHeight(); y++){
            for (int x = 0; x < layer.getWidth(); x++){
                Cell currentCell = layer.getCell(x,y);
                if(currentCell != null)  arrMap[x][y] = new Node(x,y, NodeType.WALL);
                else arrMap[x][y] = new Node(x,y);
            }
        }
        return arrMap;
    }

    /**
     * A debug class that prints out the current state of the grid as a string
     *
     * @param arrMap the arr map
     * @return the string
     */
//A debug function to draw what the array thinks the map looks like
    public static String tileMapToString(Node[][] arrMap){
        StringBuilder sB = new StringBuilder();
        for (int y = arrMap.length - 1; y >= 0; y--){
            for (int x = 0; x < arrMap.length; x++){
                sB.append(arrMap[x][y].getNodeType().toString());
            }
            if(y != 0){
                sB.append("\n");
            }
        }
        return sB.toString();
    }

    /**
     * Gets the node that the chef is currently facing
     *
     * @param facing      the facing
     * @param grid        the grid
     * @param currentNode the current node
     * @return the node
     */
    public static Node getNodeAtFacing(Facing facing, Node[][] grid, Node currentNode){
        if(!PathfindingUtils.isValidNode(currentNode, grid)) return null;
        boolean valid = false;
        switch(facing){
            case UP:
                valid = PathfindingUtils.isValidNode(currentNode.getGridX(),currentNode.getGridY() + 1, grid);
                return valid ? grid[currentNode.getGridX()][currentNode.getGridY() + 1] : null;
            case DOWN:
                valid = PathfindingUtils.isValidNode(currentNode.getGridX(),currentNode.getGridY() - 1, grid);
                return valid ? grid[currentNode.getGridX()][currentNode.getGridY() - 1] : null;
            case RIGHT:
                valid = PathfindingUtils.isValidNode(currentNode.getGridX() + 1,currentNode.getGridY(), grid);
                return valid ? grid[currentNode.getGridX() + 1][currentNode.getGridY()] : null;
            default:
                valid = PathfindingUtils.isValidNode(currentNode.getGridX() - 1,currentNode.getGridY(), grid);
                return valid ? grid[currentNode.getGridX() - 1][currentNode.getGridY()] : null;
        }
    }

    /**
     * Converts a single axis world co-ordinate to a grid co-ordinate
     *
     * @param spriteCoord the sprite coord
     * @param tiledMap    the tiled map
     * @return the int
     */
//A function to convert a world position to a grid position
    //The + 256 is because the grid is 256 worldCoordinates wide
    //The -4 is to account for the camera offset
    public static int positionToCoord(float spriteCoord, TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        return (int)(spriteCoord + 256)/layer.getTileWidth() - 4;
    }

    /**
     * Converts a single axis grid co-ordinate to a centered world co-ordinate
     *
     * @param coord    the coord
     * @param tiledMap the tiled map
     * @return the float
     */
//A function to convert a grid position to a world position
    //The -240 is because the grid is 256 worldCoordinates wide and a tile is 32 wide
    //32/2 is 16 which is used to centre the coord
    //256-16 = 240
    //The +4 is to account for the camera offset
    public static float coordToPosition(int coord, TiledMap tiledMap){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        return (coord + 4f) * layer.getTileWidth() - 240;
    }

    /**
     * A helper function to check whether there is a collision with a node on the grid at the sprite position
     *
     * @param sprite   the sprite
     * @param tiledMap the tiled map
     * @param arrMap   the grid
     * @return the boolean
     */
//Checks for a collision at the location of a sprite
    public static boolean getCollisionAtSprite(Sprite sprite, TiledMap tiledMap, Node[][] arrMap){
        return getCollisionAtSprite(sprite.getX(), sprite.getY(),tiledMap, arrMap);
    }

    /**
     * A helper function to check whether there is a collision with a node on the grid at the sprite position
     *
     * @param x        the x co-ordinate on the grid
     * @param y        the y co-ordinate on the grid
     * @param tiledMap the tiled map
     * @param arrMap   the grid
     * @return the boolean
     */
    public static boolean getCollisionAtSprite(float x, float y, TiledMap tiledMap, Node[][] arrMap){
        return arrMap[positionToCoord(x,tiledMap)][positionToCoord(y, tiledMap)].isCollidable();
    }
}
