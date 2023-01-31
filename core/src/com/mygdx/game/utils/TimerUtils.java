package com.mygdx.game.utils;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Match;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.screens.PiazzaPanic;

public class TimerUtils {
   /*
    long totalTime;
    long currentTime;
    ITimer timerUser;
    Object thing;

    public TaskTimer(float baseTime, ITimer timerUser, Match match){
        currentTime = TimeUtils.millis();
        totalTime = currentTime + (long)(baseTime * match.getDifficultyLevel().getTimeMultipier() * 1000);
        this.timerUser = timerUser;
    }

    public void runTimer(){
        if(!timerUser.isRunning()){
            currentTime = TimeUtils.millis();
            totalTime = currentTime + (baseTime * PiazzaPanic.difficultyLevel.getTimeMultiplier() * 1000);
        }
        else{
            if(TimeUtils.millis() - 1000 >= currentTime){
                currentTime += 1000;
                if(currentTime == totalTime){
                    timerUser.finishedTimer();


                    //Timer is done
                    //Customer leaves angrily or station is finished
                }
            }
        }
        //Subtract 1 second

    }

    public void resetTimer(){

    }
*/
}
