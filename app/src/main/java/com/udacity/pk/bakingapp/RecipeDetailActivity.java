package com.udacity.pk.bakingapp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.udacity.pk.bakingapp.Models.Recipe;

/**
 * Detail Activity contains recipe detail fragment and recipe step fragment and toggles the layouts
 */

public class RecipeDetailActivity extends AppCompatActivity {

    private FrameLayout detailsContainer;

    private FrameLayout stepContainer;

    private boolean tabletMode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        detailsContainer = (FrameLayout) findViewById(R.id.recipe_details_fragment_container);
        stepContainer = (FrameLayout) findViewById(R.id.recipe_step_fragment_container);

        tabletMode = getResources().getBoolean(R.bool.tabletMode);
        if (!tabletMode) {
            if (savedInstanceState == null) {
                RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(getIntent().getExtras());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.recipe_details_fragment_container, recipeDetailFragment, RecipeDetailFragment.TAG)
                    .commit();
            } else if (savedInstanceState.getBoolean("stepContainer")) {
                showStepFragment();
            } else {
                showDetailsFragment();
            }
        } else {
            Bundle detailBundle = getIntent().getExtras();
            Recipe recipe = detailBundle.getParcelable(RecipeDetailFragment.RECIPE_KEY);
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(detailBundle);
            if (recipe != null) {
                detailBundle.putParcelable(RecipeDetailFragment.STEP_KEY, recipe.getSteps().get(0));
            }
            StepDescriptionFragment stepDescriptionFragment = StepDescriptionFragment.newInstance(detailBundle);

            getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_details_fragment_container, recipeDetailFragment)
                .add(R.id.recipe_step_fragment_container, stepDescriptionFragment)
                .commit();
            setupRecipeDescriptionAppbar();
        }
    }

    public void setupRecipeDescriptionAppbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.stepDescriptionToolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.recipe_description_title);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    public void setupStepDescriptionAppbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.stepDescriptionToolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.step_description_title);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addFragment(Fragment newFragment, @IdRes int containerId, String TAG) {
        getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(containerId, newFragment, TAG)
            .addToBackStack(TAG)
            .commit();
    }

    public void replaceFragment(Fragment newFragment, @IdRes int containerId, String TAG, boolean nextStep) {
        //TODO set proper Animations
        getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(nextStep ? android.R.anim.slide_out_right : android.R.anim.slide_in_left, nextStep ? android.R.anim
                .slide_in_left : android.R.anim.slide_out_right, 0, 0)
            .replace(containerId, newFragment, TAG)
            .commit();
    }

    public void showDetailsFragment() {
        detailsContainer.setVisibility(View.VISIBLE);
        stepContainer.setVisibility(View.GONE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.recipe_description_title);
        }
    }

    public void showStepFragment() {
        if (!tabletMode) {
            detailsContainer.setVisibility(View.GONE);
            stepContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (detailsContainer.getVisibility() == View.GONE && stepContainer.getVisibility() == View.VISIBLE) {
            outState.putBoolean("stepContainer", true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!tabletMode && detailsContainer.getVisibility() == View.GONE && stepContainer.getVisibility() == View.VISIBLE) {
            showDetailsFragment();
        }
    }
}
