package de.tomgrill.gdxtesting.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Chef;
import com.mygdx.game.foodClasses.Food;
import com.mygdx.game.foodClasses.FoodItems;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class IngredientStationTests{

    // fake texture
    Texture fakeTexture = Mockito.mock(Texture.class);
    //Create a chef
    Chef fakeChef = Mockito.mock(Chef.class);

}
