package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.enums.DifficultyLevel;
import com.mygdx.game.enums.ModeOptions;

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
     * If customer is served within the tolerance time limit, a reputation point earned.
     * If customer's tolerance runs out, a reputation point is lost.
     *
     * @param customer  customer for the current match
     * @param tolerance customer's tolerance in seconds
     * @return 0 if customer is waiting for the food,
     * 1 if customer is served within the time limit,
     * -1 if customer runs out of tolerance waiting
     */
    public int tolerance(Customer customer, int tolerance) {
        if (customer.runTimer(tolerance) == -1) {
            reputationPoints--;
            return -1;
        } else if (customer.beenServed) {
            reputationPoints++;
            customerServed++;
            return 1;
        }
        return 0;
    }

    /**
     * Conditions for the match. "ENDLESS" match ends when reputationPoints reaches 0.
     * @return true boolean if "SCENARIO" match is won, i.e. winning conditions are met
     *         false boolean otherwise
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

    public int getReputationPoints(){
        return reputationPoints;
    }
    public int getMoneyGained() {return moneyGained;}
    public boolean getResult(){
        return result;
    }

    public boolean getStatus(){
        return status;
    }
    
    public long getTimer(){
        return timer;
    }
    
    public void setTimer(long timer){
        this.timer = timer;
    }

    public DifficultyLevel getDifficultyLevel(){return difficulty;}
    public void setDifficultyLevel(DifficultyLevel difficultyLevel){difficulty = difficultyLevel;}

    public int getCustomerServed(){return customerServed;}
    public void incrementCustomerServed(){customerServed++;}
    public void incrementReputationPoints(){reputationPoints++;}
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
