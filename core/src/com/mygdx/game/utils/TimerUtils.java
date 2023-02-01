package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Chef;
import com.mygdx.game.Match;
import com.mygdx.game.interfaces.ITimer;
import com.mygdx.game.screens.GameScreen;

/**
 * The type Timer utils - manages countdown timers for objects with the ITimer interface - currently just CookingStation.
 */
public class TimerUtils {
    /**
     * The Total time in milliseconds.
     */
    long totalTime;
    /**
     * The Current time in milliseconds.
     */
    long currentTime;
    /**
     * The class with the interface ITimer.
     */
    ITimer timerUser;
    /**
     * The Match.
     */
    Match match;
    /**
     * The Start time of the timer in seconds.
     */
    float startTime;
    /**
     * Whether or not the timer is paused or not
     */
    boolean isRunning;
    /**
     * The Current time in seconds.
     */
    float currentTimeSeconds;
    /**
     * The Sprite who the timer visualisation is being spawned above.
     */
    Sprite sprite;

    /**
     * Instantiates a new Timer utils.
     * Any TimerObject user is automatically added to the GameScreen.TIMER_USERS list
     *
     * @param startTime the start time
     * @param timerUser the timer user
     * @param match     the match
     * @param sprite    the sprite
     */
    public TimerUtils(float startTime, ITimer timerUser, Match match, Sprite sprite){
        currentTime = TimeUtils.millis();
        this.match = match;
        totalTime = currentTime + (long)(startTime * match.getDifficultyLevel().getTimeMultipier() * 1000);
        this.startTime = startTime * match.getDifficultyLevel().getTimeMultipier();
        this.timerUser = timerUser;
        currentTimeSeconds = this.startTime;
        isRunning = false;
        GameScreen.getTimerUsers().add(this);
        this.sprite = sprite;
    }

    /**
     * Get whether the timer is running
     *
     * @return the boolean
     */
    public boolean getIsRunning(){
        return isRunning;
    }

    /**
     * Set whether the running.
     *
     * @param running the running
     */
    public void setIsRunning(boolean running){
        isRunning = running;
    }

    /**
     * Runs the timer - done in a loop for TimerUtils objects in the GameScreen class.
     * When the timer finishes, the timerUser.finishedTimer() function is run - from the ITimer interface
     *
     * @param chef the chef
     */
    public void runTimer(Chef chef){
        if(!isRunning){
            //Keeps the time paused
            currentTime = TimeUtils.millis();
            totalTime = currentTime + (long)(currentTimeSeconds * match.getDifficultyLevel().getTimeMultipier() * 1000);
        }
        else{
            if(TimeUtils.millis() - 1000 >= currentTime){
                System.out.println(currentTime);
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

    /**
     * Renders a visualisation of the timer.
     *
     * @param batch the sprite batch
     */
    public void renderTimer(SpriteBatch batch){
        if(isRunning){
            batch.begin();
            batch.draw(new Texture("grey_pixel.png"),sprite.getX() + 120,sprite.getY() + 145,18,4);
            batch.draw(new Texture("red_pixel.png"),sprite.getX()+1 + 120,sprite.getY()+1 + 145,16*(currentTimeSeconds/startTime),2);
            batch.end();
        }
    }

}
