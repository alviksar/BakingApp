package xyz.alviksar.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    // https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        setContentView(R.layout.activity_main);
    }
}
