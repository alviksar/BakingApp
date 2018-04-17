package xyz.alviksar.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import xyz.alviksar.bakingapp.model.Recipe;
import xyz.alviksar.bakingapp.model.Step;


public class StepDetailActivity extends AppCompatActivity {
    private Recipe mRecipe;
    private int mStepNum;
    private TextView mInstructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        mInstructions = findViewById(R.id.tv_instructions);
        if (savedInstanceState == null || !savedInstanceState.containsKey(Recipe.PARCEBLE_NAME)) {
            mRecipe = getIntent().getParcelableExtra(Recipe.PARCEBLE_NAME);
            mStepNum = getIntent().getIntExtra(Step.PARCEBLE_NAME, 0);
        } else {
            mRecipe = savedInstanceState.getParcelable(Recipe.PARCEBLE_NAME);
            mStepNum = savedInstanceState.getInt(Step.PARCEBLE_NAME);

        }
        if (mRecipe != null) {
            setTitle(mRecipe.getName());
            mInstructions.setText(mRecipe.getSteps().get(mStepNum).getDescription());
        } else {
            mInstructions.setText("");
        }

    }
}
