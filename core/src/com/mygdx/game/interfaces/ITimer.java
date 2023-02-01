package com.mygdx.game.interfaces;

import com.mygdx.game.Chef;

/**
 * The interface Timer to be used in tandem with the TimerUtils class.
 */
public interface ITimer {
    /**
     * An abstract function that determines what the implementing class does when its timer reaches 0
     *
     * @param chef the chef
     */
    public void finishedTimer(Chef chef);
}
