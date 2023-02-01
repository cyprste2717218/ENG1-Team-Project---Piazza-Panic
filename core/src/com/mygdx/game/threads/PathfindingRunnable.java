package com.mygdx.game.threads;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Node;
import com.mygdx.game.utils.PathfindingUtils;

/**
 * The seperate thread that pathfinding algorithms are run on
 */
public class PathfindingRunnable implements Runnable{

    //This is a thread that the pathfinding algorithm runs on
    private final Node start;
    private final Node end;
    private final Node[][] grid;
    private Vector2[] gridPath;

    /**
     * Get grid path vector2[ ].
     *
     * @return the grid path vector2[ ]
     */
//This public method is used to return the grid after the pathfinding is done
    public Vector2[] getGridPath(){
        return gridPath;
    }

    //This constructor is used to transfer data to this thread

    /**
     * Instantiates a new Pathfinding runnable.
     *
     * @param start the start node
     * @param end   the end node
     * @param grid  the grid being pathfinding on
     */
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
