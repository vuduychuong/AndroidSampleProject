package com.chuongvd.app.example.ui.main;

import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.content.Intent;
import com.chuongvd.app.example.R;
import com.chuongvd.app.example.databinding.ActivityMainBinding;
import com.chuongvd.app.example.ui.common.DataBindingActivity;

public class MainActivity extends DataBindingActivity<ActivityMainBinding, AndroidViewModel> {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        MainFragment fragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, MainFragment.class.getName())
                .commit();
    }

    @Override
    protected void subscribeToViewModel() {

    }

    public static Intent newIntance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
