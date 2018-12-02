package com.chuongvd.app.example.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import com.chuongvd.app.example.BasicApp;
import com.google.gson.Gson;
import java.util.Map;

/**
 * Created by chuongvd on 12/1/18.
 */
public class PrefHelpers {
    private static final String PREF_NAME = "Sample-Prefs";
    private static PrefHelpers _self;
    private SharedPreferences mSharedPreferences;
    private Gson mGson;

    @VisibleForTesting
    public static PrefHelpers init(Context context) {
        return new PrefHelpers(context.getApplicationContext());
    }

    public static PrefHelpers self() {
        return _self == null ? _self = new PrefHelpers(BasicApp.context()) : _self;
    }

    private PrefHelpers(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    public <T> T get(String key, Class<T> clazz) {
        if (clazz == String.class) {
            return (T) mSharedPreferences.getString(key, null);
        } else if (clazz == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (clazz == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (clazz == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (clazz == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        } else {
            String json = mSharedPreferences.getString(key, null);
            if (!TextUtils.isEmpty(json)) return mGson.fromJson(json, clazz);
        }
        return null;
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data == null) {
            remove(key);
            return;
        }
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else {
            editor.putString(key, mGson.toJson(data));
        }
        editor.apply();
    }

    public void remove(String... keys) {
        if (keys == null) {
            return;
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @VisibleForTesting
    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }
}
