package xyz.alviksar.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import xyz.alviksar.bakingapp.model.Recipe;

public class StepListActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        if (savedInstanceState == null || !savedInstanceState.containsKey(Recipe.PARCEBLE_KEY)) {
            mRecipe = getIntent().getParcelableExtra(Recipe.PARCEBLE_KEY);
        } else {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCEBLE_KEY);
        }
        if (mRecipe != null) {
            setTitle(mRecipe.getName());

            StepListFragment stepListFragment = StepListFragment.newInstance(mRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = getSupportFragmentManager().findFragmentById( R.id.step_list_fragment);
            if( fragment == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.step_list_fragment, stepListFragment)
                        .commit();
            }
        }

    }
}
