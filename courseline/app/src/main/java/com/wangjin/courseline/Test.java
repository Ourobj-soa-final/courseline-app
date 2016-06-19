package com.wangjin.courseline;

import com.tongji.courseline.CourseTool;

/**
 * Created by wangjin on 16/6/19.
 */
public class Test extends Thread {

    public void getCourse()
    {
        try {
            System.out.println(CourseTool.getAllCoursesInfo("id", "password", "101"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet()
    {
        getCourse();
    }

    @Override
    public void run() {
        doGet();
    }
}
