package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.enums.DifficultyLevel;
import com.mygdx.game.enums.ModeOptions;

/**
 * The type Match.
 */
public class Match {
    private ModeOptions mode;
    private DifficultyLevel difficulty = DifficultyLevel.EASY;
    private int reputationPoints;
    private int customerServed;
    private int moneyGained;
    private int maxCustomers;
    private long timer;          // match timer in seconds
    private boolean result;     // true is win, false is loss
    private boolean status;     // true if the match is ongoing, false if the match is ended


    /**
     * Initialising match for "endless" mode
     */
    public Match() {
        mode = ModeOptions.ENDLESS;
        reputationPoints = 3;
        customerServed = 0;
        moneyGained = 0;
        maxCustomers = 0;
        status = true;
    }

    /**
     * Initialising match for "scenario" mode
     *
     * @param maxCustomers Maximum number of customers allowed in a match.
     */
    public Match(int maxCustomers) {
        mode = ModeOptions.SCENARIO;
        reputationPoints = 3;
        this.customerServed = 0;
        this.moneyGained = 0;
        this.timer = TimeUtils.millis();
        this.maxCustomers = maxCustomers;

        float score = timer/maxCustomers;
        if (score>=20 && score<25) {
            difficulty = DifficultyLevel.EASY;
        } else if (score>=15) {
            difficulty = DifficultyLevel.MEDIUM;
        } else {
            difficulty = DifficultyLevel.HARD;
        }
        status = true;
    }
    /**
     * Conditions for the match. "ENDLESS" match ends when reputationPoints reaches 0.
     *
     * @return true boolean if "SCENARIO" match is won, i.e. winning conditions are met         false boolean otherwise
     */
    public boolean isWin(){
        if (reputationPoints <= 0 || (timer==0 && customerServed<maxCustomers)) {
            status = false;
        } else if (timer==0 && customerServed>=maxCustomers) {
            status = false;
            return true;
        }
        return false;
    }

    /**
     * Get reputation points int.
     *
     * @return the int
     */
    public int getReputationPoints(){
        return reputationPoints;
    }

    /**
     * Gets money gained.
     *
     * @return the money gained
     */
    public int getMoneyGained() {return moneyGained;}

    /**
     * Get result boolean.
     *
     * @return the boolean
     */
    public boolean getResult(){
        return result;
    }

    /**
     * Get status boolean.
     *
     * @return the boolean
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * Get timer long.
     *
     * @return the long
     */
    public long getTimer(){
        return timer;
    }

    /**
     * Set timer.
     *
     * @param timer the timer
     */
    public void setTimer(long timer){
        this.timer = timer;
    }

    /**
     * Get difficulty level difficulty level.
     *
     * @return the difficulty level
     */
    public DifficultyLevel getDifficultyLevel(){return difficulty;}

    /**
     * Set difficulty level.
     *
     * @param difficultyLevel the difficulty level
     */
    public void setDifficultyLevel(DifficultyLevel difficultyLevel){difficulty = difficultyLevel;}

    /**
     * Get customer served int.
     *
     * @return the int
     */
    public int getCustomerServed(){return customerServed;}

    /**
     * Increment customer served.
     */
    public void incrementCustomerServed(){customerServed++;}

    /**
     * Increment reputation points.
     */
    public void incrementReputationPoints(){reputationPoints++;}

    /**
     * Increment money gained.
     *
     * @param money the money
     */
    public void incrementMoneyGained(int money){moneyGained+=money;}

    /**
     * Scoreboard at the end of the match. Error if the game is not yet finished.
     * @return the necessary information on the scoreboard
     */
    @Override
    public String toString(){
        String output = "Error: The match is not yet finished.";
        result = this.isWin();
        if (!status) {
            if (mode.equals(ModeOptions.ENDLESS)) {
                output = "Time:" + timer +
                        "\nNumber of customers served: " + customerServed +
                        "\nNumber of customers served: " + customerServed;
            } else {
                output = "You " + (result ? "win" : "lose") +
                        "\nDifficulty: " + difficulty +
                        "\nReputation points: " + reputationPoints +
                        "\nMoney Earned: " + moneyGained +
                        "\nNumber of customers served: " + customerServed;
            }
        }
        return output;
    }
}
