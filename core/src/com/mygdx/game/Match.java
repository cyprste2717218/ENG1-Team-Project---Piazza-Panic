package com.mygdx.game;

public class Match {
    private static final String[] difficultyOptions = new String[]{"EASY", "MEDIUM", "HARD", "EXPERT"};
    private static final String[] modeOptions = new String[]{"SCENARIO", "ENDLESS"};
    private String mode;
    private String difficulty;
    private int reputationPoints;
    private int customerServed;
    private int maxCustomers;
    private int timer;          // match timer in seconds
    private boolean result;     // true is win, false is loss
    private boolean status;     // true if the match is ongoing, false if the match is ended


    /**
     * Initialising match for "endless" mode
     */
    public Match() {
        mode = modeOptions[1];
        reputationPoints = 3;
        customerServed = 0;
        status = true;
    }

    /**
     * Initialising match for "scenario" mode
     * @param maxCustomers Maximum number of customers allowed in a match.
     * @param timer        The match is over when the timer runs out.
     */
    public Match(int maxCustomers, int timer) {
        mode = modeOptions[0];
        reputationPoints = 3;
        this.customerServed = 0;
        this.timer = timer;
        this.maxCustomers = maxCustomers;

        float score = timer/maxCustomers;
        if (score>=20 && score<25) {
            difficulty = difficultyOptions[0];
        } else if (score>=15) {
            difficulty = difficultyOptions[1];
        } else if (score>=10) {
            difficulty = difficultyOptions[2];
        }else {
            difficulty = difficultyOptions[3];
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
        if (mode.equals(modeOptions[1])) {
            if (reputationPoints == 0) {
                status = false;
                return false;
            }
        }
        if (reputationPoints <= 0 || (timer==0 && customerServed<maxCustomers)) {
            status = false;
            return false;
        } else if (timer==0 && customerServed>=maxCustomers) {
            status = false;
            return true;
        }
        return false;
    }

    public int getReputationPoints(){
        return reputationPoints;
    }

    public boolean getResult(){
        return result;
    }

    public boolean getStatus(){
        return status;
    }
    
    public int getTimer(){
        return timer;
    }
    
    public void setTimer(int timer){
        this.timer = timer;
    }

    /**
     * Scoreboard at the end of the match. Error if the game is not yet finished.
     * @return the necessary information on the scoreboard
     */
    @Override
    public String toString(){
        String output = "Error: The match is not yet finished.";
        result = this.isWin();
        if (!status) {
            if (mode.equals(modeOptions[1])) {
                output = "Time:" + timer +
                        "\nNumber of customers served: " + customerServed +
                        "\nNumber of customers served: " + customerServed;
            } else {
                output = "You " + (result ? "win" : "lose") +
                        "\nDifficulty: " + difficulty +
                        "\nReputation points: " + reputationPoints +
                        "\nNumber of customers served: " + customerServed;
            }
        }
        return output;
    }
}