package com.chuongvd.support.adapterbinding;

/**
 * Created by chuongvd on 1/8/19.
 */

import com.chuongvd.support.adapterbinding.sectionedrecyclerviewadapter.Section;

/**
 * Class used as constructor parameters of {@link Section}.
 */
@SuppressWarnings({ "WeakerAccess", "PMD.AvoidFieldNameMatchingMethodName" })
public final class StateParameters {

    public final boolean itemViewWillBeProvided;
    public final boolean loadingViewWillBeProvided;
    public final boolean failedViewWillBeProvided;
    public final boolean emptyViewWillBeProvided;

    private StateParameters(StateParameters.Builder builder) {

        this.itemViewWillBeProvided = builder.itemViewWillBeProvided;
        this.loadingViewWillBeProvided = builder.loadingViewWillBeProvided;
        this.failedViewWillBeProvided = builder.failedViewWillBeProvided;
        this.emptyViewWillBeProvided = builder.emptyViewWillBeProvided;
    }

    /**
     * Builder static factory method with mandatory parameters of {@link StatefulAdapterBinding} (namely none).
     */
    public static StateParameters.Builder builder() {
        return new StateParameters.Builder();
    }

    /**
     * Builder of {@link com.chuongvd.support.adapterbinding.sectionedrecyclerviewadapter.SectionParameters}.
     */
    public static class Builder {
        private boolean itemViewWillBeProvided;
        private boolean loadingViewWillBeProvided;
        private boolean failedViewWillBeProvided;
        private boolean emptyViewWillBeProvided;

        /**
         * Constructor with mandatory parameters of {@link Section} (namely none).
         */
        private Builder() {
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public StateParameters.Builder itemViewWillBeProvided() {
            itemViewWillBeProvided = true;

            return this;
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public StateParameters.Builder loadingViewWillBeProvided() {
            loadingViewWillBeProvided = true;

            return this;
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public StateParameters.Builder failedViewWillBeProvided() {
            failedViewWillBeProvided = true;

            return this;
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public StateParameters.Builder emptyViewWillBeProvided() {
            emptyViewWillBeProvided = true;

            return this;
        }

        /**
         * Build an instance of SectionParameters.
         *
         * @return an instance of SectionParameters
         */
        public StateParameters build() {
            return new StateParameters(this);
        }
    }
}
