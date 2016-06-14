package com.wangjin.courseline.model;

import java.util.ArrayList;

/**
 * Created by wangjin on 16/5/30.
 */
public class Course {

    private String name;

    private String teacherName;

    private CourseTime time;

    public CourseTime getTime() {
        return time;
    }

    public void setTime(CourseTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


}
