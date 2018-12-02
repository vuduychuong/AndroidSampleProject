package com.chuongvd.support.adapterbinding.sectionedrecyclerviewadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.chuongvd.support.adapterbinding.ViewHolderBinding;

/**
 * Abstract {@link Section} with no states.
 */
public abstract class StatelessSection extends Section {

    /**
     * Create a stateless Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */
    public StatelessSection(SectionParameters sectionParameters) {
        super(sectionParameters);

        if (sectionParameters.loadingViewWillBeProvided) {
            throw new IllegalArgumentException(
                    "Stateless section shouldn't have loadingViewWillBeProvided set");
        }

        if (sectionParameters.failedViewWillBeProvided) {
            throw new IllegalArgumentException(
                    "Stateless section shouldn't have failedViewWillBeProvided set");
        }

        if (sectionParameters.emptyViewWillBeProvided) {
            throw new IllegalArgumentException(
                    "Stateless section shouldn't have emptyViewWillBeProvided set");
        }
    }

    // Override these methods to make them final.

    @Override
    public final void onBindLoadingViewHolder(ViewHolderBinding holder) {
        super.onBindLoadingViewHolder(holder);
    }

    @Override
    public final ViewHolderBinding getLoadingViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return super.getLoadingViewHolder(parent, inflater);
    }

    @Override
    public final void onBindFailedViewHolder(ViewHolderBinding holder) {
        super.onBindFailedViewHolder(holder);
    }

    @Override
    public final ViewHolderBinding getFailedViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return super.getFailedViewHolder(parent, inflater);
    }

    @Override
    public final void onBindEmptyViewHolder(ViewHolderBinding holder) {
        super.onBindEmptyViewHolder(holder);
    }

    @Override
    public final ViewHolderBinding getEmptyViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return super.getEmptyViewHolder(parent, inflater);
    }
}
