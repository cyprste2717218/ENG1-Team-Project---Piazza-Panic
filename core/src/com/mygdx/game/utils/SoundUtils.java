package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * The type Sound utils.
 */
public class SoundUtils {

    /**
     * Get background music music.
     *
     * @return the music
     */
    public static Music getBackgroundMusic(){
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound_Effects/Background_Music.mp3"));
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.setLooping(true);
        return backgroundMusic;
    }

    /**
     * Get failure sound sound.
     *
     * @return the sound
     */
    public static Sound getFailureSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/error_sound.mp3"));
    }

    /**
     * Get item pickup sound sound.
     *
     * @return the sound
     */
    public static Sound getItemPickupSound(){
        Sound itemPickupSound = Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/item_pickup.mp3"));
        itemPickupSound.setVolume(0, 0.5f);
        return itemPickupSound;
    }

    /**
     * Get chef switch sound sound.
     *
     * @return the sound
     */
    public static Sound getChefSwitchSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/chef-switch.mp3"));
    }

    /**
     * Get customer spawn sound sound.
     *
     * @return the sound
     */
    public static Sound getCustomerSpawnSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/customer_spawn.mp3"));
    }

    /**
     * Get correct order sound sound.
     *
     * @return the sound
     */
    public static Sound getCorrectOrderSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/correct_order.mp3"));
    }

    /**
     * Get timer finished sound sound.
     *
     * @return the sound
     */
    public static Sound getTimerFinishedSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/timer_finished.mp3"));
    }

    /**
     * Get fryer sound sound.
     *
     * @return the sound
     */
    public static Sound getFryerSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/fryer_sound.mp3"));
    }

    /**
     * Get cutting sound sound.
     *
     * @return the sound
     */
    public static Sound getCuttingSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/chopping.mp3"));
    }

    /**
     * Get forming sound sound.
     *
     * @return the sound
     */
    public static Sound getFormingSound(){
        return Gdx.audio.newSound(Gdx.files.internal("Sound_Effects/making.mp3"));
    }
}
