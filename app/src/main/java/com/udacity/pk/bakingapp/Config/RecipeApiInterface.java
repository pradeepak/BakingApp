package com.udacity.pk.bakingapp.Config;

import com.udacity.pk.bakingapp.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Recipe API Interface.
 */

public interface RecipeApiInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipeList();
}
