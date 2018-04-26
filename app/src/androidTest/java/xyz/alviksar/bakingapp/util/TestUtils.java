package xyz.alviksar.bakingapp.util;

import org.junit.Test;

import xyz.alviksar.bakingapp.R;
import xyz.alviksar.bakingapp.model.Recipe;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class TestUtils {

    public static Recipe getTestRecipe() {
        Recipe recipe = new Recipe();

        return recipe;
    }

}
