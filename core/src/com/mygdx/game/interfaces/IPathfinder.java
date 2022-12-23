package com.mygdx.game.interfaces;

import com.mygdx.game.enums.Facing;

public interface IPathfinder {
    //This interface contains getters and setters needed for the followPath() function in PathfindingUtils
    public void setPathCounter(int counter);
    public int getPathCounter();

    public void setFacing(Facing direction);
    public Facing getFacing();
}
