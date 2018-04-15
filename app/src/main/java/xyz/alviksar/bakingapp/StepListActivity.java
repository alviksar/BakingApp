package xyz.alviksar.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.alviksar.bakingapp.model.Recipe;

public class StepListActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        if (savedInstanceState == null || !savedInstanceState.containsKey(Recipe.PARCEBLE_KEY)) {
            mRecipe = getIntent().getExtras().getParcelable(Recipe.PARCEBLE_KEY);
        } else {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCEBLE_KEY);
        }
        setTitle(mRecipe.getName());

        StepListFragment stepListFragment =  StepListFragment.newInstance(mRecipe);
        // TODO: Scroll to current position after rotation
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.step_list_fragment, stepListFragment)
                .commit();

    }
}
