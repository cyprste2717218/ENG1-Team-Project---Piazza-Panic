package com.mygdx.game.interfaces;

import com.mygdx.game.enums.Facing;

public interface IPathfinder {
    //This interface contains getters and setters needed for the followPath() function in PathfindingUtils
    void setPathCounter(int counter);
    int getPathCounter();

    void setFacing(Facing direction);
    Facing getFacing();
}
