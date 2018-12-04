package com.chuongvd.app.example;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicApp extends Application implements Application.ActivityLifecycleCallbacks {

    private static Application sSelf;
    private static Context sContext;
    private static int sNumActivityStarted;

    @Override
    protected void attachBaseContext(Context base) {
        sContext = base;
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSelf = this;
        registerActivityLifecycleCallbacks(this);
    }

    public static Application getInstance() {
        return sSelf;
    }

    public static Context context() {
        return sContext;
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
