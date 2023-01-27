package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundUtils {

    public static Music getBackgroundMusic(){
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Background_Music.mp3"));
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.setLooping(true);
        return backgroundMusic;
    }

    public static Sound getFailureSound(){
        return Gdx.audio.newSound(Gdx.files.internal("error_sound.mp3"));
    }

    public static Sound getItemPickupSound(){
        return Gdx.audio.newSound(Gdx.files.internal("item_pickup.mp3"));
    }

    public static Sound getChefSwitchSound(){
        return Gdx.audio.newSound(Gdx.files.internal("chef-switch.mp3"));
    }

    public static Sound getCustomerSpawnSound(){
        return Gdx.audio.newSound(Gdx.files.internal("customer_spawn.mp3"));
    }

    public static Sound getCorrectOrderSound(){
        return Gdx.audio.newSound(Gdx.files.internal("correct_order.mp3"));
    }

    public static Sound getTimerFinishedSound(){
        return Gdx.audio.newSound(Gdx.files.internal("timer_finished.mp3"));
    }

    public static Sound getFryerSound(){
        return Gdx.audio.newSound(Gdx.files.internal("fryer_sound.mp3"));
    }

    public static Sound getCuttingSound(){
        return Gdx.audio.newSound(Gdx.files.internal("chopping.mp3"));
    }

    public static Sound getFormingSound(){
        return Gdx.audio.newSound(Gdx.files.internal("making.mp3"));
    }
}