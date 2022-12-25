package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.enums.Facing;
import com.mygdx.game.Node;
import com.mygdx.game.interfaces.IPathfinder;
import java.util.*;

public class PathfindingUtils {
    private PathfindingUtils(){}

    //Pathfinds between two grid co-ordinates
    public static Vector2[] findPath(Node start, Node end, Node[][] grid){

        if(!isValidNode(end.getGridX(), end.getGridY(), grid)) return new Vector2[0];
        if(start == end) return new Vector2[] {new Vector2(start.getGridX(), start.getGridY())};
        if(end.getWall()) return new Vector2[0];
        if(end.isInteractable()) end = findBestInteractingNode(start, end, grid);
        clearParents(grid);

        PriorityQueue<Node> openList = new PriorityQueue<>();
        List<Node> closedList = new ArrayList<>();

        openList.add(start);
        while(!openList.isEmpty()){
            Node current = openList.poll();
            Node[] neighbours = getNeighbours(current, grid);
            for(Node n : neighbours){
                //Check if we have reached the end node
                if(n == end) {
                    n.setParent(current);
                    return backTrackPath(n);
                }
                else if (!closedList.contains(n) && !n.isCollidable()){
                    checkNeighbour(current, n, end, openList, closedList);
                }
            }
            closedList.add(current);
        }
        //Path not found
        return new Vector2[0];
    }

    //Finds the node next to an interactable node that should be pathfound to
    //It finds the most extreme direction of the shortest node to path to
    public static Node findBestInteractingNode(Node start, Node end, Node[][] grid){
        double smallestDistance = 10000;
        Node bestNode = null;

        int[] xMod = {1, -1, 0, 0};
        int[] yMod = {0, 0, -1, 1};

        for(int i = 0; i < xMod.length; i++){
            if(!isValidNode(end.getGridX() + xMod[i],end.getGridY() + yMod[i], grid)) continue;
            Node current = grid[end.getGridX() + xMod[i]][end.getGridY() + yMod[i]];
            if(current.isCollidable()) continue;

            float biggestCurrentDistance = Math.max(Math.abs(start.getGridX() - current.getGridX()), Math.abs(start.getGridY() - current.getGridY()));

            if(biggestCurrentDistance < smallestDistance){
                bestNode = current;
                smallestDistance = biggestCurrentDistance;
            }

        }
        return bestNode;
    }

    //Because all interactable objects path to one space before them, this function calculates the correct direction for the sprite to face to interact with the object on a neighbouring node
    public static Facing calculateFinalFacing(Node penultimate, Node end){
        if(end.getGridY() > penultimate.getGridY()) return Facing.UP;
        if(end.getGridY() < penultimate.getGridY()) return Facing.DOWN;
        if(end.getGridX() < penultimate.getGridX()) return Facing.LEFT;
        return Facing.RIGHT;
    }

    //Determines whether a neighbour should be in the open or closed list
    private static void checkNeighbour(Node current, Node n, Node end, PriorityQueue<Node> openList, List<Node> closedList){
        //Calculate g,h and f for n
        float g = calculateMoveCost(current);
        float h = calculateHeuristic(n, end);
        float f = g + h;
        if(openList.contains(n) && n.getF() < f || closedList.contains(n) && n.getF() < f) return;
        n.setG(g);
        n.setH(h);
        n.setParent(current);
        openList.add(n);
    }

    //Converts a path in grid co-ordinates to world co-ordinates
    public static List<Vector2> convertGridPathToWorld(Vector2[] path, TiledMap tiledMap){
        List<Vector2> worldPath = new ArrayList<>();
        for (Vector2 coordinate: path){
            //The 16s are added to centre the sprite
            float worldX = TileMapUtils.coordToPosition((int)coordinate.x, tiledMap);
            float worldY = TileMapUtils.coordToPosition((int)coordinate.y, tiledMap);
            worldPath.add(new Vector2(worldX, worldY));
        }
        return worldPath;
    }

