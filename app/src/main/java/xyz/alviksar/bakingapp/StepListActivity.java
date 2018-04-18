package xyz.alviksar.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import xyz.alviksar.bakingapp.model.Recipe;
import xyz.alviksar.bakingapp.model.Step;

public class StepListActivity extends AppCompatActivity implements StepListFragment.OnStepClickListener {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        if (savedInstanceState == null || !savedInstanceState.containsKey(Recipe.PARCEBLE_NAME)) {
            mRecipe = getIntent().getParcelableExtra(Recipe.PARCEBLE_NAME);
        } else {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCEBLE_NAME);
        }
        if (mRecipe != null) {
            setTitle(mRecipe.getName());

            StepListFragment stepListFragment = StepListFragment.newInstance(mRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.step_list_fragment);
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.step_list_fragment, stepListFragment)
                        .commit();
            }
        }

    }

    @Override
    public void onStepClick(int stepId) {
        //  Toast.makeText(this, step.getDescription(), Toast.LENGTH_LONG).show();
        Intent stepDetailIntent = new Intent(StepListActivity.this, StepDetailActivity.class);
        stepDetailIntent.putExtra(Step.PARCEBLE_NAME, stepId);
        stepDetailIntent.putExtra(Recipe.PARCEBLE_NAME, mRecipe);
        startActivity(stepDetailIntent);
    }
}
