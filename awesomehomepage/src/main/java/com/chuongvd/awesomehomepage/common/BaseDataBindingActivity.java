package com.chuongvd.awesomehomepage.common;

import android.app.Activity;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public abstract class BaseDataBindingActivity<B extends ViewDataBinding, V extends AndroidViewModel>
        extends AppCompatActivity {

    protected B mBinding;

    protected V mViewModel;

    protected abstract int getContentViewLayoutId();

    protected Context context() {
        return this;
    }

    protected abstract void initListeners();

    protected abstract void initData();

    protected abstract void subscribeToViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getContentViewLayoutId());
        initListeners();
        initData();
        subscribeToViewModel();
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        // all touch events close the keyboard before they are processed except EditText instances.
        // if focus is an EditText we need to check, if the touchevent was inside the focus editTexts
        final View currentFocus = getCurrentFocus();
        if (!(currentFocus instanceof EditText) || !isTouchInsideView(ev, currentFocus)) {
            hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * determine if the given motionevent is inside the given view.
     *
     * @param ev the given view
     * @param currentFocus the motion event.
     * @return if the given motionevent is inside the given view
     */
    private boolean isTouchInsideView(final MotionEvent ev, final View currentFocus) {
        final int[] loc = new int[2];
        currentFocus.getLocationOnScreen(loc);
        return ev.getRawX() > loc[0] && ev.getRawY() > loc[1] && ev.getRawX() < (loc[0]
                + currentFocus.getWidth()) && ev.getRawY() < (loc[1] + currentFocus.getHeight());
    }

    private void hideKeyboard(@NonNull Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) return;
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
