package com.chuongvd.support.adapterbinding;

import android.databinding.ViewDataBinding;

/**
 * Created on 9/15/18
 *
 * @author Chuongvd
 */
public class SectionViewHolder<BINDINGVIEW extends ViewDataBinding, MODEL>
        extends ViewHolderBinding<BINDINGVIEW, MODEL> {

    public SectionViewHolder(BINDINGVIEW binding) {
        super(binding);
    }

    public SectionViewHolder(BINDINGVIEW binding,
            AdapterBinding.OnRecyclerItemListener<MODEL> listener) {
        super(binding, listener);
    }
}