    //Resets the parents of every Node
    private static void clearParents(Node[][] grid){
        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid.length; y++){
                grid[x][y].setParent(null);
            }
        }
    }

    //Checks the node is on the grid
    public static boolean isValidNode(int gridX, int gridY, Node[][] grid){
        return gridY < grid.length && gridY >= 0 && gridX >= 0 && gridX < grid.length;
    }

    //Gets the neighbouring nodes of the current Node
    private static Node[] getNeighbours(Node current, Node[][] grid){
        int[] xMod = {1,-1,0,0};
        int[] yMod = {0,0,1,-1};
        List<Node> neighbourList = new ArrayList<>();

        for(int i = 0; i < xMod.length; i++){
            if(isValidNode(current.getGridX() + xMod[i], current.getGridY() + yMod[i], grid))
                neighbourList.add(grid[current.getGridX() + xMod[i]][current.getGridY() + yMod[i]]);
        }
        return neighbourList.toArray(new Node[0]);
    }

    private static float calculateHeuristic(Node current, Node end){
        return (float)Math.abs(end.getGridX() - current.getGridX()) + (float)Math.abs(end.getGridY() - current.getGridY());
    }

    private static float calculateMoveCost(Node current){
        return 1 + current.getG();
    }

    //Backtracks through the parents to get the complete path
    private static Vector2[] backTrackPath(Node end){
        List<Vector2> path = new ArrayList<>();
        Node current = end;
        while(current.getParent() != null){
            path.add(new Vector2(current.getGridX(), current.getGridY()));
            if(current.getParent() == null) break;
            current = current.getParent();
        }
        Collections.reverse(path);
        return path.toArray(new Vector2[0]);
    }

    //Makes the sprite follow the path
    public static void followPath(Sprite sprite, List<Vector2> path, float speed, IPathfinder pathfinder){

        if(pathfinder.getPathCounter() >= path.size()) return;

        int pointBuffer = 2;

        //The direction from point a to point b = atan(bY-aY,bX-aX)
        float angle = (float) Math.atan2(path.get(pathfinder.getPathCounter()).y - sprite.getY(), path.get(pathfinder.getPathCounter()).x - sprite.getX());
        Vector2 movementDir = new Vector2((float)Math.cos(angle) * speed, (float)Math.sin(angle) * speed);
        setPathfinderFacing(movementDir, pathfinder);
        sprite.setPosition(sprite.getX() + movementDir.x * Gdx.graphics.getDeltaTime(), sprite.getY() + movementDir.y * Gdx.graphics.getDeltaTime());

        if(Math.abs(path.get(pathfinder.getPathCounter()).x - sprite.getX()) <= pointBuffer && Math.abs(path.get(pathfinder.getPathCounter()).y - sprite.getY()) <= pointBuffer){
            pathfinder.setPathCounter(pathfinder.getPathCounter() + 1);
        }
    }

    //This function controls the direction the sprite is facing during its pathfinding
    private static void setPathfinderFacing(Vector2 movementDir, IPathfinder pathfinder){
        //check which movement direction is the largest and face that way
        if(Math.abs(movementDir.x) > Math.abs(movementDir.y)){
            if(movementDir.x > 0) pathfinder.setFacing(Facing.RIGHT);
            else pathfinder.setFacing(Facing.LEFT);
        }
        else{
            if(movementDir.y > 0) pathfinder.setFacing(Facing.UP);
            else pathfinder.setFacing(Facing.DOWN);
        }
    }

    //This function draws a line along the path to provide a visual indicator
    public static void drawPath(List<Vector2> path, Camera camera, Sprite sprite, IPathfinder pathfinder){
        if(path.isEmpty()) return;
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for(int i = pathfinder.getPathCounter(); i < path.size() - 1; i++){

            if(i == 0){
                shapeRenderer.line(modifyVectorForDrawing(sprite.getX(),sprite.getY()),
                    modifyVectorForDrawing(path.get(0)));
            }
            shapeRenderer.line(modifyVectorForDrawing(path.get(i)),
                    modifyVectorForDrawing(path.get(i + 1)));
        }
        shapeRenderer.end();
        shapeRenderer.dispose();
    }

    //Helper methods to convert world vectors into vectors to be drawn on the camera
    private static Vector2 modifyVectorForDrawing(float inputX, float inputY){
        return modifyVectorForDrawing(new Vector2(inputX, inputY));
    }

    private static Vector2 modifyVectorForDrawing(Vector2 inputVector){
        final float mid = (float)256/2;
        Vector2 midpoint = new Vector2(mid, mid);
        return new Vector2(inputVector.x + midpoint.x, inputVector.y + midpoint.y);
    }
}
