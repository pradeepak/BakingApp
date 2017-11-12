package com.udacity.pk.bakingapp.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pk on 8/20/17.
 */

public class RecipeApiClient {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }

}
