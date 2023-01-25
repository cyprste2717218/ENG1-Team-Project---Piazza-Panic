package com.mygdx.game;

public enum DifficultyLevel {
    EASY(5),
    MEDIUM(10),
    HARD(15),

    EXPERT(20);
    int CUSTOMER_TARGET;

    public int getCustomerTarget(){
        return CUSTOMER_TARGET;
    }
    DifficultyLevel(int CUSTOMER_TARGET){
        this.CUSTOMER_TARGET = CUSTOMER_TARGET;

    }



}
