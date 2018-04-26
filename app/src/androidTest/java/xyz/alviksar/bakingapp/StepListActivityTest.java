package xyz.alviksar.bakingapp;

import android.content.Intent;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import xyz.alviksar.bakingapp.model.Recipe;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static xyz.alviksar.bakingapp.util.TestUtils.getTestRecipe;


@RunWith(AndroidJUnit4.class)
public class StepListActivityTest {

    private IdlingResource mIdlingResource;

    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<MainActivity> mStepDetailActivityTestRule
            = new ActivityTestRule<>(MainActivity.class, true, false);


    @Before
    public void setUp() {
        Recipe recipe = getTestRecipe();
        Intent intent = new Intent();
    //    intent.putExtra("EXTRA", "Test");
        intent.putExtra(Recipe.PARCEBLE_NAME, recipe);

        mStepDetailActivityTestRule.launchActivity(intent);
    }


    @Test
    public void startActivity_ShowStepList() {

        onView(withId(R.id.rv_step_list))
                .perform(scrollToPosition(0))
                .check(matches(isDisplayed()));
    }

}