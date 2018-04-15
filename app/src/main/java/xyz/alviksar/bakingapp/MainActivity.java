package xyz.alviksar.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.alviksar.bakingapp.model.Recipe;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    // https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json

    // The recipe card width on screen in inches
    private static final float CARD_WIDTH_INCHES = 1.4f;

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    GridLayoutManager mLayoutManager;

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mErrorMessage = findViewById(R.id.tv_error_message);

        // Calculate the number of columns in the grid
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int mColumnWidthPixels;
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            mColumnWidthPixels = Math.round(CARD_WIDTH_INCHES * metrics.ydpi);

        } else {   // ORIENTATION_PORTRAIT
            mColumnWidthPixels = Math.round(CARD_WIDTH_INCHES * metrics.xdpi);

        }
        int columns = Math.max(1, metrics.widthPixels / mColumnWidthPixels);
        mLayoutManager = new GridLayoutManager(this, columns);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecipeAdapter = new RecipeAdapter(MainActivity.this, null);
        mRecyclerView.setAdapter(mRecipeAdapter);

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
                mRecipeAdapter.swapData(recipeList);
//                mRecyclerView.setAdapter(new RecipeAdapter(MainActivity.this, recipeList));
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Toast.makeText(MainActivity.this, R.string.can_not_get_recipes,
                        Toast.LENGTH_LONG).show();
            }

        });

        // Check network connection
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo == null || !networkInfo.isConnected()) {
            // Set no connection error message
            showErrorMessage(R.string.no_connection_error_msg);
        }
    }

    /**
     * This method will hide everything except the TextView error message
     * and set the appropriate text to it.
     */
    private void showErrorMessage(int msgResId) {
        mRecipeAdapter.swapData(null);
        mLoadingIndicator.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.VISIBLE);
        mErrorMessage.setText(msgResId);
    }
}
