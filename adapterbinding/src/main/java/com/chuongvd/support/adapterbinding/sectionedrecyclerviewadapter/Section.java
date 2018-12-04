package com.chuongvd.support.adapterbinding.sectionedrecyclerviewadapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.chuongvd.support.adapterbinding.BindableDataSupport;
import com.chuongvd.support.adapterbinding.R;
import com.chuongvd.support.adapterbinding.ViewHolderBinding;
import com.chuongvd.support.adapterbinding.databinding.ItemNoDataBinding;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "WeakerAccess", "PMD.AvoidFieldNameMatchingMethodName" })
public abstract class Section<MODEL> {

    private final Context mContext;
    private SectionedRecyclerViewAdapter mAttachedAdapter;

    protected List<MODEL> mList = new ArrayList<>();

    public enum State {
        LOADING, LOADED, FAILED, EMPTY
    }

    private State state = State.LOADED;

    private boolean visible = true;

    private boolean hasHeader = false;
    private boolean hasFooter = false;

    private final boolean itemViewWillBeProvided;
    private final boolean headerViewWillBeProvided;
    private final boolean footerViewWillBeProvided;
    private final boolean loadingViewWillBeProvided;
    private final boolean failedViewWillBeProvided;
    private final boolean emptyViewWillBeProvided;

    /**
     * Create a Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */
    public Section(SectionParameters sectionParameters) {
        this(null, sectionParameters);
    }

    public Section(Context context, SectionParameters sectionParameters) {
        mContext = context;
        this.itemViewWillBeProvided = sectionParameters.itemViewWillBeProvided;
        this.headerViewWillBeProvided = sectionParameters.headerViewWillBeProvided;
        this.footerViewWillBeProvided = sectionParameters.footerViewWillBeProvided;
        this.loadingViewWillBeProvided = sectionParameters.loadingViewWillBeProvided;
        this.failedViewWillBeProvided = sectionParameters.failedViewWillBeProvided;
        this.emptyViewWillBeProvided = sectionParameters.emptyViewWillBeProvided;
        //
        this.hasHeader = this.headerViewWillBeProvided;
        this.hasFooter = this.footerViewWillBeProvided;
    }

    public void updateBindableData(List<MODEL> data) {
        if (data == null) return;
        int oldSize = mList.size();
        int newSize = data.size();
        if (newSize == 0) {
            mList.clear();
            mAttachedAdapter.notifyItemRangeChangedInSection(this, 0, oldSize);
            return;
        }
        if (oldSize == 0) {
            mList.addAll(data);
            mAttachedAdapter.notifyDataSetChanged();
            return;
        }
        if (newSize < oldSize) {
            for (int i = 0; i < newSize; i++) {
                MODEL model = mList.get(i);
                if (model instanceof BindableDataSupport) {
                    ((BindableDataSupport) model).updateBindableData(data.get(i));
                } else {
                    if (!data.get(i).equals(mList.get(i))) {
                        mList.set(i, data.get(i));
                        mAttachedAdapter.notifyItemChangedInSection(this, i);
                    }
                }
            }
            for (int i = newSize; i < oldSize; i++) {
                mList.remove(newSize);
            }
            mAttachedAdapter.notifyItemRangeRemovedFromSection(this, newSize, oldSize - newSize);
            return;
        }
        if (newSize == oldSize) {
            for (int i = 0; i < newSize; i++) {
                MODEL model = mList.get(i);
                if (model instanceof BindableDataSupport) {
                    ((BindableDataSupport) model).updateBindableData(data.get(i));
                } else {
                    if (!data.get(i).equals(mList.get(i))) {
                        mList.set(i, data.get(i));
                        mAttachedAdapter.notifyItemChangedInSection(this, i);
                    }
                }
            }
            return;
        }
        /*
          newSize > oldSize
         */
        for (int i = 0; i < oldSize; i++) {
            MODEL model = mList.get(i);
            if (model instanceof BindableDataSupport) {
                ((BindableDataSupport) model).updateBindableData(data.get(i));
            } else {
                if (!data.get(i).equals(mList.get(i))) {
                    mList.set(i, data.get(i));
                    mAttachedAdapter.notifyItemChangedInSection(this, i);
                }
            }
        }
        for (int i = oldSize; i < newSize; i++) {
            mList.add(data.get(i));
        }
        mAttachedAdapter.notifyItemRangeInsertedInSection(this, oldSize, newSize - oldSize);
    }

    public void addItem(MODEL item) {
        addItem(item, mList.size());
    }

    public void addItem(MODEL item, int position) {
        mList.add(position, item);
        mAttachedAdapter.notifyItemInsertedInSection(this, position);
    }

    public void addListItem(List<MODEL> list) {
        addListItem(list, mList.size());
    }

    public void addListItem(List<MODEL> list, int position) {
        mList.addAll(position, list);
        mAttachedAdapter.notifyItemRangeInsertedInSection(this, position, list.size());
    }

