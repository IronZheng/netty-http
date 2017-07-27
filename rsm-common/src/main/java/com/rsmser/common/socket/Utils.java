package com.rsmser.common.socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *  JSON以及其他工具类
 */
public class Utils {

    private static Gson gson = new GsonBuilder().create();

    public static String transToJson(Object obj) {
        return gson.toJson(obj);
    }

}
