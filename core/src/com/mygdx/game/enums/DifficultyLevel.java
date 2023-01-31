package com.mygdx.game.enums;

public enum DifficultyLevel {
    EASY(5, 1),
    MEDIUM(10, 2),
    HARD(15,3);

    int CUSTOMER_TARGET;
    int TIME_MULTIPLIER;

    public int getCustomerTarget(){
        return CUSTOMER_TARGET;
    }
    public int getTimeMultipier(){
        return TIME_MULTIPLIER;
    }
    DifficultyLevel(int CUSTOMER_TARGET, int TIME_MULTIPLIER){
        this.CUSTOMER_TARGET = CUSTOMER_TARGET;
        this.TIME_MULTIPLIER = TIME_MULTIPLIER;
    }



}