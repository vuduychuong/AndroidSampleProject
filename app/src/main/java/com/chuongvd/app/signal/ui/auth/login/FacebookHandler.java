package com.chuongvd.app.signal.ui.auth.login;

import android.content.Context;
import android.support.annotation.NonNull;
import com.chuongvd.app.signal.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FacebookHandler {

    @NonNull
    private Context mContext;

    public FacebookHandler(@NonNull Context context) {
        mContext = context;
    }

    public List<String> getFacebookPermission() {
        List<String> list =
                Arrays.asList(mContext.getResources().getStringArray(R.array.facebook_permission));
        return list;
    }

    /**
     * The photos must be less than 12MB in size
     * People need the native Facebook for Android app installed, version 7.0 or higher
     */
    public void shareImage() {
        //TODO: function share image to facebook's user
    }
}
