package xyz.alviksar.bakingapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.alviksar.bakingapp.model.Recipe;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * The configuration screen for the {@link BakingAppWidget BakingAppWidget} AppWidget.
 */
public class BakingAppWidgetConfigureActivity extends Activity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG = BakingAppWidgetConfigureActivity.class.getSimpleName();

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    private static final String BUNDLE_RECYCLER_LAYOUT = "BakingAppWidgetConfigureActivity.mRecyclerView.layout";

    // The recipe card width on screen in inches
    private static final float CARD_WIDTH_INCHES = 1.7f;

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    GridLayoutManager mLayoutManager;

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessage;

    private Parcelable mSavedRecyclerLayoutState = null;

    private static final String PREFS_NAME = "xyz.alviksar.bakingapp.BakingAppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static final String PREF_RECIPE_KEY = "appwidget_recipe_";

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    //   EditText mAppWidgetText;
//    View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            final Context context = BakingAppWidgetConfigureActivity.this;
//
//            // When the button is clicked, store the string locally
//            String widgetText = mAppWidgetText.getText().toString();
//            saveTitlePref(context, mAppWidgetId, widgetText);
//
//            // It is the responsibility of the configuration activity to update the app widget
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            BakingAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
//
//            // Make sure we pass back the original appWidgetId
//            Intent resultValue = new Intent();
//            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
//            setResult(RESULT_OK, resultValue);
//            finish();
//        }
//    };

    public BakingAppWidgetConfigureActivity() {
        super();
    }

//    // Write the prefix to the SharedPreferences object for this widget
//    static void saveTitlePref(Context context, int appWidgetId, String text) {
//        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
//        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
//        prefs.apply();
//    }
//
//
//    // Read the prefix from the SharedPreferences object for this widget.
//    // If there is no preference saved, get the default from a resource
//    static String loadTitlePref(Context context, int appWidgetId) {
//        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
//        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
//        if (titleValue != null) {
//            return titleValue;
//        } else {
//            return context.getString(R.string.appwidget_text);
//        }
//    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }


    // Write the prefix to the SharedPreferences object for this widget
    static void saveRecipePref(Context context, int appWidgetId, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        String recipeJson = new Gson().toJson(recipe);
        prefs.putString(PREF_RECIPE_KEY + appWidgetId, recipeJson);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static Recipe loadRecipePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String recipeJson = prefs.getString(PREF_RECIPE_KEY + appWidgetId, null);
        if (recipeJson != null) {
            return new Gson().fromJson(recipeJson, Recipe.class);
        } else {
            return null;
        }
    }

    static void deleteRecipePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_RECIPE_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        //====
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

        mRecipeAdapter = new RecipeAdapter(BakingAppWidgetConfigureActivity.this, this);
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
                showData();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Toast.makeText(BakingAppWidgetConfigureActivity.this, R.string.can_not_get_recipes,
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
        } else {
            showLoading();
        }
        //====
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

//        setContentView(R.layout.baking_app_widget_configure);
//        mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
//        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);


        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        //mAppWidgetText.setText(loadTitlePref(BakingAppWidgetConfigureActivity.this, mAppWidgetId));
    }

    @Override
    public void onClick(Recipe recipe) {
        final Context context = BakingAppWidgetConfigureActivity.this;

        // When the button is clicked, store the string locally
        //  String widgetText = mAppWidgetText.getText().toString();
        String widgetText = recipe.getIngredientsString();
        // saveTitlePref(context, mAppWidgetId, widgetText);
        saveRecipePref(context, mAppWidgetId, recipe);

                // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        BakingAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
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

    /**
     * This method will make the loading indicator visible and hide the RecyclerView and error
     * message.
     */
    private void showLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }

    /**
     * This method will make the RecyclerView visible and hide the error message and
     * loading indicator.
     */
    private void showData() {
        mLoadingIndicator.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        // Restore position upon orientation change
        if (mSavedRecyclerLayoutState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }


    /**
     * https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
     */
    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(BUNDLE_RECYCLER_LAYOUT))
                mSavedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
        }
    }
}

