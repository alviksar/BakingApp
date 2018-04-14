package xyz.alviksar.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.alviksar.bakingapp.model.Recipe;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    // https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        BakingAppClient bakingAppClient = retrofit.create(BakingAppClient.class);
        Call<List<Recipe>> call = bakingAppClient.listRecipes();

        call.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();

                mRecyclerView.setAdapter(new RecipeAdapter(MainActivity.this, recipeList));
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                String s = t.getMessage();
                Log.e(TAG, s);
                Toast.makeText(MainActivity.this, R.string.can_not_get_recipes,
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
