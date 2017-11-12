package com.udacity.pk.bakingapp.viewmodels;

import android.databinding.BaseObservable;

/**
 * Description data view model.
 */

public class DescriptionViewModel extends BaseObservable {

    private String shortDescription;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyChange();
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        notifyChange();
    }
}
