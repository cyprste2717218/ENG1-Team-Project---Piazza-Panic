package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PiazzaPanic extends Game {
    SpriteBatch batch;

    public PiazzaPanic() {
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenu(this));
    }
}
