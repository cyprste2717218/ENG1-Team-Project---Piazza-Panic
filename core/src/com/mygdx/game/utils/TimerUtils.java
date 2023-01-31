package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.screens.GameScreen;

public class TimerUtils {
    long totalTime;
    long currentTime;
    ITimer timerUser;
    Match match;
    float startTime;
    boolean isRunning;
    float currentTimeSeconds;
    Sprite sprite;

    public TimerUtils(float startTime, ITimer timerUser, Match match, Sprite sprite){
        currentTime = TimeUtils.millis();
        this.match = match;
        totalTime = currentTime + (long)(startTime * match.getDifficultyLevel().getTimeMultipier() * 1000);
        this.startTime = startTime;
        this.timerUser = timerUser;
        currentTimeSeconds = startTime;
        isRunning = false;
        GameScreen.TIMER_USERS.add(this);
        this.sprite = sprite;
    }

    public boolean getIsRunning(){
        return isRunning;
    }

    public void setIsRunning(boolean running){
        isRunning = running;
    }

    public void runTimer(Chef chef){
        if(!isRunning){
            //Keeps the time paused
            currentTime = TimeUtils.millis();
            totalTime = currentTime + (long)(currentTimeSeconds * match.getDifficultyLevel().getTimeMultipier() * 1000);
        }
        else{
            if(TimeUtils.millis() - 1000 >= currentTime){
                currentTime += 1000;
                currentTimeSeconds--;
                System.out.println(currentTimeSeconds);
                if(currentTimeSeconds == 0){
                    System.out.println("Timer Finished");
                    timerUser.finishedTimer(chef);
                    setIsRunning(false);
                }
            }
        }
    }

    public void renderTimer(SpriteBatch batch){
        if(isRunning){
            batch.begin();
            batch.draw(new Texture("grey_pixel.png"),sprite.getX() + 120,sprite.getY() + 145,18,4);
            batch.draw(new Texture("red_pixel.png"),sprite.getX()+1 + 120,sprite.getY()+1 + 145,16*(currentTimeSeconds/startTime),2);
            batch.end();
        }
    }

}
