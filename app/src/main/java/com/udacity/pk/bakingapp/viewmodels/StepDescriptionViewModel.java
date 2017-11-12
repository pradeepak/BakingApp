package com.udacity.pk.bakingapp.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Step Description View Model.
 */

public class StepDescriptionViewModel extends BaseObservable {

    private String stepDescription;

    private int exoPlayerVisibility;

    private int thumbnailVisibility;

    private int previousButtonVisibility;

    private int nextButtonVisibility;

    private String thumbnailUrl;


    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
        notifyChange();
    }

    public int getExoPlayerVisibility() {
        return exoPlayerVisibility;
    }

    public void setExoPlayerVisibility(int exoPlayerVisibility) {
        this.exoPlayerVisibility = exoPlayerVisibility;
        notifyChange();
    }

    public int getPreviousButtonVisibility() {
        return previousButtonVisibility;
    }

    public void setPreviousButtonVisibility(int previousButtonVisibility) {
        this.previousButtonVisibility = previousButtonVisibility;
        notifyChange();
    }

    public int getNextButtonVisibility() {
        return nextButtonVisibility;
    }

    public void setNextButtonVisibility(int nextButtonVisibility) {
        this.nextButtonVisibility = nextButtonVisibility;
        notifyChange();
    }

    public int getThumbnailVisibility() {
        return thumbnailVisibility;
    }

    public void setThumbnailVisibility(int thumbnailVisibility) {
        this.thumbnailVisibility = thumbnailVisibility;
        notifyChange();
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @BindingAdapter("thumbNailUrl")
    public static void loadImage(ImageView thumbnailView, String thumbnailUrl) {
        Glide.with(thumbnailView.getContext()).load(thumbnailUrl).into(thumbnailView);
    }
}
