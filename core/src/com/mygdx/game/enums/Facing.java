package com.mygdx.game.enums;

import com.mygdx.game.utils.TileMapUtils;

/**
 * The enum Facing that controls the direction the chef and customers are looking.
 * //It is laid out in this specific order so that the ordinals (index) can be multiplied by 90 degrees to get the correct rotation or that the Texture indexes for the rotation match the ordinal
 */

public enum Facing{
    /**
     * Up facing.
     */
    UP,
    /**
     * Left facing.
     */
    LEFT,
    /**
     * Down facing.
     */
    DOWN,
    /**
     * Right facing.
     */
    RIGHT;
}
