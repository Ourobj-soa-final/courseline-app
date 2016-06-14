package com.wangjin.courseline;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangjin on 16/6/13.
 */
public class Saver {
    private static SharedPreferences sp;
    public static final String SP_NAME = "USER_INFO";

    public static void init(Context context) {
        sp = context.getSharedPreferences(SP_NAME, 0);
    }

    public static void saveUserId(int userId){
        sp.edit().putInt("userId",userId).commit();
    }

    public static int getUserId(){
        return sp.getInt("userId",-1);
    }
}
