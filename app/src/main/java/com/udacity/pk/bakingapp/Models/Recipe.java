package com.udacity.pk.bakingapp.Models;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pk on 8/20/17.
 */

public class Recipe implements Parcelable {

    @SerializedName("id")
    private int recipeId;

    @SerializedName("name")
    private String recipeName;

    @SerializedName("ingredients")
    private List<Ingredient> ingredientList = new ArrayList<>();

    @SerializedName("steps")
    private List<RecipeStep> recipeStepList = new ArrayList<>();

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String imageUrl;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<RecipeStep> getSteps() {
        return recipeStepList;
    }

    public void setSteps(List<RecipeStep> steps) {
        this.recipeStepList = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    protected Recipe(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        servings = in.readInt();
        imageUrl = in.readString();
        ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
        recipeStepList = in.createTypedArrayList(RecipeStep.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(recipeName);
        dest.writeInt(servings);
        dest.writeString(imageUrl);
        dest.writeTypedList(ingredientList);
        dest.writeTypedList(recipeStepList);
    }

    public List<RecipeStep> getRecipeStepList() {
        return recipeStepList;
    }

    public void setRecipeStepList(List<RecipeStep> recipeStepList) {
        this.recipeStepList = recipeStepList;
    }
}
