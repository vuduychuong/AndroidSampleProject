package com.chuongvd.support.adapterbinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created on 9/3/18
 *
 * @author Chuongvd
 */
public class ItemSelectable extends BaseObservable {
    public boolean selected;

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }
}
