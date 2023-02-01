package com.mygdx.game.enums;

/**
 * The enum Difficulty level that controls how hard the game will be.
 */
public enum DifficultyLevel {
    /**
     * Easy difficulty level.
     * 5 Customers served are needed to beat this difficulty
     * Stations take 1x as long to finish cooking
     */
    EASY(5, 1),
    /**
     * Medium difficulty level.
     * 10 Customers served are needed to beat this difficulty
     * Stations take 2x as long to finish cooking
     */
    MEDIUM(10, 2),
    /**
     * Hard difficulty level.
     * 15 Customers served are needed to beat this difficulty
     * Stations take 3x as long to finish cooking
     */
    HARD(15,3);

    /**
     * The target of customers to be served.
     */
    int customerTarget;
    /**
     * The Time multiplier for how long cooking stations take.
     */
    int timeMultiplier;

    /**
     * Get the target of customers to be served.
     *
     * @return Int Customer Target
     */
    public int getCustomerTarget(){
        return customerTarget;
    }

    /**
     * Get the time multiplier for how long cooking stations take.
     *
     * @return Int Time Multiplier
     */
    public int getTimeMultipier(){
        return timeMultiplier;
    }
    DifficultyLevel(int customerTarget, int timeMultiplier){
        this.customerTarget = customerTarget;
        this.timeMultiplier = timeMultiplier;
    }



}