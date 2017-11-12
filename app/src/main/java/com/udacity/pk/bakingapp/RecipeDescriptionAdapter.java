package com.udacity.pk.bakingapp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.pk.bakingapp.Models.RecipeStep;
import com.udacity.pk.bakingapp.databinding.DescriptionItemBinding;
import com.udacity.pk.bakingapp.viewmodels.DescriptionViewModel;

import java.util.List;

/**
 * Recipe Description Adapter.
 */

public class RecipeDescriptionAdapter extends RecyclerView.Adapter<RecipeDescriptionAdapter.RecipeDescriptionViewHolder> {


    private List<RecipeStep> recipeStepList;

    private DescriptionItemClickListener descriptionItemClickListener;

    public RecipeDescriptionAdapter(List<RecipeStep> recipeStepList, DescriptionItemClickListener descriptionItemClickListener) {
        this.recipeStepList = recipeStepList;
        this.descriptionItemClickListener = descriptionItemClickListener;
    }

    @Override
    public RecipeDescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.description_item, parent, true);
        return new RecipeDescriptionViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(RecipeDescriptionViewHolder holder, final int position) {
        DescriptionViewModel descriptionViewModel = new DescriptionViewModel();
        final RecipeStep recipeStep = recipeStepList.get(position);
        descriptionViewModel.setShortDescription(recipeStep.getShortDescription());
        descriptionViewModel.setDescription(recipeStep.getDescription());
        holder.descriptionItemBinding.setDescriptionItem(descriptionViewModel);
        holder.descriptionItemBinding.descriptionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionItemClickListener.onStepItemClick(recipeStep);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeStepList.size();
    }

    class RecipeDescriptionViewHolder extends RecyclerView.ViewHolder {

        DescriptionItemBinding descriptionItemBinding;

        public RecipeDescriptionViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.descriptionItemBinding = (DescriptionItemBinding) binding;
        }
    }
}

interface DescriptionItemClickListener {

    void onStepItemClick(RecipeStep recipeStep);
}

