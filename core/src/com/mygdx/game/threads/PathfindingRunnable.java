package com.mygdx.game.threads;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Node;
import com.mygdx.game.utils.PathfindingUtils;

public class PathfindingRunnable implements Runnable{

    //This is a thread that the pathfinding algorithm runs on
    private final Node start;
    private final Node end;
    private final Node[][] grid;
    private Vector2[] gridPath;

    //This public method is used to return the grid after the pathfinding is done
    public Vector2[] getGridPath(){
        return gridPath;
    }

    //This constructor is used to transfer data to this thread

    public PathfindingRunnable(Node start, Node end, Node[][] grid){
        this.start = start;
        this.end = end;
        this.grid = grid;
    }

    //This is the function that the thread runs when it is called
    @Override
    public void run() {
        gridPath = PathfindingUtils.findPath(start, end, grid);
    }
}
