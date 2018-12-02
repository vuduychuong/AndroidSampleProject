package com.chuongvd.app.example.utils;
//
// Created by chuongvd on 9/10/18.
//
//

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonUtils {

    public static Gson instance;

    public static Gson getInstance() {
        if (instance == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Double.class,
                    (JsonSerializer<Double>) (originalValue, typeOf, context) -> {
                        BigDecimal bigValue = BigDecimal.valueOf(originalValue);

                        return new JsonPrimitive(bigValue.toPlainString());
                    });
            instance = gsonBuilder.create();
        }

        return instance;
    }

    public static String Object2String(Object obj) {
        return getInstance().toJson(obj);
    }

    public static <T> String Object2String(Object obj, TypeToken<T> typeToken) {
        return getInstance().toJson(obj, typeToken.getType());
    }

    public static <T> T String2Object(String json, Class<T> clzz) {
        if (TextUtils.isEmpty(json)) return null;
        return getInstance().fromJson(json, clzz);
    }

    public static <T> T String2Object(String json, Type typeOfT) {
        if (TextUtils.isEmpty(json)) return null;
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(json, typeOfT);
    }

    public static <T> List<T> String2ListObject(String json, Class<T[]> clazz) {
        if (TextUtils.isEmpty(json)) return new ArrayList<>();
        T[] t = getInstance().fromJson(json, clazz);
        return Arrays.asList(t);
    }

    public static <T> T String2Object(String json, TypeToken<T> tTypeToken) {
        if (TextUtils.isEmpty(json)) return null;
        return getInstance().fromJson(json, tTypeToken.getType());
    }
}