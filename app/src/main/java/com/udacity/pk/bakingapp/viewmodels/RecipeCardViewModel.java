package com.udacity.pk.bakingapp.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by pk on 8/20/17.
 */

public class RecipeCardViewModel extends BaseObservable {

    private String recipeName;

    public String getRecipeName() {
        return recipeName;
    }

    @Bindable
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
