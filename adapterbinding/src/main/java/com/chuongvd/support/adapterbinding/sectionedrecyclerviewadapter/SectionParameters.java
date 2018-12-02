package com.chuongvd.support.adapterbinding.sectionedrecyclerviewadapter;

/**
 * Class used as constructor parameters of {@link Section}.
 */
@SuppressWarnings({ "WeakerAccess", "PMD.AvoidFieldNameMatchingMethodName" })
public final class SectionParameters {

    public final boolean itemViewWillBeProvided;
    public final boolean headerViewWillBeProvided;
    public final boolean footerViewWillBeProvided;
    public final boolean loadingViewWillBeProvided;
    public final boolean failedViewWillBeProvided;
    public final boolean emptyViewWillBeProvided;

    private SectionParameters(Builder builder) {

        this.itemViewWillBeProvided = builder.itemViewWillBeProvided;
        this.headerViewWillBeProvided = builder.headerViewWillBeProvided;
        this.footerViewWillBeProvided = builder.footerViewWillBeProvided;
        this.loadingViewWillBeProvided = builder.loadingViewWillBeProvided;
        this.failedViewWillBeProvided = builder.failedViewWillBeProvided;
        this.emptyViewWillBeProvided = builder.emptyViewWillBeProvided;
    }

    /**
     * Builder static factory method with mandatory parameters of {@link Section} (namely none).
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder of {@link SectionParameters}.
     */
    public static class Builder {
        private boolean itemViewWillBeProvided;
        private boolean headerViewWillBeProvided;
        private boolean footerViewWillBeProvided;
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
        public Builder itemViewWillBeProvided() {
            itemViewWillBeProvided = true;

            return this;
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public Builder headerViewWillBeProvided() {
            headerViewWillBeProvided = true;

            return this;
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public Builder footerViewWillBeProvided() {
            footerViewWillBeProvided = true;

            return this;
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public Builder loadingViewWillBeProvided() {
            loadingViewWillBeProvided = true;

            return this;
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public Builder failedViewWillBeProvided() {
            failedViewWillBeProvided = true;

            return this;
        }

        /**
         * Declare that there will be no , as the view will be provided by
         * the Section's implementation directly.
         *
         * @return this builder
         */
        public Builder emptyViewWillBeProvided() {
            emptyViewWillBeProvided = true;

            return this;
        }

        /**
         * Build an instance of SectionParameters.
         *
         * @return an instance of SectionParameters
         */
        public SectionParameters build() {
            return new SectionParameters(this);
        }
    }
}
