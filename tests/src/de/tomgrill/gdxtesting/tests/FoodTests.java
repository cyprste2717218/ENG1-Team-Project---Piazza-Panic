package de.tomgrill.gdxtesting.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.foodClasses.Food;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(GdxTestRunner.class)
public class FoodTests {

    @Test
    public void testFullFoodBuilder() {

        //It was intended to test the reward functionality in this class, however,because it is connected to the FoodItems class, it loads textures, breaking the testing environment

        Texture texture = Mockito.mock(Texture.class);

        Food food = new Food.FoodBuilder("Pizza", texture)
                .setFryable()
                .setChoppable()
                .setBakeable()
                .setFormable()
                .setToastable()
                .build();

        assertEquals("Pizza", food.name);
        assertTrue(food.isFryable);
        assertTrue(food.isChoppable);
        assertTrue(food.isBakeable);
        assertTrue(food.isFormable);
        assertTrue(food.isToastable);
        assertEquals(0, food.reward);
    }

    @Test
    public void testEmptyFoodBuilder() {

        Texture texture = Mockito.mock(Texture.class);

        Food food = new Food.FoodBuilder("Pasta", texture).build();

        assertEquals("Pasta", food.name);
        assertFalse(food.isFryable);
        assertFalse(food.isChoppable);
        assertFalse(food.isBakeable);
        assertFalse(food.isFormable);
        assertFalse(food.isToastable);
        assertEquals(0, food.reward);
    }
}
