package xyz.alviksar.bakingapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import xyz.alviksar.bakingapp.model.Recipe;

public interface BakingAppClient {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    public Call<List<Recipe>> listRecipes();
}
