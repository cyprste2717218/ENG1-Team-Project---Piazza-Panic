package com.mygdx.game.enums;

import com.mygdx.game.utils.TileMapUtils;

//An enum that has all the directions the chef can be facing
//It is laid out in this specific order so that the ordinals (index) can be multiplied by 90 degrees to get the correct rotation
public enum Facing{
    UP,
    LEFT,
    DOWN,
    RIGHT;
}
