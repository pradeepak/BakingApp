package com.udacity.pk.bakingapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.pk.bakingapp.Models.Recipe;
import com.udacity.pk.bakingapp.databinding.RecipeCardBinding;
import com.udacity.pk.bakingapp.viewmodels.RecipeCardViewModel;

import java.util.List;

/**
 * Recipe List Adapter.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeCardViewHolder> {

    private List<Recipe> recipeList;

    private CardClickListener cardClickListener;

    public RecipeListAdapter(List<Recipe> recipeList, CardClickListener cardClickListener) {
        this.recipeList = recipeList;
        this.cardClickListener = cardClickListener;
    }

    @Override
    public RecipeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.recipe_card, parent, true);
        return new RecipeCardViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(RecipeCardViewHolder holder, final int position) {
        RecipeCardViewModel cardViewModel = new RecipeCardViewModel();
        final Recipe recipeItem = recipeList.get(position);
        cardViewModel.setRecipeName(recipeItem.getRecipeName());
        holder.recipeCardBinding.setRecipeCardView(cardViewModel);
        holder.recipeCardBinding.recipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardClickListener.onCardClick(recipeItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeCardViewHolder extends RecyclerView.ViewHolder {

        RecipeCardBinding recipeCardBinding;

        public RecipeCardViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.recipeCardBinding = (RecipeCardBinding) binding;
        }
    }
}

interface CardClickListener {

    void onCardClick(Recipe recipe);
}
