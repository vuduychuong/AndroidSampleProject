package com.chuongvd.app.signal;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicApp extends Application implements Application.ActivityLifecycleCallbacks {

    private static Application sSelf;
    private static int sNumActivityStarted;

    @Override
    public void onCreate() {
        super.onCreate();
        sSelf = this;
        registerActivityLifecycleCallbacks(this);
    }

    public static Application self() {
        return sSelf;
    }

    public static boolean isAppInBackground() {
        return sNumActivityStarted <= 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        sNumActivityStarted++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        sNumActivityStarted--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
