package xyz.alviksar.bakingapp;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import xyz.alviksar.bakingapp.model.Recipe;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;
import static xyz.alviksar.bakingapp.util.TestUtils.getTestRecipe;


@RunWith(AndroidJUnit4.class)
public class StepListActivityTest {

    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<StepListActivity> mStepListActivityTestRule
            = new ActivityTestRule<>(StepListActivity.class, true, false);


    @Before
    public void setUp() {
        Recipe recipe = getTestRecipe();
        Intent intent = new Intent();
        intent.putExtra(Recipe.PARCEBLE_NAME, recipe);

        mStepListActivityTestRule.launchActivity(intent);
    }

    @Test
    public void startActivity_ShowStepList() {

        // Check ingredients
        onView(withId(R.id.tv_ingredients))
                .check(matches(withText(containsString("Graham Cracker"))));

        // Check the first step
        onView(withId(R.id.rv_step_list))
                .perform(scrollToPosition(1))
                .check(matches(hasDescendant(withText(containsString("1.")))));
        //    .check(matches(isDisplayed()));

        // Start detail activity for the first step
        onView(withId(R.id.rv_step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));

        // Check if detail activity displayed
        onView(withId(R.id.tv_step_description)).check(matches(isDisplayed()));
    }

}