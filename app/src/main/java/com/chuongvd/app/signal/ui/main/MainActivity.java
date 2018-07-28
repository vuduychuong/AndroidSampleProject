package com.chuongvd.app.signal.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.chuongvd.app.signal.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            MainFragment fragment = new MainFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, MainFragment.class.getName())
                    .commit();
        }
    }

    public static Intent newIntance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
