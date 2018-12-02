package com.chuongvd.support.adapterbinding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created on 9/15/18
 *
 * @author Chuongvd
 */
public abstract class SectionAdapter<VIEWHOLDER extends SectionViewHolder, MODEL>
        extends AdapterBinding<VIEWHOLDER, MODEL> {
    protected static final int TYPE_SECTION_HEADER = 3;

    public SectionAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolderBinding onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NO_DATA:
                return getNoDataViewHolder(parent, getLayoutInflater(parent.getContext()));
            case TYPE_SECTION_HEADER:
                return getSectionHeaderViewHolder(parent, getLayoutInflater(parent.getContext()));
            default:
                return getViewHolder(parent, getLayoutInflater(parent.getContext()));
        }
    }

    protected abstract ViewHolderBinding getSectionHeaderViewHolder(ViewGroup parent,
            LayoutInflater layoutInflater);

    @Override
    public int getItemViewType(int position) {
        if (getData().size() == 0) return TYPE_NO_DATA;
        if (isEnableSection()) {
            int countSize = 0;
            for (int i = 0; i < getNumSection(); i++) {
                if (position == countSize) return TYPE_SECTION_HEADER;
                countSize += getDataSection(i).size() + 1;
                if (position < countSize) return TYPE_NORMAL;
            }
        }
        return super.getItemViewType(position);
    }

    protected abstract boolean isEnableSection();

    protected abstract List<MODEL> getDataSection(int sectionPosition);

    @Override
    public int getItemCount() {
        if (isEnableSection() && getData().size() > 0) {
            return getData().size() + getNumSection();
        }
        return super.getItemCount();
    }

    protected abstract int getNumSection();

    public int getRealPosition(int position) {
        if (isEnableSection()) {

        }
        return position;
    }
}
