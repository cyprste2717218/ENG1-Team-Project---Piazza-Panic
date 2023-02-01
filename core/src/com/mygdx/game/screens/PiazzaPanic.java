package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The type Piazza panic.
 */
public class PiazzaPanic extends Game {
    /**
     * The Sprite batch.
     */
    SpriteBatch batch;

    /**
     * Instantiates a new Piazza panic.
     */
    public PiazzaPanic() {
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenu(this));
    }
}