    public void clear() {
        updateBindableData(new ArrayList<>());
    }

    public void removeItem(int position) {
        if (position >= mList.size() || position < 0) {
            throw new IndexOutOfBoundsException("Wrong position!!!");
        }
        mList.remove(position);
        mAttachedAdapter.notifyItemRemovedFromSection(this, position);
    }

    public void removeItem(MODEL item) {
        int position = -1;
        position = mList.indexOf(item);
        if (position == -1) {
            throw new Resources.NotFoundException("Not founded any matched item!!!");
        }
        removeItem(position);
    }

    public void attachedAdapter(SectionedRecyclerViewAdapter adapter) {
        mAttachedAdapter = adapter;
    }

    protected Context context() {
        return mContext;
    }

    /**
     * Set the State of this Section.
     *
     * @param state state of this section
     */
    public final void setState(State state) {
        switch (state) {
            case LOADING:
                if (!loadingViewWillBeProvided) {
                    throw new IllegalStateException(
                            "Resource id for 'loading state' should be provided or 'loadingViewWillBeProvided' should be set");
                }
                break;
            case FAILED:
                if (!failedViewWillBeProvided) {
                    throw new IllegalStateException(
                            "Resource id for 'failed state' should be provided or 'failedViewWillBeProvided' should be set");
                }
                break;
            case EMPTY:
                if (!emptyViewWillBeProvided) {
                    throw new IllegalStateException(
                            "Resource id for 'empty state' should be provided or 'emptyViewWillBeProvided' should be set");
                }
                break;
            default:
                break;
        }

        this.state = state;
    }

    /**
     * Return the current State of this Section.
     *
     * @return current state of this section
     */
    public final State getState() {
        return state;
    }

    /**
     * Check if this Section is visible.
     *
     * @return true if this Section is visible
     */
    public final boolean isVisible() {
        return visible;
    }

    /**
     * Set if this Section is visible.
     *
     * @param visible true if this Section is visible
     */
    public final void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Check if this Section has a header.
     *
     * @return true if this Section has a header
     */
    public final boolean hasHeader() {
        return hasHeader;
    }

    /**
     * Set if this Section has header.
     *
     * @param hasHeader true if this Section has a header
     */
    public final void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * Check if this Section has a footer.
     *
     * @return true if this Section has a footer
     */
    public final boolean hasFooter() {
        return hasFooter;
    }

    /**
     * Set if this Section has footer.
     *
     * @param hasFooter true if this Section has a footer
     */
    public final void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    /**
     * @return whether the item view is provided through {@link #getItemView(ViewGroup)}.
     */
    public final boolean isItemViewWillBeProvided() {
        return itemViewWillBeProvided;
    }

    /**
     * @return whether the header view is provided through {@link #getHeaderView(ViewGroup)}.
     */
    public final boolean isHeaderViewWillBeProvided() {
        return headerViewWillBeProvided;
    }

    /**
     * @return whether the footer view is provided through {@link #getFooterView(ViewGroup)}.
     */
    public final boolean isFooterViewWillBeProvided() {
        return footerViewWillBeProvided;
    }

    /**
     * @return whether the loading view is provided through {@link #getLoadingView(ViewGroup)}.
     */
    public final boolean isLoadingViewWillBeProvided() {
        return loadingViewWillBeProvided;
    }

    /**
     * @return whether the failed view is provided through {@link #getFailedView(ViewGroup)}.
     */
    public final boolean isFailedViewWillBeProvided() {
        return failedViewWillBeProvided;
    }

    /**
     * @return whether the empty view is provided through {@link #getEmptyView(ViewGroup)}.
     */
    public final boolean isEmptyViewWillBeProvided() {
        return emptyViewWillBeProvided;
    }

    /**
     * Bind the data to the ViewHolder for the Content of this Section, that can be the Items,
     * Loading view or Failed view, depending on the current state of the section.
     *
     * @param holder ViewHolder for the Content of this Section
     * @param position position of the item in the Section, not in the RecyclerView
     */
    public final void onBindContentViewHolder(ViewHolderBinding holder, int position) {
        switch (state) {
            case LOADING:
                onBindLoadingViewHolder(holder);
                break;
            case LOADED:
                onBindItemViewHolder(holder, position);
                break;
            case FAILED:
                onBindFailedViewHolder(holder);
                break;
            case EMPTY:
                onBindEmptyViewHolder(holder);
                break;
            default:
                throw new IllegalStateException("Invalid state");
        }
    }

    /**
     * Return the total of items of this Section, including content items (according to the section
     * state) plus header and footer.
     *
     * @return total of items of this section
     */
    public final int getSectionItemsTotal() {
        int contentItemsTotal;

        switch (state) {
            case LOADING:
                contentItemsTotal = 1;
                break;
            case LOADED:
                contentItemsTotal = getContentItemsTotal();
                break;
            case FAILED:
                contentItemsTotal = 1;
                break;
            case EMPTY:
                contentItemsTotal = 1;
                break;
            default:
                throw new IllegalStateException("Invalid state");
        }

        return contentItemsTotal + (hasHeader ? 1 : 0) + (hasFooter ? 1 : 0);
    }

