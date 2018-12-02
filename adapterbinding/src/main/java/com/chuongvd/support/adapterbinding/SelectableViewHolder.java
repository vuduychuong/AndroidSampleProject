package com.chuongvd.support.adapterbinding;

import android.databinding.ViewDataBinding;
import com.chuongvd.support.adapterbinding.AdapterBinding.OnRecyclerItemListener;

/**
 * Created on 9/3/18
 *
 * @author Chuongvd
 */
public class SelectableViewHolder<BINDINGVIEW extends ViewDataBinding, MODEL extends ItemSelectable>
        extends ViewHolderBinding<BINDINGVIEW, MODEL> {

    public SelectableViewHolder(BINDINGVIEW binding, OnRecyclerItemListener<MODEL> listener) {
        super(binding, listener);
    }
}
