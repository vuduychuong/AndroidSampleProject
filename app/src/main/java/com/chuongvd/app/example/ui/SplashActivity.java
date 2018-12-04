package com.chuongvd.app.example.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.chuongvd.app.example.R;

public class SplashActivity extends AppCompatActivity {
    private static final long DELAY_TIME = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (savedInstanceState == null) {
            SplashFragment fragment = new SplashFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, SplashFragment.class.getName())
                    .commit();
        }
    }
}