    /**
     * Return the total of items of this Section.
     *
     * @return total of items of this Section
     */
    public abstract int getContentItemsTotal();

    /**
     * @param parent The parent view. Note that there is no need to attach the new view.
     * @return View for an Item of this Section.
     */
    public View getItemView(ViewGroup parent) {
        throw new UnsupportedOperationException(
                "You need to implement getItemView() if you set itemViewWillBeProvided");
    }

    public abstract ViewHolderBinding getItemViewHolder(ViewGroup parent, LayoutInflater inflater);

    public abstract void onBindItemViewHolder(ViewHolderBinding holder, int position);

    public View getHeaderView(@SuppressWarnings("unused") ViewGroup parent) {
        throw new UnsupportedOperationException(
                "You need to implement getHeaderView() if you set headerViewWillBeProvided");
    }

    /**
     * Return the ViewHolder for the Header of this Section.
     *
     * @return ViewHolder for the Header of this Section
     */
    public ViewHolderBinding getHeaderViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new EmptyViewHolder(ItemNoDataBinding.inflate(inflater, parent, false));
    }

    /**
     * Bind the data to the ViewHolder for the Header of this Section.
     *
     * @param holder ViewHolder for the Header of this Section
     */
    public void onBindHeaderViewHolder(ViewHolderBinding holder) {
        // Nothing to bind here.
    }

    /**
     * @param parent The parent view. Note that there is no need to attach the new view.
     * @return View for the Footer of this Section.
     */
    public View getFooterView(@SuppressWarnings("unused") ViewGroup parent) {
        throw new UnsupportedOperationException(
                "You need to implement getFooterView() if you set footerViewWillBeProvided");
    }

    public ViewHolderBinding getFooterViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new EmptyViewHolder(ItemNoDataBinding.inflate(inflater, parent, false));
    }

    public void onBindFooterViewHolder(ViewHolderBinding holder) {
        // Nothing to bind here.
    }

    public View getLoadingView(@SuppressWarnings("unused") ViewGroup parent) {
        throw new UnsupportedOperationException(
                "You need to implement getLoadingView() if you set loadingViewWillBeProvided");
    }

    public ViewHolderBinding getLoadingViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new EmptyViewHolder(ItemNoDataBinding.inflate(inflater, parent, false));
    }

    //
    //    /**
    //     * Bind the data to the ViewHolder for Loading state of this Section.
    //     *
    //     * @param holder ViewHolder for the Loading state of this Section
    //     */
    public void onBindLoadingViewHolder(ViewHolderBinding holder) {
        // Nothing to bind here.
    }

    public View getFailedView(@SuppressWarnings("unused") ViewGroup parent) {
        throw new UnsupportedOperationException(
                "You need to implement getFailedView() if you set failedViewWillBeProvided");
    }

    public ViewHolderBinding getFailedViewHolder(ViewGroup parent, LayoutInflater inflater) {
        return new EmptyViewHolder(ItemNoDataBinding.inflate(inflater, parent, false));
    }

    /**
     * Bind the data to the ViewHolder for the Failed state of this Section.
     *
     * @param holder ViewHolder for the Failed state of this Section
     */
    public void onBindFailedViewHolder(ViewHolderBinding holder) {
        // Nothing to bind here.
    }

    public View getEmptyView(@SuppressWarnings("unused") ViewGroup parent) {
        throw new UnsupportedOperationException(
                "You need to implement getEmptyView() if you set emptyViewWillBeProvided");
    }

    public ViewHolderBinding<ItemNoDataBinding, String> getEmptyViewHolder(ViewGroup parent,
            LayoutInflater inflater) {
        return new EmptyViewHolder(ItemNoDataBinding.inflate(inflater, parent, false));
    }

    /**
     * Bind the data to the ViewHolder for the Empty state of this Section.
     *
     * @param holder ViewHolder for the Empty state of this Section
     */
    public void onBindEmptyViewHolder(ViewHolderBinding holder) {
        // Nothing to bind here.
    }

    /**
     * A concrete class of an empty ViewHolder.
     * Should be used to avoid the boilerplate of creating a ViewHolder class for simple case
     * scenarios.
     */
    public class EmptyViewHolder extends ViewHolderBinding<ItemNoDataBinding, String> {

        EmptyViewHolder(ItemNoDataBinding binding) {
            super(binding);
        }

        @Override
        public void bindData(String model) {
            if (TextUtils.isEmpty(model)) {
                model = context().getString(R.string.no_data);
            }
            mBinding.setText(model);
        }
    }
}
