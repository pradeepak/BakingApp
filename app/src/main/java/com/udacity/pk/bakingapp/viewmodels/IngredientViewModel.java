package com.udacity.pk.bakingapp.viewmodels;

import android.databinding.BaseObservable;

/**
 * Ingredient data view model.
 */

public class IngredientViewModel extends BaseObservable {

    private String ingredientName;

    private String measure;

    private float quantity;

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
        notifyChange();
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
        notifyChange();
    }

    public String getQuantity() {
        return String.valueOf(quantity);
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
        notifyChange();
    }
}
