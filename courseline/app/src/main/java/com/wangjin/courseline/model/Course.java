package com.wangjin.courseline.model;

import java.util.ArrayList;

/**
 * Created by wangjin on 16/5/30.
 */
public class Course {

    private String name;

    private String place;

    private String teacherName;

    private ArrayList<CourseTime> times;

    public ArrayList<CourseTime> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<CourseTime> times) {
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


}
