package com.udacity.pk.bakingapp;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.pk.bakingapp.Models.Recipe;
import com.udacity.pk.bakingapp.Models.RecipeStep;
import com.udacity.pk.bakingapp.databinding.StepDescriptionFragmentBinding;
import com.udacity.pk.bakingapp.viewmodels.StepDescriptionViewModel;

/**
 * Contains the media to and instructions for the recipe step.
 */

public class StepDescriptionFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = StepDescriptionFragment.class.getSimpleName();

    public static final String SEEK_POSITION_KEY = "recipeStep";

    private StepDescriptionFragmentBinding stepDescriptionBinding;

    private RecipeStep recipeStep;

    private Recipe recipe;

    private StepDescriptionViewModel stepDescriptionViewModel;

    private SimpleExoPlayer player;

    private long seekPosition = -1;

    private boolean tabletView;

    public static StepDescriptionFragment newInstance(Bundle bundle) {

        StepDescriptionFragment fragment = new StepDescriptionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            recipe = bundle.getParcelable(RecipeDetailFragment.RECIPE_KEY);
            recipeStep = bundle.getParcelable(RecipeDetailFragment.STEP_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        stepDescriptionBinding = DataBindingUtil.inflate(inflater, R.layout.step_description_fragment, container, false);
        return stepDescriptionBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!tabletView) {
            ((RecipeDetailActivity) getActivity()).setupStepDescriptionAppbar();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stepDescriptionViewModel = new StepDescriptionViewModel();
        tabletView = getResources().getBoolean(R.bool.tabletMode);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !tabletView) {
            stepDescriptionViewModel.setThumbnailVisibility(View.GONE);
            stepDescriptionBinding.stepDescriptionCard.setVisibility(View.GONE);
            stepDescriptionBinding.exoPlayer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

            if (TextUtils.isEmpty(recipeStep.getVideoURL())) {
                stepDescriptionViewModel.setStepDescription(getString(R.string.no_video_description));
                stepDescriptionViewModel.setExoPlayerVisibility(View.GONE);
            } else {
                setupVideoPlayer();
            }
            if (getActivity() instanceof RecipeDetailActivity) {
                ((RecipeDetailActivity) getActivity()).showStepFragment();
            }

        } else {
            stepDescriptionViewModel.setStepDescription(recipeStep.getDescription());
            if (savedInstanceState != null && savedInstanceState.containsKey(SEEK_POSITION_KEY)) {
                seekPosition = savedInstanceState.getLong(SEEK_POSITION_KEY);
            }
            setupVideoPlayer();
            stepDescriptionBinding.setStepDescriptionItem(stepDescriptionViewModel);
            if (!TextUtils.isEmpty(recipeStep.getThumbNailUrl())) {
                stepDescriptionViewModel.setThumbnailUrl(recipeStep.getThumbNailUrl());
            } else {
                stepDescriptionViewModel.setThumbnailVisibility(View.GONE);
            }
            stepDescriptionViewModel.setStepDescription(recipeStep.getDescription());
            setupButtons();
            stepDescriptionBinding.nextStep.setOnClickListener(this);
            stepDescriptionBinding.previousStep.setOnClickListener(this);
        }
    }

    private void setupButtons() {
        if (recipeStep.getId() == 0 && recipeStep.getId() < recipe.getSteps().size()) {
            stepDescriptionViewModel.setPreviousButtonVisibility(View.GONE);
        } else if (recipeStep.getId() == recipe.getSteps().size() - 1) {
            stepDescriptionViewModel.setNextButtonVisibility(View.GONE);
        } else {
            stepDescriptionViewModel.setPreviousButtonVisibility(View.VISIBLE);
            stepDescriptionViewModel.setNextButtonVisibility(View.VISIBLE);
        }
    }

    private void setupVideoPlayer() {
        if (player == null) {
            if (!TextUtils.isEmpty(recipeStep.getVideoURL())) {
                player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

                stepDescriptionBinding.exoPlayer.setPlayer(player);
                player.setPlayWhenReady(true);
                Uri mediaUri = Uri.parse(recipeStep.getVideoURL());
                if (seekPosition != -1) {
                    player.seekTo(seekPosition);
                }
                player.prepare(buildMediaSource(mediaUri), true, false);
            } else {
                stepDescriptionViewModel.setExoPlayerVisibility(View.GONE);
            }
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
            new DefaultHttpDataSourceFactory("ua"),
            new DefaultExtractorsFactory(), null, null);
    }

    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
        boolean nextStep = false;
        args.putParcelable(RecipeDetailFragment.RECIPE_KEY, recipe);
        if (v == stepDescriptionBinding.previousStep && recipeStep.getId() != 0) {
            args.putParcelable(RecipeDetailFragment.STEP_KEY, recipe.getSteps().get(recipeStep.getId() - 1));
            nextStep = false;
        } else if (v == stepDescriptionBinding.nextStep && recipeStep.getId() != recipe.getSteps().size() - 1) {
            args.putParcelable(RecipeDetailFragment.STEP_KEY, recipe.getSteps().get(recipeStep.getId() + 1));
            nextStep = true;
        }
        if (getActivity() instanceof RecipeDetailActivity) {
            ((RecipeDetailActivity) getActivity()).replaceFragment(StepDescriptionFragment.newInstance(args), R.id
                .recipe_step_fragment_container, StepDescriptionFragment.TAG, nextStep);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            seekPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            seekPosition = player.getCurrentPosition();
            player.release();
            player = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setupVideoPlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
        if (recipeStep != null) {
            outState.putParcelable(RecipeDetailFragment.STEP_KEY, recipeStep);
            outState.putParcelable(RecipeDetailFragment.RECIPE_KEY, recipe);
        }

        outState.putLong(SEEK_POSITION_KEY, seekPosition);
    }
}
