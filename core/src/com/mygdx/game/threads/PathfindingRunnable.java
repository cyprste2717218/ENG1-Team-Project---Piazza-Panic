package com.mygdx.game.threads;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Node;
import com.mygdx.game.utils.PathfindingUtils;

public class PathfindingRunnable implements Runnable{

    private Node start;
    private Node end;
    private Node[][] walls;
    private Vector2[] gridPath;

    public Vector2[] getGridPath(){
        return gridPath;
    }

    public PathfindingRunnable(Node start, Node end, Node[][] walls){
        this.start = start;
        this.end = end;
        this.walls = walls;
    }

    @Override
    public void run() {
        gridPath = PathfindingUtils.findPath(start, end, walls);
    }
}
