package com.udacity.pk.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.udacity.pk.bakingapp.Models.Ingredient;
import com.udacity.pk.bakingapp.Models.Recipe;
import com.udacity.pk.bakingapp.Models.RecipeStep;
import com.udacity.pk.bakingapp.databinding.IngredientItemBinding;
import com.udacity.pk.bakingapp.viewmodels.IngredientViewModel;

/**
 * Recipe Detail Fragment displays list of ingredients and list of recipe steps.
 */

public class RecipeDetailFragment extends Fragment implements DescriptionItemClickListener {

    public static final String TAG = RecipeDetailFragment.class.getSimpleName();

    public static final String RECIPE_KEY = "recipe";

    public static final String STEP_KEY = "step";

    private Recipe recipe;

    private IngredientItemBinding ingredientBinding;

    LinearLayout ingredientContainer;

    CardView stepCardView;

    RecyclerView descriptionRecyclerView;

    LinearLayoutManager linearLayoutManager;

    public static RecipeDetailFragment newInstance(Bundle args) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        recipe = bundle.getParcelable(RECIPE_KEY);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ingredientContainer = (LinearLayout) root.findViewById(R.id.ingredientContainer);
        stepCardView = (CardView) root.findViewById(R.id.descriptionCard);
        descriptionRecyclerView = (RecyclerView) root.findViewById(R.id.descriptionRecyclerView);
        descriptionRecyclerView.setFocusable(false);
        linearLayoutManager = new LinearLayoutManager(getContext());
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (Ingredient ingredient : recipe.getIngredientList()) {
            ingredientBinding =
                DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.ingredient_item, ingredientContainer, true);
            IngredientViewModel ingredientView = new IngredientViewModel();

            ingredientView.setIngredientName(ingredient.getIngredient());
            ingredientView.setMeasure(ingredient.getMeasure());
            ingredientView.setQuantity(ingredient.getQuantity());
            ingredientBinding.setIngredient(ingredientView);
        }

        RecipeDescriptionAdapter descriptionAdapter = new RecipeDescriptionAdapter(recipe.getRecipeStepList(), this);
        descriptionRecyclerView.setAdapter(descriptionAdapter);
        descriptionRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boolean tabletView = getResources().getBoolean(R.bool.tabletMode);
        if (!tabletView) {
            ((RecipeDetailActivity) getActivity()).setupRecipeDescriptionAppbar();
        }
    }

    @Override
    public void onStepItemClick(RecipeStep recipeStep) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeDetailFragment.STEP_KEY, recipeStep);
        bundle.putParcelable(RecipeDetailFragment.RECIPE_KEY, recipe);
        StepDescriptionFragment fragment = StepDescriptionFragment.newInstance(bundle);
        if (getActivity() instanceof RecipeDetailActivity) {
            ((RecipeDetailActivity) getActivity()).addFragment(fragment, R.id.recipe_step_fragment_container,
                StepDescriptionFragment.TAG);
            ((RecipeDetailActivity) getActivity()).showStepFragment();
        }
    }
}
