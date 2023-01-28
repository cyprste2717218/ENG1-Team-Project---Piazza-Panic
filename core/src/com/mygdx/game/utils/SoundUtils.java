package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundUtils {

    public static Music getBackgroundMusic(){
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound_Effects/Background_Music.mp3"));
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.setLooping(true);
        return backgroundMusic;
    }

    public static Sound getFailureSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/error_sound.mp3"));
    }

    public static Sound getItemPickupSound(){
        Sound itemPickupSound = Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/item_pickup.mp3"));
        itemPickupSound.setVolume(0, 0.5f);
        return itemPickupSound;
    }

    public static Sound getChefSwitchSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/chef-switch.mp3"));
    }

    public static Sound getCustomerSpawnSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/customer_spawn.mp3"));
    }

    public static Sound getCorrectOrderSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/correct_order.mp3"));
    }

    public static Sound getTimerFinishedSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/timer_finished.mp3"));
    }

    public static Sound getFryerSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/fryer_sound.mp3"));
    }

    public static Sound getCuttingSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/chopping.mp3"));
    }

    public static Sound getFormingSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/making.mp3"));
    }
}
