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

    public static void saveStudent(String student_id,String student_pwd) {
        sp.edit().putString("student_id",student_id).commit();
        sp.edit().putString("student_pwd",student_pwd).commit();
    }

    public static int getUserId(){
        return sp.getInt("userId",-1);
    }

    public static String getStudentId(){
        return sp.getString("student_id","");
    }

    public static String getStudentPwd(){
        return sp.getString("student_pwd","");
    }

    public static void deleteUserId(){
        sp.edit().clear().commit();
    }
}
