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

    /*@Test
    public void testFullFoodBuilder() {

        //It was intended to test the reward functionality as a part of this, but this initialises the FoodItems class, causing rendering errors

        // Create a mock Texture object using Mockito
        Texture texture = Mockito.mock(Texture.class);

        // Use the FoodBuilder to create a Food object with all optional attributes set to true
        Food food = new Food.FoodBuilder("Pizza", texture)
                .setFryable()
                .setChoppable()
                .setBakeable()
                .setFormable()
                .setToastable()
                .build();

        // Assert that the values of the Food object's attributes are as expected
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
        // Create a mock Texture object using Mockito
        Texture texture = Mockito.mock(Texture.class);

        // Use the FoodBuilder to create a Food object with no optional attributes set
        Food food = new Food.FoodBuilder("Pasta", texture).build();

        // Assert that the values of the Food object's attributes are as expected
        assertEquals("Pasta", food.name);
        assertFalse(food.isFryable);
        assertFalse(food.isChoppable);
        assertFalse(food.isBakeable);
        assertFalse(food.isFormable);
        assertFalse(food.isToastable);
        assertEquals(0, food.reward);
    }*/
}