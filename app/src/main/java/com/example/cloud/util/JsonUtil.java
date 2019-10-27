package com.example.cloud.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYeTong on 2017/9/22.
 * Email:wytaper495@qq.com
 */

public class JsonUtil {
    private static Gson mGson = new Gson();

    /**把一个Object类型的数据转换为 Json格式的字符串
     * @param object ：
     * @return ：
     */
    public static String toJsonString(Object object) {
        return mGson.toJson(object);
    }


    public static <T> List<T> parseArray(String jsonArrayString, TypeToken<List<T>> type) {
        return mGson.fromJson(jsonArrayString,type.getType());
    }
    public static <T> T parseArray(JsonElement element, TypeToken<T> type) {
        return mGson.fromJson(element,type.getType());
    }
    /** 将json 字符 解析 出来
     * @param jsonString :json字符串
     * @param c : Class 类型
     * @param <T> : 泛型
     * @return :T所代表的类的对象
     */
    public static <T> T parse(String jsonString, Class<T> c) {
        return mGson.fromJson(jsonString, c);
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> toArray(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    public static boolean isJson(String content) {
        if (content == null || content.isEmpty() || content.length() == 0 || content.equals("")) {
            return false;
        }
        return true;
    }

    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
